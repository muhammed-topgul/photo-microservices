package com.mtopgul.photoapp.userservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author muhammed-topgul
 * @since 01/11/2023 13:40
 */
@Entity
@Table(name = "t_authority")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<RoleEntity> roles;

    public AuthorityEntity(String name) {
        this.name = name;
    }
}
