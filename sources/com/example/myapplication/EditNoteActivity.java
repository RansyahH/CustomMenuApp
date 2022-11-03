package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.myapplication.db.NotesDB;
import com.example.myapplication.db.NotesDao;
import com.example.myapplication.model.Note;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    public static final String NOTE_EXTRA_Key = "note_id";
    private NotesDao dao;
    private EditText inputNote;
    private Note temp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        setTheme(getSharedPreferences(MainActivity11.APP_PREFERENCES, 0).getInt(MainActivity11.THEME_Key, R.style.AppTheme));
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_edite_note);
        setSupportActionBar((Toolbar) findViewById(R.id.edit_note_activity_toolbar));
        this.inputNote = (EditText) findViewById(R.id.input_note);
        this.dao = NotesDB.getInstance(this).notesDao();
        if (getIntent().getExtras() != null) {
            Note noteById = this.dao.getNoteById(getIntent().getExtras().getInt(NOTE_EXTRA_Key, 0));
            this.temp = noteById;
            this.inputNote.setText(noteById.getNoteText());
            return;
        }
        this.inputNote.setFocusable(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edite_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            onSaveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        String text = this.inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();
            Note note = this.temp;
            if (note == null) {
                Note note2 = new Note(text, date);
                this.temp = note2;
                this.dao.insertNote(note2);
            } else {
                note.setNoteText(text);
                this.temp.setNoteDate(date);
                this.dao.updateNote(this.temp);
            }
            finish();
        }
    }
}
