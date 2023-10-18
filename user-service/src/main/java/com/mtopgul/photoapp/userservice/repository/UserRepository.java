package com.mtopgul.photoapp.userservice.repository;

import com.mtopgul.photoapp.userservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author muhammed-topgul
 * @since 09/10/2023 10:35
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntityByEmail(String email);

    Optional<UserEntity> findUserEntityById(String email);
}
