package ru.brainix.ept.vkbox.activity;

import android.os.AsyncTask;
import android.util.Log;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.brainix.ept.vkbox.docs.data.DocumentModel;


public class MainModel {

    private String finalCount;

    //Форматируем размер файла в корректные величины
    private String sizeFormatter(int size){

        String ourSize;
        Double d = (Double.valueOf(size)/1024d)/1024d;
        Double d2 = (Double.valueOf(size)/1024d);

        if(d>1){ ourSize = String.format("%.2f", d) + "Mb"; }

           else{ ourSize = String.format("%.0f", d2) + "Kb"; }


        return ourSize;
    }

    //Форматируем дату в понятный формат
    private String dateFormatter(long date){

        Date expiry = new Date( date * 1000 );

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String newDate = dateFormat.format( expiry );

        return newDate;
    }

    //Отдаем готовый список с модельками документов
    public List<DocumentModel> getDocList(String typeDoc){

        DocDataDownlooad mDocDataDownload = new DocDataDownlooad();
        mDocDataDownload.execute(typeDoc);

        List<DocumentModel> doc = new ArrayList<>();

        try {
            doc = mDocDataDownload.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i("MODEL!  size getDoc", ""+doc.size());



        return doc;
    }

    //Публичный метод получения кол-ва файлов
    public String getCount(){

        DocGetCount docGetCount = new DocGetCount();
        docGetCount.execute();

        String outCount = "0";
        try {
            outCount = docGetCount.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        return outCount;
    }



    //Загружам данные документов в список элементов doc
    class DocDataDownlooad extends AsyncTask<String, Void, List<DocumentModel>>{


        List<DocumentModel> doc = new ArrayList<>();

        @Override
        protected List<DocumentModel>  doInBackground(String... strings) {


            VKRequest request = new VKRequest("docs.get", VKParameters.from("type", strings[0]));

            request.executeSyncWithListener(new VKRequest.VKRequestListener() {

                @Override
                public void onComplete(VKResponse response) {

                    try {


                        JSONObject jsonObject = response.json.getJSONObject ("response");
                        JSONArray items = jsonObject.getJSONArray ("items");
                        int count = jsonObject.getInt("count");
                        Log.i("MODEL!  count ", " "+count);

                        if(strings[0].equals("")){



                        }


                        for(int i=0; i<count; i++){

                            jsonObject = items.getJSONObject(i);

                            String title = jsonObject.getString("title");
                            int size = jsonObject.getInt("size");
                            long date = jsonObject.getLong("date");
                            String type = jsonObject.getString("ext");
                            String url = jsonObject.getString("url");
                            String idDoc = jsonObject.getString("id");
                            int typeCat = jsonObject.getInt("type");
                            int ownerId = jsonObject.getInt("owner_id");

                            String sizeFinal = sizeFormatter(size);
                            String dateFinal = dateFormatter(date);

                            doc.add(new DocumentModel(
                                    title,
                                    sizeFinal,
                                    dateFinal,
                                    type,
                                    url,
                                    idDoc,
                                    typeCat,
                                    ownerId));


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                @Override
                public void onError(VKError error) {
                }

                @Override
                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                }
            });

            Log.i("MODEL!  end async", " "+doc.size());
            return doc;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.pbVisible();

        }

        @Override
        protected void onPostExecute(List<DocumentModel> documentModels) {
            super.onPostExecute(documentModels);
            MainActivity.pbInvivible();
        }
    }

    //Метод загрузки кол-ва файлов
    class DocGetCount extends AsyncTask<Void, Void, String>{



        @Override
        protected String doInBackground(Void... voids) {

            finalCount = "0";

            VKRequest request = new VKRequest("docs.get", VKParameters.from("type", "0"));

            request.executeSyncWithListener(new VKRequest.VKRequestListener() {

                @Override
                public void onComplete(VKResponse response) {

                    try {


                        JSONObject jsonObject = response.json.getJSONObject ("response");
                        JSONArray items = jsonObject.getJSONArray ("items");
                        int count = jsonObject.getInt("count");

                        Log.i("MODEL!  count ", " "+count);

                        finalCount = String.valueOf(count);




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                @Override
                public void onError(VKError error) {
                }

                @Override
                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                }
            });

            return finalCount;

        }


    }

}
