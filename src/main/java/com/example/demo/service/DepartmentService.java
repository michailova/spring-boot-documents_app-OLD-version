package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Document;

import java.util.List;

public interface DepartmentService {

    Department addDepartment(Department department);

    Department getDepartmentById(Integer id);
    Department getDepartmentByNumber(Integer number);

    List<Department> getAllDepartments();

    List<Department> getDepartmentsByDocument(Document document);

    List<Document> getDocumentsByDepartmentId(int id);

    Department updateDepartment(Integer id, Department department);

    String deleteDepartment(Integer id);

    Document addDocumentById(Integer idDep, Integer idDoc);

}
