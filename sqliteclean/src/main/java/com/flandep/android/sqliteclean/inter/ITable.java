package com.flandep.android.sqliteclean.inter;

import java.util.List;

public interface ITable {
    String getTableName();

    List<String> getScriptsCreate();

    List<String> getScriptsUpgrade(int oldVersion, int newVersion);
}