package com.example.demo.service;

import com.example.demo.model.Document;
import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserService userService;

    @Override
    public Note addNote(Note note) {
        Note noteSave = new Note() ;
        User user = note.getUser();
        Document document = note.getDocument();
        List<Document> documents = user.getDocuments();
        documents.remove(document);
        user.setDocuments(documents);
        userService.updateUser(user.getId(), userService.mapEntityToDtoUser(user));
        return noteRepository.save(note);

    }

    @Override
    public Note getNoteById(Integer id) {
        return noteRepository.getReferenceById(id);
    }


    @Override
    public List<Note> getNotesByUserId(Integer id) {
        List<Note> all = getAllNotes();
        List<Note> allById = new ArrayList<>();
        for (Note n:
             all) {
            if(n.getUser().getId() == id){
                allById.add(n);
            }
        }
        return allById;
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
}
