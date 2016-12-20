package com.flandep.android.sqliteclean;

import android.content.Context;
import android.os.AsyncTask;

import com.flandep.android.sqliteclean.inter.ICreateDatabase;
import com.flandep.android.sqliteclean.repository.inter.IRepository;

public class CreateDatabase extends AsyncTask<IRepository, Void, Database> {

    private final Context mContext;
    private final String mDatabaseName;
    private final int mVersion;
    private final ICreateDatabase mListener;

    private CreateDatabase(final Context context, final String databaseName, final int version, final ICreateDatabase listener) {
        mContext = context;
        mDatabaseName = databaseName;
        mVersion = version;
        mListener = listener;
    }

    @Override
    protected Database doInBackground(final IRepository... repositories) {
        final Database.Builder database = new Database.Builder(mContext, mDatabaseName, mVersion);
        for (final IRepository repository : repositories) {
            database.addRepository(repository);
        }
        return database.build();
    }

    @Override
    protected void onPostExecute(final Database database) {
        super.onPostExecute(database);
        mListener.onSuccess(database);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mListener.onError(new Exception("Create Database Cancelled"));
    }

    public static CreateDatabase newInstance(final Context context, final String databaseName, final int version, final ICreateDatabase listener) {
        return new CreateDatabase(context, databaseName, version, listener);
    }
}