package com.example.myapplication.callbacks;

import com.example.myapplication.model.Note;

public interface NoteEventListener {
    void onNoteClick(Note note);

    void onNoteLongClick(Note note);
}
