package com.flandep.android.sqliteclean.inter;

import android.database.sqlite.SQLiteDatabase;

public interface IDatabaseHelper {
    void onCreate(final SQLiteDatabase database);

    void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion);
}
