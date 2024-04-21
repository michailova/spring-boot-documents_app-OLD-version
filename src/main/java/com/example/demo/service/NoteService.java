package com.example.demo.service;

import com.example.demo.model.Note;

import java.util.List;

public interface NoteService {

    Note addNote(Note note);
    Note getNoteById(Integer id);

    List<Note> getNotesByUserId(Integer id);

    List<Note> getAllNotes();
}
