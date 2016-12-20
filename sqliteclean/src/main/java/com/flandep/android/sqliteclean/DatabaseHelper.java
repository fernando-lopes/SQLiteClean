package com.flandep.android.sqliteclean;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.flandep.android.sqliteclean.inter.IDatabaseHelper;
import com.flandep.android.sqliteclean.inter.ITable;

import java.util.List;

public class DatabaseHelper implements IDatabaseHelper {
    private static final String TAG = "DatabaseHelper";

    private final SQLiteOpenHelper mOpenHelper;
    private final ITable mListener;

    public DatabaseHelper(final SQLiteOpenHelper openHelper, final ITable listener) {
        mOpenHelper = openHelper;
        mListener = listener;
    }

    @Override
    public void onCreate(final SQLiteDatabase database) {
        final List<String> scripts = mListener.getScriptsCreate();
        if (scripts == null || scripts.size() == 0)
            return;

        for (final String script : scripts) {
            database.execSQL(script);
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        final List<String> scripts = mListener.getScriptsUpgrade(oldVersion, newVersion);
        if (scripts == null || scripts.size() == 0)
            return;

        for (final String script : scripts) {
            database.execSQL(script);
        }
    }

    public long insert(final String table, final ContentValues contentValues) {
        final SQLiteDatabase database = getDatabase();
        long id = -1;
        try {
            id = database.insert(table, null, contentValues);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            database.close();
        }
        return id;
    }

    public int update(final String table, final ContentValues contentValues, final String whereClause, final String[] whereArgs) {
        final SQLiteDatabase database = getDatabase();
        int affectedRows = -1;
        try {
            affectedRows = database.update(table, contentValues, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            database.close();
        }
        return affectedRows;
    }

    public int delete(final String table, final String whereClause, final String[] whereArgs) {
        final SQLiteDatabase database = getDatabase();
        int affectedRows = -1;
        try {
            affectedRows = database.delete(table, whereClause, whereArgs);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            database.close();
        }
        return affectedRows;
    }

    public Cursor query(final boolean distinct, final String tableName, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final String limit) {
        return getDatabase().query(distinct, tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    private SQLiteDatabase getDatabase() {
        return mOpenHelper.getWritableDatabase();
    }
}
