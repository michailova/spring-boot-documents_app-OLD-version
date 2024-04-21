package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="document_type_ID")
    private DocumentType type;

    @Column(name = "designation", nullable = true)
    private String designation;

    @Column(name = "status", nullable=false)
    private String status = DocumentStatus.ACT.getDocumentStatus();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="developer_ID")
    private Department developer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id")
    private Department owner;

    @Column(name = "valid_from", nullable = true)
    private Date valid_from;


    @Column(name = "valid_until", nullable = true)
    private Date valid_until;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "replacing_document", referencedColumnName = "id" , nullable = true)
    private Document replacing_document;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "canceled_document", referencedColumnName = "id" , nullable = true)
    private Document canceled_document;

    @Column(name = "replacing_documents", nullable = true)
    private String replacing_documents;

    @Column(name = "created_ts", nullable = false)
    private Timestamp created_ts;

    @Column(name = "updated_ts", nullable = true)
    private Timestamp updated_ts;

    @Column(name = "revision_interval", nullable = true)
    private String revision_interval;                      // TODO: 08.04.2024

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "departments_documents",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments = new HashSet<>();

    @Column(name = "path", nullable = true)
    private String path;


}



