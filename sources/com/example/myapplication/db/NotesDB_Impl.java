package com.example.myapplication.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.HashMap;
import java.util.HashSet;

public final class NotesDB_Impl extends NotesDB {
    private volatile NotesDao _notesDao;

    /* access modifiers changed from: protected */
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
        return configuration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(configuration.context).name(configuration.name).callback(new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
            public void createAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `text` TEXT, `date` INTEGER NOT NULL)");
                _db.execSQL(RoomMasterTable.CREATE_QUERY);
                _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'eda4b3710308a25783ba2532fb49c242')");
            }

            public void dropAllTables(SupportSQLiteDatabase _db) {
                _db.execSQL("DROP TABLE IF EXISTS `notes`");
                if (NotesDB_Impl.this.mCallbacks != null) {
                    int _size = NotesDB_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((RoomDatabase.Callback) NotesDB_Impl.this.mCallbacks.get(_i)).onDestructiveMigration(_db);
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onCreate(SupportSQLiteDatabase _db) {
                if (NotesDB_Impl.this.mCallbacks != null) {
                    int _size = NotesDB_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((RoomDatabase.Callback) NotesDB_Impl.this.mCallbacks.get(_i)).onCreate(_db);
                    }
                }
            }

            public void onOpen(SupportSQLiteDatabase _db) {
                SupportSQLiteDatabase unused = NotesDB_Impl.this.mDatabase = _db;
                NotesDB_Impl.this.internalInitInvalidationTracker(_db);
                if (NotesDB_Impl.this.mCallbacks != null) {
                    int _size = NotesDB_Impl.this.mCallbacks.size();
                    for (int _i = 0; _i < _size; _i++) {
                        ((RoomDatabase.Callback) NotesDB_Impl.this.mCallbacks.get(_i)).onOpen(_db);
                    }
                }
            }

            public void onPreMigrate(SupportSQLiteDatabase _db) {
                DBUtil.dropFtsSyncTriggers(_db);
            }

            public void onPostMigrate(SupportSQLiteDatabase _db) {
            }

            /* access modifiers changed from: protected */
            public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
                HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<>(3);
                _columnsNotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, (String) null, 1));
                _columnsNotes.put("text", new TableInfo.Column("text", "TEXT", false, 0, (String) null, 1));
                _columnsNotes.put("date", new TableInfo.Column("date", "INTEGER", true, 0, (String) null, 1));
                TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, new HashSet<>(0), new HashSet<>(0));
                TableInfo _existingNotes = TableInfo.read(_db, "notes");
                if (_infoNotes.equals(_existingNotes)) {
                    return new RoomOpenHelper.ValidationResult(true, (String) null);
                }
                return new RoomOpenHelper.ValidationResult(false, "notes(com.example.myapplication.model.Note).\n Expected:\n" + _infoNotes + "\n Found:\n" + _existingNotes);
            }
        }, "eda4b3710308a25783ba2532fb49c242", "dd0e7652c67d323463ae50fd8a8372ea")).build());
    }

    /* access modifiers changed from: protected */
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap<>(0), new HashMap<>(0), "notes");
    }

    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            _db.execSQL("DELETE FROM `notes`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            _db.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!_db.inTransaction()) {
                _db.execSQL("VACUUM");
            }
        }
    }

    public NotesDao notesDao() {
        NotesDao notesDao;
        if (this._notesDao != null) {
            return this._notesDao;
        }
        synchronized (this) {
            if (this._notesDao == null) {
                this._notesDao = new NotesDao_Impl(this);
            }
            notesDao = this._notesDao;
        }
        return notesDao;
    }
}
