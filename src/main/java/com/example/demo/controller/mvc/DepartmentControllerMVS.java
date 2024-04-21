package com.example.demo.controller.mvc;

import com.example.demo.model.Department;
import com.example.demo.model.Document;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentControllerMVS {
    Logger logger = LoggerFactory.getLogger(DepartmentControllerMVS.class);
    @Autowired
    DepartmentService departmentService;

    @Autowired
    DocumentService documentService;

    @GetMapping(value = {"/", "/list"})
    public String listDepartments(ModelMap model) {

        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "departmentform";
    }


    @GetMapping("/newdepartmentform")
    public String newDepartment(Model model) {
        logger.info("newDepartment started");
        Department department = new Department();
        model.addAttribute("department", department);
        return "newdepartment";
    }
    @RequestMapping(value = {"/addnewdepartment"}, method = RequestMethod.POST)
    public String saveDepartment(Department department, BindingResult result,
                                 ModelMap model) {

        if (result.hasErrors()) {
            return "newdepartment";

        }

        departmentService.addDepartment(department);
        model.addAttribute("success", "Department " + department.getName() + " add successfully");
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "departmentform";

    }

    @RequestMapping(value = {"/delete-department-{depId}"}, method = RequestMethod.GET)
    public String deleteDepartment(@PathVariable Integer depId) {
        departmentService.deleteDepartment(depId);
        return "redirect:/departments/list";
    }


    @RequestMapping(value = {"/update-department-{depId}"}, method = RequestMethod.GET)
    public String updateDepartment(Model model, @PathVariable Integer depId) {
        logger.info("updateDepartment started");
        Department department = departmentService.getDepartmentById(depId);
        model.addAttribute("department", department);
        return "updatedepartment";
    }

    @RequestMapping(value = {"/update-department-{depId}"}, method = RequestMethod.POST)
    public String updateDepartment(Department department, BindingResult result,
                             ModelMap model, @PathVariable Integer depId) {

        if (result.hasErrors()) {
            return "newdepartment";
        }

        departmentService.updateDepartment(depId, department);

        model.addAttribute("success", "Department " + department.getName() +  " updated successfully");
        return "redirect:/departments/list";
    }

    @RequestMapping(value = {"/view-documents-{depId}"}, method = RequestMethod.GET)
    public String showDepartmentDocument(Model model, @PathVariable Integer depId) {
        logger.info("showDepartmentDocument started");
        List<Document> documents = departmentService.getDocumentsByDepartmentId(depId);
        model.addAttribute("documents", documents);
        return "documentslist";
    }

    @RequestMapping(value = {"/adddocument-{depId}"}, method = RequestMethod.GET)
    public String addDepartmentDocument(Model model, @PathVariable Integer depId) {
        logger.info("addDepartmentDocument started");
        Department department = departmentService.getDepartmentById(depId);
        model.addAttribute("department", department);
        return "adddocform";
    }

    @RequestMapping(value = {"/add-{docId}-{depId}"}, method = RequestMethod.POST)
    public String addDocument(Department department, @PathVariable Integer docId) {
//        List<Document> documents = departmentService.getDocumentsByDepartmentId(department.getId());
//        documents.add(documentService.getById(docId));
        departmentService.addDocumentById(department.getId(), docId);
        return "redirect:/departments/adddocform";
    }
    @ModelAttribute("documentlist")
    public List<Document> initializeDocuments() {
        return documentService.getAll();
    }

}

