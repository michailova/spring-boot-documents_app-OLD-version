package com.example.demo.service;



import com.example.demo.dto.UserDto;
import com.example.demo.model.Document;

import java.util.List;

public interface DocumentService {
    Document addDocument(Document document);
    Document updateDocument(int id, Document document);
    Document getById(int id);
    List<Document> getAll();

    List<Document> getAllByType(String Type);


    String delete(Integer docId);

    List<Document> search(String keyword);


}
