package com.flandep.android.sqliteclean.inter;

import com.flandep.android.sqliteclean.Database;

public interface ICreateDatabase {

    void onSuccess(final Database database);

    void onError(final Exception exception);
}