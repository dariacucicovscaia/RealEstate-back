package com.daria.realestate.dao;


public interface DAO<T> {
    /**
     * Creates an entity
     *
     * @param entity to be created
     * @return entity with generated id
     */
    T create(T entity);

    /**
     * Removes an entity from the db by its id
     *
     * @param id of the entity to be removed
     * @return id
     */
    long removeById(long id);

    /**
     * Gets an entity by its id
     *
     * @param id of the entity we want to retrieve
     * @return Entity
     */
    T getById(long id);

}
