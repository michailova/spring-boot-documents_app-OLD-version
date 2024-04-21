//package com.example.demo.controller;
//
//import com.journaldev.spring.dao.DocumentDao;
//import com.journaldev.spring.model.Document;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import java.util.List;
//
//
//@RestController
//    @EnableWebMvc
//    public class DocumentRestController {
//        private static final Logger logger = LogManager.getLogger(DocumentRestController.class);
//
//        @Autowired
//        private DocumentDao documentDao; //will inject dao from XML file
//
//        @GetMapping("/JSONdocuments")
//        public List<Document> documents() {
//            List<Document> documents = documentDao.getAll();
//            return documents;
//        }
//
//        @GetMapping("/JSONdocumentbyid")
//        public Document documentById(int id) {
//            Document d = documentDao.getDocumentById(id);
//            return d;
//        } // TODO: 11.02.2024
//
////    @GetMapping("/JSONdocumentbyid/{id}")
////    public ResponseEntity<Document> documentById(@PathVariable("id") int id){
////        Document d = documentDao.getDocumentById(id);
////        return new ResponseEntity<>(d, HttpStatus.OK);
////    }
//
//
//        @PutMapping("/JSONsavedocument")
//        public String put(@RequestBody Document d) {
//            try {
//                documentDao.save(d);
//                logger.info("Document" + d.getDesignation()+ " " + d.getName()+ " - saved successfully");
//            } catch (Throwable t) {
//                logger.error("Document" + d.getDesignation()+ " " + d.getName()+ " - save error. Details: " + t.getMessage());
//                return "error";
//            }
//            return "success";
//        } // TODO: 11.02.2024
//
//
//        @PostMapping("/JSONeditdocument")
//        public String doPost(@RequestBody Document d) {
//            try {
//                int res = documentDao.update(d);
//                logger.info("Document", d.getDesignation()+ " ' " + d.getName()+ "' - updated successfully");
//            } catch (Throwable t) {
//                logger.error("Document", d.getDesignation()+ " ' " + d.getName()+ "' - save error. Details: " + t.getMessage());
//                return "error";
//            }
//            return "success";
//        } // TODO: 11.02.2024
//
//
//        @DeleteMapping("/JSONdeletedocument")
//        public String delete(@RequestParam int id) {
//            try {
//                documentDao.delete(id);
//                logger.info("Document with id - " + id + " deleted successfully.");
//            } catch (Throwable t) {
//                logger.error("Document with id - "  + id + " delete error. Details: " + t.getMessage());
//                return "error";
//            }
//            return "success";
//        }
//    }
//
