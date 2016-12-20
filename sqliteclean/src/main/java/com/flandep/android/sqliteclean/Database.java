package com.flandep.android.sqliteclean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.flandep.android.sqliteclean.inter.IDatabaseHelper;
import com.flandep.android.sqliteclean.repository.inter.IRepository;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String TAG = "Database";

    private final String mName;
    private final int mVersion;

    public Database(Builder builder) {
        mName = builder.mName;
        mVersion = builder.mVersion;
    }

    public String getName() {
        return mName;
    }

    public int getVersion() {
        return mVersion;
    }

    public static class Builder extends SQLiteOpenHelper {
        private final String mName;
        private final int mVersion;
        private final List<IDatabaseHelper> mDatabaseHelper;

        public Builder(final Context context, final String name, final int version) {
            super(context, name, null, version);
            mName = name;
            mVersion = version;
            mDatabaseHelper = new ArrayList<>();
        }

        @Override
        public void onCreate(final SQLiteDatabase database) {
            for (IDatabaseHelper databaseHelper : mDatabaseHelper) {
                databaseHelper.onCreate(database);
            }
        }

        @Override
        public void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
            for (IDatabaseHelper databaseHelper : mDatabaseHelper) {
                databaseHelper.onUpgrade(database, oldVersion, newVersion);
            }
        }

        public Builder addRepository(final IRepository repository) {
            final DatabaseHelper databaseHelper = new DatabaseHelper(this, repository);
            repository.registerDatabaseHelper(databaseHelper);
            mDatabaseHelper.add(databaseHelper);
            return this;
        }

        public Database build() {
            try {
                final SQLiteDatabase openHelper = getWritableDatabase();
                if (openHelper != null) {
                    openHelper.close();
                }
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            } finally {
                return new Database(this);
            }
        }
    }
}
