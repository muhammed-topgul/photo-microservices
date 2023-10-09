package com.mtopgul.photoapp.userservice.mapper;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author muhammed-topgul
 * @since 20/09/2023 09:50
 */
public abstract class AbstractMapper<D, E> {
    public abstract D toDto(E entity);

    public abstract E toEntity(D dto);

    public abstract List<D> toDto(List<E> entities);

    public abstract List<E> toEntity(List<D> dtos);

    public D save(CrudRepository<E, ?> repository, D dto) {
        return toDto(repository.save(toEntity(dto)));
    }
}
