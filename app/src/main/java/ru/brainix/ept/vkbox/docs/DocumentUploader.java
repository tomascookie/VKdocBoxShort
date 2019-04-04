package ru.brainix.ept.vkbox.docs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import ru.brainix.ept.vkbox.MainActivity;
import ru.brainix.ept.vkbox.R;


public class DocumentUploader {

    //Метка для лога
    private final String LOG_TAG = "DocUpload  ";

    //Загружаем файл на сервер
    public void ups(File file, String fileName, Context cntxt){

        //Делаем через метод API с костылем через редактирование т.к
        //В другом случае пришлось бы делать 3 метода с отправкой пост запроса
        //Что дольше и больше

        // Get length of file in bytes
        long fileSizeInBytes = file.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;

        if (200>fileSizeInMB) {


            NotificationManager notificationManager =
                    (NotificationManager) cntxt.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("3", "box!",
                        NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("File upload progress notify");
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(false);
                notificationManager.createNotificationChannel(channel);
            }


            // show notification with indeterminate progressbar
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(cntxt, "3")                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Загрузка")
                            .setContentText("Выполняется")
                            .setProgress(100,0, true);

            notificationManager.notify(1, builder.build());


            VKRequest request = VKApi.docs().uploadDocRequest(file);

            request.executeWithListener(new VKRequest.VKRequestListener() {


                @Override
                public void onComplete(VKResponse response) {

                    builder.setProgress(0, 100, false)
                            .setContentText("Усешно завершена");
                    notificationManager.notify(1, builder.build());

                    Log.i(LOG_TAG, "upload done");

                    String finalFileName = fileName.replaceAll("[()<>'\"]", "");

                    try {

                        //Получаем данные загруженного файла
                        JSONArray items = response.json.getJSONArray("response");

                        JSONObject jsonObject = items.getJSONObject(0);


                        String idDoc = jsonObject.getString("id");
                        String ownerId = jsonObject.getString("owner_id");

                        Log.i(LOG_TAG, "File data: " + idDoc + " " + ownerId + " " + finalFileName);

                        //Редактируем данные устанавливая новое имя
                        DocumentEdit documentEdit = new DocumentEdit();
                        documentEdit.editDoc(cntxt, ownerId, idDoc, finalFileName);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                @Override
                public void onError(VKError error) {
                    Log.e(LOG_TAG, "ERROR " + error.toString());

                    builder.setProgress(0, 100, false)
                            .setContentText("Произошла ошибка");
                    notificationManager.notify(1, builder.build());
                }
            });


        }
        else {

            ((MainActivity)cntxt).fileIncorrect();

        }
}

}
