package ru.brainix.ept.vkbox.docs;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ru.brainix.ept.vkbox.R;


public class DocumentCopyUrl {

    //Метка для лога
    private final String LOG_TAG = "CopyUrlAct ";

    //Метод копирования адреса в буфер обмена
    public void docCopy(String url, Context cntxt){

        ClipboardManager clipboard = (ClipboardManager) cntxt.getSystemService(cntxt.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("CopiedText", url);

        clipboard.setPrimaryClip(clip);

        Log.i(LOG_TAG,"url was copied in buffer");

        Toast.makeText(cntxt, R.string.copy_url, Toast.LENGTH_SHORT).show();

    }


}
