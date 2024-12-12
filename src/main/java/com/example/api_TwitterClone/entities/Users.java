package com.example.api_TwitterClone.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "background", nullable = false)
    private String background;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tweets> tweets;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TweetsComments> comments;

    public Users() {}
}
