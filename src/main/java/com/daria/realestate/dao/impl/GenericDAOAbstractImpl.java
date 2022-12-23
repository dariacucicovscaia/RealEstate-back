package com.daria.realestate.dao.impl;

import com.daria.realestate.dao.GenericDAO;
import com.daria.realestate.domain.PaginationFilter;
import com.daria.realestate.util.DataBaseConnection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAOAbstractImpl<T> implements GenericDAO<T> {
    private static final Logger logger = LogManager.getLogger(GenericDAOAbstractImpl.class);

    private Class<T> entityClazz;

    private DataBaseConnection dataBaseConnection;

    protected GenericDAOAbstractImpl(DataBaseConnection dataBaseConnection, final Class<T> clazzToSet) {

        this.dataBaseConnection = dataBaseConnection;
        entityClazz = clazzToSet;
    }

    protected Connection getConnection() {
        return dataBaseConnection.getConnection();
    }

    @Override
    public List<T> getAll() {
        String sql = "SELECT *  FROM " + getTableName() + ";";
        List<T> entityList = setEntityValues(sql);

        return entityList;
    }

    @Override
    public T update(T entity, long id) {
        Field[] fields = entityClazz.getDeclaredFields();

        String resultToBeUpdated = "";
        for (Field field : fields) {
            try {
                if (!field.getType().getName().matches("com.daria.realestate.domain." + field.getType().getSimpleName())) {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (field.getType().getName().equals("int") || field.getType().getName().equals("Long") || field.getType().getName().equals("boolean")) {
                        resultToBeUpdated += field.getName() + " = " + value + ",";
                    } else {
                        resultToBeUpdated += "`" + field.getName() + "` = '" + value + "',";
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        resultToBeUpdated = resultToBeUpdated.substring(0, resultToBeUpdated.length() - 1);

        String update = "UPDATE " + getTableName() + "\n SET " + resultToBeUpdated + " \n WHERE `id`=" + id + ";";

        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(update);
            logger.info("Entity updated");
            return entity;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public long create(T entity) {
        String insert = "INSERT INTO " + getTableName() + "  " + getColumns() + " VALUES (" + getValuesToInsert(entity) + " );";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                logger.info("Created entity, with generated id of: " + generatedKeys.getLong(generatedKeys.getRow()));
                return generatedKeys.getLong(generatedKeys.getRow());
            } else return 0;

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public T getById(long id) {
        String sql = "SELECT *  FROM " + getTableName() + " WHERE id=" + id + " ;";
        List<T> entity = setEntityValues(sql);

        if (!(entity.size() < 1)) {
            logger.info("Entity "+ entity.get(0) + " extracted");
            return entity.get(0);
        } else {
            logger.error("No entity with the specified Id found!");
            throw new NullPointerException();
        }
    }

    @Override
    public long removeById(long id) {
        String sql = "DELETE FROM " + getTableName() + " \r\n 	WHERE id= " + id + " ;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate(sql);
            return id;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> getAllPaginated(String selectSql, PaginationFilter paginationFilter) {

        String sql = selectSql
                + " order by " + paginationFilter.getColumnWeWantOrdered() + " " + paginationFilter.getOrderBy().name()
                + " limit " + paginationFilter.getNrOfElementsWeWantDisplayed()
                + " offset " + getOffset(paginationFilter.getPageNumber(), paginationFilter.getNrOfElementsWeWantDisplayed()) + ";";

        return setEntityValues(sql);
    }

    private int getOffset(int pageNumber, int nrOfElementsWeWantDisplayed) {
        return (pageNumber - 1) * nrOfElementsWeWantDisplayed;
    }

    private String getColumns() {
        Field fields[] = entityClazz.getDeclaredFields();

        String columns = "(";
        for (Field field : fields) {
            if (!field.getName().equals("id") && !field.getType().getName().matches("com.daria.realestate.domain." + field.getType().getSimpleName())) {
                columns += field.getName() + ",";
            }
        }
        columns = columns.substring(0, columns.length() - 1);
        columns += ")";
        return columns;
    }

    private String getValuesToInsert(T entity) {
        Field fields[] = entityClazz.getDeclaredFields();
        String values = "";
        for (Field field : fields) {

            if (!field.getName().equals("id") && !field.getType().getName().matches("com.daria.realestate.domain." + field.getType().getSimpleName())) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);

                    if (field.getType().getName().equals("int") || field.getType().getName().equals("boolean") || field.getType().getName().equals("int[]")) {
                        values += value + ",";
                    } else {
                        values += "'" + value + "',";
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        values = values.substring(0, values.length() - 1);
        logger.info("The values to be added to the table are: " + values);
        return values;
    }

    private List<T> setEntityValues(String sql) {
        Field fields[] = entityClazz.getDeclaredFields();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
             ResultSet result = preparedStatement.executeQuery()) {
            List<T> entityList = new ArrayList<>();

            while (result.next()) {
                Constructor constr = entityClazz.getConstructor();
                T t = (T) constr.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    if (!field.getType().getName().matches("com.daria.realestate.domain." + field.getType().getSimpleName())) {
                        Object fieldValue = result.getObject(fieldName);
                        if (field.getType().isEnum()) {
                            Method valueOf = field.getType().getMethod("valueOf", String.class);
                            Object value = valueOf.invoke(null, fieldValue);
                            field.set(t, value);
                        } else if (field.getType().getName().equals("java.lang.Long")) {
                            field.set(t, fieldValue);
                        } else {
                            field.set(t, fieldValue);
                        }
                    }
                }
                entityList.add(t);
            }
            logger.info("Entity list contains " + entityList.size() + " elements");
            return entityList;
        } catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchMethodException | SecurityException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }


    protected abstract String getTableName();
}
