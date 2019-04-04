package ru.brainix.ept.vkbox.data;

import android.provider.BaseColumns;

public final class SettingsBaseContract {

    private SettingsBaseContract(){}

    public static final class SettingsParamName implements BaseColumns {

        public final static String TABLE_NAME   = "settings";
        public final static String COLUMN_NAME  = "name";
        public final static String COLUMN_STATE = "state";

        public final static String _ID = BaseColumns._ID;

        public static final String STATE_TRUE  = "1";
        public static final String STATE_FALSE = "0";
        public static final String AUTENT_NAME = "Autentification State";

    }
}