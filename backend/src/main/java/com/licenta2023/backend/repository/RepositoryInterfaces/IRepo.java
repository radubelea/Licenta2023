package com.licenta2023.backend.repository.RepositoryInterfaces;

import com.licenta2023.backend.domain.Entity;

public interface IRepo<E extends Entity> {
    E findOne(Long id);

    Iterable<E> findAll();

    void save(E entity);

    void delete(Long id);

    void update(E entity);

}
