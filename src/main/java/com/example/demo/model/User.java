package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "USER_APP")
@ToString(exclude = "userProfiles")
@Data
@DynamicInsert
@DynamicUpdate
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(unique=true)
    private int person_number;

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String login;

    @Column(unique = true)
    private String email;

    @Column(nullable = false) // TODO: 08.03.2024 ENUM
    private boolean is_active;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "APP_USER_USER_PROFILE",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_documents",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )

    private List<Document> documents = new ArrayList<>();

}

