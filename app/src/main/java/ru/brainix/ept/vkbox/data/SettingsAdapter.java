package ru.brainix.ept.vkbox.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import ru.brainix.ept.vkbox.data.SettingsBaseContract.SettingsParamName;


public class SettingsAdapter {

    private SettingsDbHelper dbHelper;
    private SQLiteDatabase database;

    //Устанавливаем дб-хелпер в адаптере
    public SettingsAdapter(Context context){
        dbHelper = new SettingsDbHelper(context.getApplicationContext());
    }

    //Устанавливаем хелпер на запись/чтение
    public SettingsAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    //Закрываем хелпер
    public void close(){
        dbHelper.close();
    }


    //Получаем поле по id
    public SettingModel getData(int id){

        SettingModel user = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", SettingsParamName.TABLE_NAME, SettingsParamName._ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});

        if(cursor.moveToFirst()){

            String name = cursor.getString(cursor.getColumnIndex(SettingsParamName.COLUMN_NAME));
            int year = cursor.getInt(cursor.getColumnIndex(SettingsParamName.COLUMN_STATE));

            user = new SettingModel(id, name, year);
        }
        cursor.close();
        return  user;
    }

    //Удаляем элемент по id
    public long delete(int userId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(SettingsParamName.TABLE_NAME, whereClause, whereArgs);
    }

    //Добавляем элемент в БД
    public long insert(SettingModel user){

        ContentValues cv = new ContentValues();
        cv.put(SettingsParamName.COLUMN_NAME, user.getName());
        cv.put(SettingsParamName.COLUMN_STATE, user.getState());

        return  database.insert(SettingsParamName.TABLE_NAME, null, cv);
    }

    //Обновляем поле в БД
    public long update(SettingModel user){

        String whereClause = SettingsParamName._ID + "=" + String.valueOf(user.getId());
        ContentValues cv = new ContentValues();
        cv.put(SettingsParamName.COLUMN_NAME, user.getName());
        cv.put(SettingsParamName.COLUMN_STATE, user.getState());
        return database.update(SettingsParamName.TABLE_NAME, cv, whereClause, null);

    }
}