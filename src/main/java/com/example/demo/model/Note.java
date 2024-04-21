package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "document", nullable = true)
    private Document document;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private User user;


    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private Timestamp timestamp;

}
