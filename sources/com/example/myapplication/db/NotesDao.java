package com.example.myapplication.db;

import com.example.myapplication.model.Note;
import java.util.List;

public interface NotesDao {
    void deleteNote(Note... noteArr);

    void deleteNoteById(int i);

    Note getNoteById(int i);

    List<Note> getNotes();

    void insertNote(Note note);

    void updateNote(Note note);
}
