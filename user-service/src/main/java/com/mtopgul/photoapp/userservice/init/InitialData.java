package com.mtopgul.photoapp.userservice.init;

import com.mtopgul.photoapp.userservice.entity.AuthorityEntity;
import com.mtopgul.photoapp.userservice.entity.RoleEntity;
import com.mtopgul.photoapp.userservice.entity.UserEntity;
import com.mtopgul.photoapp.userservice.enumeration.AuthorityEnum;
import com.mtopgul.photoapp.userservice.enumeration.RoleEnum;
import com.mtopgul.photoapp.userservice.repository.AuthorityRepository;
import com.mtopgul.photoapp.userservice.repository.RoleRepository;
import com.mtopgul.photoapp.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * @author muhammed-topgul
 * @since 15/10/2023 20:12
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class InitialData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void run(String... args) {
        log.info("Application ready event.");
        AuthorityEntity readAuthority = newAuthority(AuthorityEnum.READ.name());
        AuthorityEntity writeAuthority = newAuthority(AuthorityEnum.WRITE.name());
        AuthorityEntity deleteAuthority = newAuthority(AuthorityEnum.DELETE.name());

        RoleEntity user = createRole(RoleEnum.ROLE_USER.name(), Set.of(readAuthority, writeAuthority));
        RoleEntity admin = createRole(RoleEnum.ROLE_ADMIN.name(), Set.of(readAuthority, writeAuthority, deleteAuthority));

        log.info("User {} ready to use.", newUser("Muhammed", "Topgul", "mtopgul@xmail.com", Set.of(admin)));
        log.info("User {} ready to use.", newUser("John", "Doe", "jdoe@xmail.com", Set.of(user)));
    }

    @Transactional
    protected UserEntity newUser(String firstName, String lastName, String email, Set<RoleEntity> roles) {
        Optional<UserEntity> optionalUser = userRepository.findUserEntityByEmail(email);
        if (optionalUser.isEmpty()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userEntity.setEmail(email);
            userEntity.setPassword("12345678");
            userEntity.setEncryptedPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(roles);
            return userRepository.save(userEntity);
        }
        return optionalUser.get();
    }

    @Transactional
    protected AuthorityEntity newAuthority(String name) {
        Optional<AuthorityEntity> optionalAuthority = authorityRepository.findByName(name);
        if (optionalAuthority.isEmpty()) {
            return authorityRepository.save(new AuthorityEntity(name));
        }
        return optionalAuthority.get();
    }

    @Transactional
    protected RoleEntity createRole(String name, Set<AuthorityEntity> authorities) {
        Optional<RoleEntity> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isEmpty()) {
            return roleRepository.save(new RoleEntity(name, authorities));
        }
        return optionalRole.get();
    }
}
