package ru.brainix.ept.vkbox.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ru.brainix.ept.vkbox.database.SettingsBaseContract.SettingsParamName;

public class SettingsDbHelper extends SQLiteOpenHelper {


    public static final String LOG_TAG = SettingsDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "settings.db";

    private static final int DATABASE_VERSION = 1;


    public SettingsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

         String SQL_CREATE_SETTINGS_TABLE = "CREATE TABLE "
                + SettingsParamName.TABLE_NAME + " ("
                + SettingsParamName._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SettingsParamName.COLUMN_NAME + " TEXT NOT NULL, "
                + SettingsParamName.COLUMN_STATE + " INTEGER NOT NULL DEFAULT 0);";

         String SQL_SET_SETTINGS_TABLE = "INSERT INTO "
                + SettingsParamName.TABLE_NAME +" ("
                + SettingsParamName.COLUMN_NAME + ", "
                + SettingsParamName.COLUMN_STATE + ") "
                + "VALUES ('"
                + SettingsParamName.AUTENT_NAME + "', "
                + SettingsParamName.STATE_FALSE + ");" ;

        db.execSQL(SQL_CREATE_SETTINGS_TABLE);

        db.execSQL(SQL_SET_SETTINGS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SettingsParamName.TABLE_NAME);
        onCreate(db);
    }
}