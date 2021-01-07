package com.iratsel.dailydoses.utils;

public enum DatabaseContents {

    DATABASE("17112402.db"),
    TABLE_USERS("user"),
    TABLE_DAIRY("dairy");

    private String name;

    /**
     * Constructs DatabaseContents.
     * @param name name of this content for using in database.
     */
    private DatabaseContents(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}