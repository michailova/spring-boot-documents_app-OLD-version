package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Document;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DocumentRepository documentRepository;



    @Override
    public Department addDepartment(Department department) {
        Department departmentNew = department;
        departmentRepository.save(departmentNew);
        return departmentNew;
    }

    @Override
    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id).get();
    }

    @Override
    public Department getDepartmentByNumber(Integer number) {
        List<Department> departments = departmentRepository.findAll();
        Department findD = null;
        for (Department d :
                departments) {
            if (d.getNumber() == number) {
                findD = d;
            }
        }
        return findD;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> getDepartmentsByDocument(Document document) {
        return (List<Department>) document.getDepartments();
    }


    @Override
    public List<Document> getDocumentsByDepartmentId(int id) {
    Department department = departmentRepository.getReferenceById(id);
    List<Document> documents = documentRepository.findAll();
    List<Document> documentListDepartment = new ArrayList<>();
        for (Document d:
    documents) {
        if (d.getDepartments().contains(department)){
            documentListDepartment.add(d);
        }
    }
    return documentListDepartment;
    }

    @Override
    public Department updateDepartment(Integer id, Department department) {
        Department department1 = departmentRepository.getReferenceById(id);
        department1.setId(id);
        department1.setName(department.getName());
        department1.setStatus(department.getStatus());
        department1.setNumber(department.getNumber());
        departmentRepository.save(department1);
        return department1;
    }


    @Override
    public String deleteDepartment(Integer id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()) {
            departmentRepository.deleteById(id);
            return "Department with id: " + department.get().getId() + " deleted successfully!";
        }
        throw new RuntimeException("Could not delete  user with id:" + department.get().getId());
    }


    public Document addDocumentById(Integer idDep, Integer idDoc){
        List<Document> depDocs = getDocumentsByDepartmentId(idDep);
        depDocs.add(documentRepository.getReferenceById(idDoc));
        return documentRepository.getReferenceById(idDoc);
    }

}
