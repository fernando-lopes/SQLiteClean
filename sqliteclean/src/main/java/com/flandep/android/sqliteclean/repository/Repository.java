package com.flandep.android.sqliteclean.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.flandep.android.sqliteclean.DatabaseHelper;
import com.flandep.android.sqliteclean.repository.inter.IRepository;

import java.util.List;

public abstract class Repository<Entity> implements IRepository<Entity> {
    private DatabaseHelper mDatabaseHelper;

    public Repository() {
        mDatabaseHelper = null;
    }

    @Override
    public final long save(final Entity entity) {
        return save(bindValues(entity));
    }

    @Override
    public final long save(final ContentValues contentValues) {
        return mDatabaseHelper.insert(getTableName(), contentValues);
    }

    @Override
    public final int update(final Entity entity, final String whereClause, final String[] whereArgs) {
        return update(bindValues(entity), whereClause, whereArgs);
    }

    @Override
    public final int update(final ContentValues contentValues, final String whereClause, final String[] whereArgs) {
        return mDatabaseHelper.update(getTableName(), contentValues, whereClause, whereArgs);
    }

    @Override
    public final int delete(final String whereClause, final String[] whereArgs) {
        return mDatabaseHelper.delete(getTableName(), whereClause, whereArgs);
    }

    @Override
    public List<String> getScriptsUpgrade(final int oldVersion, final int newVersion) {
        return null;
    }

    @Override
    public Entity getEntity(final String selection, final String[] selectionArgs) {
        final List<Entity> entities = getEntities(selection, selectionArgs);
        return entities != null && entities.size() == 1 ? entities.get(0) : null;
    }

    @Override
    public final void registerDatabaseHelper(final DatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
    }

    protected final Cursor query(final boolean distinct, final String tableName, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final String limit) {
        return mDatabaseHelper.query(distinct, tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }
}
