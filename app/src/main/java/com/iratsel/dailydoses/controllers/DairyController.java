package com.iratsel.dailydoses.controllers;

import android.content.ContentValues;

import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseContents;

import java.util.List;

public class DairyController {
    private static Database database;
    private static DairyController instance;

    private DairyController() {}

    public static DairyController getInstance() {
        if (instance == null)
            instance = new DairyController();

        return instance;
    }

    /**
     * Sets database for use in this class.
     * @param db database.
     */
    public static void setDatabase(Database db) {
        database = db;
    }

    public Object getDairy() {
        List<Object> contents = database.select("SELECT * FROM " + DatabaseContents.TABLE_USERS);

        return contents;
    }

    public int add(ContentValues content) {
        int res = database.insert(DatabaseContents.TABLE_DAIRY.toString(), content);

        return res;
    }

    public boolean update(ContentValues content) {
        boolean update = database.update(DatabaseContents.TABLE_USERS.toString(), content);

        return update;
    }
}
