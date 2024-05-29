package com.example.greenscape;

import android.provider.BaseColumns;

public final class UserContract {
    private UserContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_ADDRESS = "address";
    }
}
