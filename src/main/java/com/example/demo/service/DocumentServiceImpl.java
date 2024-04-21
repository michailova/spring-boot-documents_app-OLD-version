package com.example.demo.service;


import com.example.demo.dto.UserDto;
import com.example.demo.model.Department;
import com.example.demo.model.Document;
import com.example.demo.model.User;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.DocumentTypeRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Autowired
    UserService userService;

    @Override
    public Document addDocument(Document document) {
        Document document1 = document;
        document1.setCreated_ts(new Timestamp(new Date().getTime()));
        List<UserDto> users = new ArrayList<>();

        documentRepository.save(document1);
        for (Department d:
                document1.getDepartments()) {
            users.addAll(userService.getUsersByDepartment(d));
        }
        for (UserDto u:
             users) {
            List<Document> documents = u.getDocuments();
            documents.add(document1);
            u.setDocuments(documents);
        }
        return document1;
    }

    @Override
    public Document updateDocument(int id, Document document) {
        Document updatedDocument = documentRepository.getById(id);
        updatedDocument.setId(id);
        updatedDocument.setDepartments(document.getDepartments());
        updatedDocument.setCanceled_document(document.getCanceled_document());
        updatedDocument.setUpdated_ts(new Timestamp(new Date().getTime()));
        updatedDocument.setReplacing_document(document.getReplacing_document());
        updatedDocument.setReplacing_documents(document.getReplacing_documents());
        updatedDocument.setName(document.getName());
        updatedDocument.setCreated_ts(document.getCreated_ts());
        updatedDocument.setDeveloper(document.getDeveloper());
        updatedDocument.setDesignation(document.getDesignation());
        updatedDocument.setOwner(document.getOwner());
        updatedDocument.setRevision_interval(document.getRevision_interval());
        updatedDocument.setValid_from(document.getValid_from());
        updatedDocument.setPath(document.getPath());
        updatedDocument.setStatus(document.getStatus());
        updatedDocument.setType(document.getType());
        updatedDocument.setValid_until(document.getValid_until());
        return documentRepository.save(updatedDocument);
    }

    @Override
    public Document getById(int id) {
        return documentRepository.getById(id);
    }

    @Override
    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    @Override
    public List<Document> getAllByType(String type) {
        List<Document> all = documentRepository.findAll();
        List<Document> allByType = new ArrayList<>();
        for (Document d :
                all) {
            if (d.getType().getName().equals(type)) {
                allByType.add(d);
            }
        }
        return allByType;
    }

    @Override
    public String delete(Integer id) {
        Optional<Document> document = documentRepository.findById(id);
        if(document.isPresent()) {
            documentRepository.deleteById(document.get().getId());
            return "Document with id: " + document.get().getId() + " deleted successfully!";
        }
        throw new RuntimeException("Could not delete  document with id:" + document.get().getId());
    }

    @Override
    public List<Document> search(String keyword) {
            List<Document> all = documentRepository.findAll();
            List<Document> searchDocuments = new ArrayList<>();
            for (Document d:
                    all) {
                if (d.getName().toLowerCase().contains(keyword.toLowerCase())||
                        d.getDesignation().toLowerCase().contains(keyword.toLowerCase())){
                    searchDocuments.add(d);
                }
            }
            return searchDocuments;
        }


}

