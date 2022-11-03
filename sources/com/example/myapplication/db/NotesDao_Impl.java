package com.example.myapplication.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.myapplication.model.Note;
import java.util.ArrayList;
import java.util.List;

public final class NotesDao_Impl implements NotesDao {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter<Note> __deletionAdapterOfNote;
    private final EntityInsertionAdapter<Note> __insertionAdapterOfNote;
    private final SharedSQLiteStatement __preparedStmtOfDeleteNoteById;
    private final EntityDeletionOrUpdateAdapter<Note> __updateAdapterOfNote;

    public NotesDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db2) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `notes` (`id`,`text`,`date`) VALUES (nullif(?, 0),?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, Note value) {
                stmt.bindLong(1, (long) value.getId());
                if (value.getNoteText() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getNoteText());
                }
                stmt.bindLong(3, value.getNoteDate());
            }
        };
        this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db2) {
            public String createQuery() {
                return "DELETE FROM `notes` WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement stmt, Note value) {
                stmt.bindLong(1, (long) value.getId());
            }
        };
        this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db2) {
            public String createQuery() {
                return "UPDATE OR ABORT `notes` SET `id` = ?,`text` = ?,`date` = ? WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement stmt, Note value) {
                stmt.bindLong(1, (long) value.getId());
                if (value.getNoteText() == null) {
                    stmt.bindNull(2);
                } else {
                    stmt.bindString(2, value.getNoteText());
                }
                stmt.bindLong(3, value.getNoteDate());
                stmt.bindLong(4, (long) value.getId());
            }
        };
        this.__preparedStmtOfDeleteNoteById = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "DELETE FROM notes WHERE id = ?";
            }
        };
    }

    public void insertNote(Note note) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfNote.insert(note);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void deleteNote(Note... note) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfNote.handleMultiple((T[]) note);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void updateNote(Note note) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfNote.handle(note);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void deleteNoteById(int noteId) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDeleteNoteById.acquire();
        _stmt.bindLong(1, (long) noteId);
        this.__db.beginTransaction();
        try {
            _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteNoteById.release(_stmt);
        }
    }

    public List<Note> getNotes() {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM notes", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
            int _cursorIndexOfNoteDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            List<Note> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                Note _item = new Note();
                _item.setId(_cursor.getInt(_cursorIndexOfId));
                _item.setNoteText(_cursor.getString(_cursorIndexOfNoteText));
                _item.setNoteDate(_cursor.getLong(_cursorIndexOfNoteDate));
                _result.add(_item);
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public Note getNoteById(int noteId) {
        Note _result;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * FROM notes WHERE id = ?", 1);
        _statement.bindLong(1, (long) noteId);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
            int _cursorIndexOfNoteDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            if (_cursor.moveToFirst()) {
                _result = new Note();
                _result.setId(_cursor.getInt(_cursorIndexOfId));
                _result.setNoteText(_cursor.getString(_cursorIndexOfNoteText));
                _result.setNoteDate(_cursor.getLong(_cursorIndexOfNoteDate));
            } else {
                _result = null;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }
}
