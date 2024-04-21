package com.example.demo.controller;


import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public Department findDepartmentById(@PathVariable(value = "id") int id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public Department saveDepartment(@RequestBody Department department) {
        Department result = null;
        try {
            result = departmentService.addDepartment(department);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable(value = "id") int id, @RequestBody Department department) {
        Department result = null;
        try {
            result = departmentService.updateDepartment(id, department);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable(name = "id") int id) {
        String message = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}


