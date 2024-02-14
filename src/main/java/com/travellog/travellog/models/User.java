package com.travellog.travellog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Username", nullable = false, length = 20)
    @JsonIgnore
    private String username;

    @Column(name = "Password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "Email", nullable = false, unique = true)
    @JsonIgnore
    private String email;

    @ManyToOne
    @JoinColumn(name = "RoleId", nullable = false)
    @JsonIgnore
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
}
