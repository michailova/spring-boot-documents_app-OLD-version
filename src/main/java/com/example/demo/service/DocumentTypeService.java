package com.example.demo.service;


import com.example.demo.model.DocumentType;

import java.util.List;


public interface DocumentTypeService {
    DocumentType addType(DocumentType type);
    DocumentType updateType(int id, DocumentType type);
    DocumentType getById(int id);
    List<DocumentType> getAll();

}
