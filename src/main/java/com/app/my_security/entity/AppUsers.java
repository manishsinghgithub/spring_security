package com.app.my_security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class AppUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String id;
    @Column(name = "user_name")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "roles")
    @ElementCollection(targetClass = UserRoles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "user_id")
    @OneToMany(cascade = CascadeType.ALL, targetEntity = UserRoles.class, fetch = FetchType.EAGER)
    private List<UserRoles> roles;
}
