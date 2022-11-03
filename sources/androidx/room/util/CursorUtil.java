package androidx.room.util;

import android.database.Cursor;
import android.database.MatrixCursor;

public class CursorUtil {
    public static Cursor copyAndClose(Cursor c) {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(c.getColumnNames(), c.getCount());
            while (c.moveToNext()) {
                Object[] row = new Object[c.getColumnCount()];
                for (int i = 0; i < c.getColumnCount(); i++) {
                    int type = c.getType(i);
                    if (type == 0) {
                        row[i] = null;
                    } else if (type == 1) {
                        row[i] = Long.valueOf(c.getLong(i));
                    } else if (type == 2) {
                        row[i] = Double.valueOf(c.getDouble(i));
                    } else if (type == 3) {
                        row[i] = c.getString(i);
                    } else if (type == 4) {
                        row[i] = c.getBlob(i);
                    } else {
                        throw new IllegalStateException();
                    }
                }
                matrixCursor.addRow(row);
            }
            return matrixCursor;
        } finally {
            c.close();
        }
    }

    public static int getColumnIndex(Cursor c, String name) {
        int index = c.getColumnIndex(name);
        if (index >= 0) {
            return index;
        }
        return c.getColumnIndex("`" + name + "`");
    }

    public static int getColumnIndexOrThrow(Cursor c, String name) {
        int index = c.getColumnIndex(name);
        if (index >= 0) {
            return index;
        }
        return c.getColumnIndexOrThrow("`" + name + "`");
    }

    private CursorUtil() {
    }
}
