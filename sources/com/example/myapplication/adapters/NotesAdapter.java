package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.callbacks.NoteEventListener;
import com.example.myapplication.model.Note;
import com.example.myapplication.utils.NoteUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {
    private Context context;
    /* access modifiers changed from: private */
    public NoteEventListener listener;
    private boolean multiCheckMode = false;
    private ArrayList<Note> notes;

    public NotesAdapter(Context context2, ArrayList<Note> notes2) {
        this.context = context2;
        this.notes = notes2;
    }

    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(this.context).inflate(R.layout.note_layout, parent, false));
    }

    public void onBindViewHolder(NoteHolder holder, int position) {
        final Note note = getNote(position);
        if (note != null) {
            holder.noteText.setText(note.getNoteText());
            holder.noteDate.setText(NoteUtils.dateFromLong(note.getNoteDate()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    NotesAdapter.this.listener.onNoteClick(note);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    NotesAdapter.this.listener.onNoteLongClick(note);
                    return false;
                }
            });
            if (this.multiCheckMode) {
                holder.checkBox.setVisibility(0);
                holder.checkBox.setChecked(note.isChecked());
                return;
            }
            holder.checkBox.setVisibility(8);
        }
    }

    public int getItemCount() {
        return this.notes.size();
    }

    private Note getNote(int position) {
        return this.notes.get(position);
    }

    public List<Note> getCheckedNotes() {
        List<Note> checkedNotes = new ArrayList<>();
        Iterator<Note> it = this.notes.iterator();
        while (it.hasNext()) {
            Note n = it.next();
            if (n.isChecked()) {
                checkedNotes.add(n);
            }
        }
        return checkedNotes;
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView noteDate;
        TextView noteText;

        public NoteHolder(View itemView) {
            super(itemView);
            this.noteDate = (TextView) itemView.findViewById(R.id.note_date);
            this.noteText = (TextView) itemView.findViewById(R.id.note_text);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    public void setListener(NoteEventListener listener2) {
        this.listener = listener2;
    }

    public void setMultiCheckMode(boolean multiCheckMode2) {
        this.multiCheckMode = multiCheckMode2;
        if (!multiCheckMode2) {
            Iterator<Note> it = this.notes.iterator();
            while (it.hasNext()) {
                it.next().setChecked(false);
            }
        }
        notifyDataSetChanged();
    }
}
