package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Document;
import com.example.demo.model.DocumentType;
import com.example.demo.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    DocumentTypeRepository documentTypeRepository;


    @Override
    public DocumentType addType(DocumentType type) {
        DocumentType documentType = type;
        documentTypeRepository.save(documentType);
        return documentType;
    }

    @Override
    public DocumentType updateType(int id, DocumentType type) {
        DocumentType documentType = new DocumentType();
        documentType.setId(id);
        documentType.setName(type.getName());
        documentTypeRepository.save(documentType);
        return documentType;
    }

    @Override
    public DocumentType getById(int id) {
        return documentTypeRepository.findById(id).get();
    }

    @Override
    public List<DocumentType> getAll() {
        return documentTypeRepository.findAll();
    }
}
