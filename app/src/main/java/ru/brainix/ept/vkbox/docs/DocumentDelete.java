package ru.brainix.ept.vkbox.docs;

import android.os.AsyncTask;
import android.util.Log;


import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.concurrent.ExecutionException;


public class DocumentDelete {

    //Название для лога
    private final String LOG_TAG = "DocumentDelete ";

    //Публичный метод для запуска удаления
    public boolean deleteDoc(String owner_id, String doc_id){

        boolean deleteStatus = false;

        DocDel delet = new DocDel();
        delet.execute(owner_id, doc_id);

        try {
            deleteStatus = delet.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return deleteStatus;
    }

    //Ассинхронно удаляем гада
    private class DocDel extends AsyncTask<String, Void, Boolean> {

        //Переменная результата удаления получилось или нет
        boolean ans;



        @Override
        protected Boolean  doInBackground(String... params) {



            int owner_id = Integer.parseInt(params[0]);
            int doc_id = Integer.parseInt(params[1]);

            Log.i(LOG_TAG, "params = "+ owner_id+" "+doc_id);




            VKRequest request = new VKRequest("docs.delete",
            VKParameters.from("owner_id", owner_id, "doc_id", doc_id));

            request.executeSyncWithListener(new VKRequest.VKRequestListener() {

                @Override
                public void onComplete(VKResponse response) {

                    ans = true;
                    Log.i(LOG_TAG, "Succes deleted");

                }


                @Override
                public void onError(VKError error) {

                    Log.e(LOG_TAG, "ERROR");
                    Log.e(LOG_TAG, " " + error.toString()+ " " + error.errorCode);

                }

                @Override
                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {

                }

            });


        return ans;

        }
    }



}
