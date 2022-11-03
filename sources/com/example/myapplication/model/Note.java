package com.example.myapplication.model;

public class Note {
    private boolean checked = false;
    private int id;
    private long noteDate;
    private String noteText;

    public Note() {
    }

    public Note(String noteText2, long noteDate2) {
        this.noteText = noteText2;
        this.noteDate = noteDate2;
    }

    public String getNoteText() {
        return this.noteText;
    }

    public void setNoteText(String noteText2) {
        this.noteText = noteText2;
    }

    public long getNoteDate() {
        return this.noteDate;
    }

    public void setNoteDate(long noteDate2) {
        this.noteDate = noteDate2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked2) {
        this.checked = checked2;
    }

    public String toString() {
        return "Note{id=" + this.id + ", noteDate=" + this.noteDate + '}';
    }
}
