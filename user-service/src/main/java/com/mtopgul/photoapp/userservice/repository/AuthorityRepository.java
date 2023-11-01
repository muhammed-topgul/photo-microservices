package com.mtopgul.photoapp.userservice.repository;

import com.mtopgul.photoapp.userservice.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author muhammed-topgul
 * @since 01/11/2023 13:45
 */
@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, String> {
    Optional<AuthorityEntity> findByName(String name);
}
