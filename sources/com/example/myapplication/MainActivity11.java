package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.NotesAdapter;
import com.example.myapplication.callbacks.MainActionModeCallback;
import com.example.myapplication.callbacks.NoteEventListener;
import com.example.myapplication.db.NotesDB;
import com.example.myapplication.db.NotesDao;
import com.example.myapplication.model.Note;
import com.example.myapplication.utils.NoteUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity11 extends AppCompatActivity implements NoteEventListener {
    public static final String APP_PREFERENCES = "notepad_settings";
    private static final String TAG = "MainActivity11";
    public static final String THEME_Key = "app_theme";
    /* access modifiers changed from: private */
    public MainActionModeCallback actionModeCallback;
    /* access modifiers changed from: private */
    public NotesAdapter adapter;
    /* access modifiers changed from: private */
    public int chackedCount = 0;
    /* access modifiers changed from: private */
    public NotesDao dao;
    private FloatingActionButton fab;
    /* access modifiers changed from: private */
    public ArrayList<Note> notes;
    /* access modifiers changed from: private */
    public RecyclerView recyclerView;
    private SharedPreferences settings;
    private ItemTouchHelper swipeToDeleteHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, 12) {
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            Note swipedNote;
            if (MainActivity11.this.notes != null && (swipedNote = (Note) MainActivity11.this.notes.get(viewHolder.getAdapterPosition())) != null) {
                MainActivity11.this.swipeToDelete(swipedNote, viewHolder);
            }
        }
    });
    private int theme;

    static /* synthetic */ int access$108(MainActivity11 x0) {
        int i = x0.chackedCount;
        x0.chackedCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$110(MainActivity11 x0) {
        int i = x0.chackedCount;
        x0.chackedCount = i - 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, 0);
        this.settings = sharedPreferences;
        int i = sharedPreferences.getInt(THEME_Key, R.style.AppTheme);
        this.theme = i;
        setTheme(i);
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main11);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.notes_list);
        this.recyclerView = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        this.fab = floatingActionButton;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity11.this.onAddNewNote();
            }
        });
        this.dao = NotesDB.getInstance(this).notesDao();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        this.notes.addAll(this.dao.getNotes());
        NotesAdapter notesAdapter = new NotesAdapter(this, this.notes);
        this.adapter = notesAdapter;
        notesAdapter.setListener(this);
        this.recyclerView.setAdapter(this.adapter);
        showEmptyView();
        this.swipeToDeleteHelper.attachToRecyclerView(this.recyclerView);
    }

    /* access modifiers changed from: private */
    public void showEmptyView() {
        if (this.notes.size() == 0) {
            this.recyclerView.setVisibility(8);
            findViewById(R.id.empty_notes_view).setVisibility(0);
            return;
        }
        this.recyclerView.setVisibility(0);
        findViewById(R.id.empty_notes_view).setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void onAddNewNote() {
        startActivity(new Intent(this, EditNoteActivity.class));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        loadNotes();
    }

    public void onNoteClick(Note note) {
        Intent edit = new Intent(this, EditNoteActivity.class);
        edit.putExtra(EditNoteActivity.NOTE_EXTRA_Key, note.getId());
        startActivity(edit);
    }

    public void onNoteLongClick(Note note) {
        note.setChecked(true);
        this.chackedCount = 1;
        this.adapter.setMultiCheckMode(true);
        this.adapter.setListener(new NoteEventListener() {
            public void onNoteClick(Note note) {
                note.setChecked(!note.isChecked());
                if (note.isChecked()) {
                    MainActivity11.access$108(MainActivity11.this);
                } else {
                    MainActivity11.access$110(MainActivity11.this);
                }
                if (MainActivity11.this.chackedCount > 1) {
                    MainActivity11.this.actionModeCallback.changeShareItemVisible(false);
                } else {
                    MainActivity11.this.actionModeCallback.changeShareItemVisible(true);
                }
                if (MainActivity11.this.chackedCount == 0) {
                    MainActivity11.this.actionModeCallback.getAction().finish();
                }
                MainActionModeCallback access$200 = MainActivity11.this.actionModeCallback;
                access$200.setCount(MainActivity11.this.chackedCount + "/" + MainActivity11.this.notes.size());
                MainActivity11.this.adapter.notifyDataSetChanged();
            }

            public void onNoteLongClick(Note note) {
                Log.d(MainActivity11.TAG, "onNoteLongClick: " + note.getId());
            }
        });
        AnonymousClass3 r0 = new MainActionModeCallback() {
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_delete_notes) {
                    MainActivity11.this.onDeleteMultiNotes();
                } else if (menuItem.getItemId() == R.id.action_share_note) {
                    MainActivity11.this.onShareNote();
                }
                actionMode.finish();
                return false;
            }
        };
        this.actionModeCallback = r0;
        startActionMode(r0);
        this.fab.setVisibility(8);
        MainActionModeCallback mainActionModeCallback = this.actionModeCallback;
        mainActionModeCallback.setCount(this.chackedCount + "/" + this.notes.size());
    }

    /* access modifiers changed from: private */
    public void onShareNote() {
        Note note = this.adapter.getCheckedNotes().get(0);
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("text/plain");
        share.putExtra("android.intent.extra.TEXT", note.getNoteText() + "\n\n Create on : " + NoteUtils.dateFromLong(note.getNoteDate()) + "\n  By :" + getString(R.string.app_name));
        startActivity(share);
    }

    /* access modifiers changed from: private */
    public void onDeleteMultiNotes() {
        List<Note> chackedNotes = this.adapter.getCheckedNotes();
        if (chackedNotes.size() != 0) {
            for (Note note : chackedNotes) {
                this.dao.deleteNote(note);
            }
            loadNotes();
            Toast.makeText(this, chackedNotes.size() + " Catatan Berhasil Dihapus !", 0).show();
            return;
        }
        Toast.makeText(this, "Catatan Belum Terpilih", 0).show();
    }

    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        this.adapter.setMultiCheckMode(false);
        this.adapter.setListener(this);
        this.fab.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void swipeToDelete(final Note swipedNote, final RecyclerView.ViewHolder viewHolder) {
        new AlertDialog.Builder(this).setMessage((CharSequence) "Delete Note?").setPositiveButton((CharSequence) "Delete", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity11.this.dao.deleteNote(swipedNote);
                MainActivity11.this.notes.remove(swipedNote);
                MainActivity11.this.adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                MainActivity11.this.showEmptyView();
            }
        }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity11.this.recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
            }
        }).setCancelable(false).create().show();
    }
}
