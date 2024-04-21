package com.example.demo.controller.mvc;

import com.example.demo.dto.UserDto;
import com.example.demo.model.*;
import com.example.demo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/documents")
public class DocumentControllerMVC {

    Logger logger = LoggerFactory.getLogger(DocumentControllerMVC.class);
    @Autowired
    DocumentService documentService;

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = {"/", "/list"})
    public String listDocuments(ModelMap model) {

        List<Document> documents = documentService.
                getAll();
        model.addAttribute("documents", documents);
        return "documentslist";
    }

    @GetMapping(value = { "/all/type-{type}"})
    public String listDocumentsByType(ModelMap model, @PathVariable String type) {

        List<Document> documents = documentService.getAllByType(type);
        model.addAttribute("documents", documents);
        return "documentslist";
    }

    @GetMapping("/addocumentform")
    public String newDocument(Model model) {
        logger.info("add document started");
        Document document = new Document();
        model.addAttribute("document", document);
        return "newdocument";
    }

    @RequestMapping(value = {"/savedocument"}, method = RequestMethod.POST)
    public String saveDocument(@Validated Document document, BindingResult result,
                               ModelMap model) {
        if (document.getValid_until() == null) {
            document.setValid_until((Date) null);
        }

//        if (result.hasErrors()) {
//            return "newdocument";
//
//        }
        documentService.addDocument(document);
        model.addAttribute("success", "Document " + document.getName() + " add successfully");
        List<Document> documents = documentService.getAll();
        model.addAttribute("documents", documents);
        return "documentslist";
    }

    @RequestMapping(value = {"/delete-document-{docId}"}, method = RequestMethod.GET)
    public String deleteDocument(@PathVariable Integer docId) {
        documentService.delete(docId);
        return "redirect:/documents/list";
    }


    @RequestMapping(value = {"/update-document-{docId}"}, method = RequestMethod.GET)
    public String updateDocument(Model model, @PathVariable Integer docId) {
        logger.info("update document started");
        Document document = documentService.getById(docId);
        model.addAttribute("document", document);
        return "updatedocument"; // TODO: 31.03.2024
    }

    @RequestMapping(value = {"/update-document-{docId}"}, method = RequestMethod.POST)
    public String updateDocument(Document document, BindingResult result,
                                 ModelMap model, @PathVariable Integer docId) {

        if (document.getValid_until() == null) {
            document.setValid_until((Date) null);
        }

//        if (result.hasErrors()) {
//            return "newdocument";
//        }
        documentService.updateDocument(docId, document);
        model.addAttribute("success", "Document " + document.getName() + " updated successfully");
        return "redirect:/documents/list";
    }

//    @RequestMapping(value = {"/read-documents-{docId}"}, method = RequestMethod.GET)
//    public String showDocument(@PathVariable Integer docId) {
//        logger.info("read document started");
//        Document document = documentService.getById(docId);
//        File file = new File("D:\\25.03.2024\\spring-boot-documents_app\\src\\main\\resources\\templates\\"
//                + document.getName().toLowerCase() + ".html");
//        if (!file.exists()) {
//            PDFtoHTML.convertToHTML(document.getPath(), document.getName().toLowerCase());
//        } else {
//            return document.getName().toLowerCase();
//        }
//        return document.getName().toLowerCase();
//    }


    @GetMapping(value = {"/all/view-document-{docId}"})
    public String viewDocument(@PathVariable Integer docId, Model model) {
        logger.info("read document started");
        Document document = documentService.getById(docId);
        model.addAttribute("document", document);
        return "read";
    }
    @ModelAttribute("departmentslist")
    public List<Department> initializeDepartments() {
        return departmentService.getAllDepartments();
    }

    @ModelAttribute("documenttypeslist")
    public List<DocumentType> initializeDocumentTypes() {
       return documentTypeService.getAll();
    }

    @ModelAttribute("documentlist")
    public List<Document> initializeDocuments() {
        return documentService.getAll();
    }

    @ModelAttribute("documentstatuslist")
    public List<String> initializeStatus() {
        List<String> statuses = new ArrayList<>();
        statuses.add(DocumentStatus.ACT.getDocumentStatus());
        statuses.add(DocumentStatus.CANCELED.getDocumentStatus());
        statuses.add(DocumentStatus.REVISION_REQUIRED.getDocumentStatus());
        return statuses;
    }
    @RequestMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Document> result = documentService.search(keyword);
        model.addAttribute("result", result);
        return "searchdoc";
    }


    @RequestMapping(value = "/all/view-document-{docId}",method = RequestMethod.POST)
    public String familiarization(Model model, @PathVariable Integer docId) {
        Document document = documentService.getById(docId);
        model.addAttribute("path", document.getPath());
        Note note = new Note();
        note.setDocument(document);
        final String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(currentUserEmail);
        note.setUser(user);
        note.setStatus("Yes");
        note.setTimestamp(new Timestamp(System.currentTimeMillis()));
        noteService.addNote(note);
        model.addAttribute("success", "You are familiarization with Document " + document.getName() + " successfully");
        return "redirect:/documents/list";

    }

    @RequestMapping(value = "/open",method = RequestMethod.GET)
    public String open() {
       return "index";

    }
    @RequestMapping(value = {"/all/repl-{docId}"}, method = RequestMethod.GET)
    public String viewReplDoc(Model model, @PathVariable Integer docId) {
        Document document = documentService.getById(docId).getReplacing_document();
        model.addAttribute("document", document);
        List<Document> documents = new ArrayList<>();
        documents.add(document);
        model.addAttribute("documents", documents);
        return "documentslist";

    }

    @RequestMapping(value = {"/all/canc-{docId}"}, method = RequestMethod.GET)
    public String viewCansDoc(Model model, @PathVariable Integer docId) {
        Document document = documentService.getById(docId).getCanceled_document();
        model.addAttribute("document", document);
        List<Document> documents = new ArrayList<>();
        documents.add(document);
        model.addAttribute("documents", documents);
        return "documentslist";
    }


//        @GetMapping(value = "/image/{docId}", produces = MediaType.IMAGE_JPEG_VALUE)
//        public Resource downloadImage(@PathVariable Integer docId) {
//            byte[] image = documentService.getById(docId).getPath()
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
//                    .getImageData();
//
//            return new ByteArrayResource(image);
//        }

    }


