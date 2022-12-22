package com.daria.realestate.dao;

import com.daria.realestate.domain.PaginationFilter;

import java.util.List;

public interface GenericDAO<T> {
    /**
     * Get all entities.
     *
     * @return list of all entities
     */
    List<T> getAll();

    /**
     * Update entity.
     *
     * @param entity, id
     * @return updated entity
     */
    T update(T entity, long id);

    /**
     * Create entity.
     *
     * @param entity
     * @return created entity
     */
    long create(T entity);

    /**
     * Get entity by id.
     *
     * @param id of entity to be found
     * @return entity
     */
    T getById(long id);

    /**
     * Remove by id.
     *
     * @param id of entity to be found
     * @return id of removed entity
     */
    long removeById(long id);

    List<T> paginate(PaginationFilter paginationFilter);
}
