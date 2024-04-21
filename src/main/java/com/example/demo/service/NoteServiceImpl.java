package com.example.demo.service;

import com.example.demo.model.Note;
import com.example.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    NoteRepository noteRepository;

    @Override
    public Note addNote(Note note) {
        Note noteSave = new Note() ;
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
