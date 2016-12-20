package com.flandep.android.sqliteclean.repository.inter;

import android.content.ContentValues;

import com.flandep.android.sqliteclean.DatabaseHelper;
import com.flandep.android.sqliteclean.inter.ITable;

import java.util.List;

public interface IRepository<Entity> extends ITable {
    List<Entity> getEntities(final String selection, final String[] selectionArgs);

    Entity getEntity(final String selection, final String[] selectionArgs);

    long save(final Entity entity);

    long save(final ContentValues contentValues);

    int update(final Entity entity, final String whereClause, final String[] whereArgs);

    int update(final ContentValues contentValues, final String whereClause, final String[] whereArgs);

    int delete(final String whereClause, final String[] whereArgs);

    void registerDatabaseHelper(final DatabaseHelper databaseHelper);

    ContentValues bindValues(final Entity entity);
}