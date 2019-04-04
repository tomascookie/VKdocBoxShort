package ru.brainix.ept.vkbox.docs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;


public class DocumentDownload {

    //Метка для лога
    private final String LOG_TAG = "DownloadDoc  ";

    //Метод для открытия ссылки
    public void docDownload(String url, Context cntxt){

        //Отправляем интент с запросом браузера
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        cntxt.startActivity(browserIntent);

        Log.i(LOG_TAG, "url was opened");
    }

}


