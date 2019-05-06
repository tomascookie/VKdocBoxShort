package ru.brainix.ept.vkbox.docs.functions;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.concurrent.ExecutionException;

import ru.brainix.ept.vkbox.activity.MainActivity;
import ru.brainix.ept.vkbox.activity.MainPresenter;


public class DocEdit {

    //Метка для лога
    private final String LOG_TAG = "DocEdit ";

    //Метод запуска запроса редактирования
    public boolean editDoc(Context cntxt, String owner_id, String doc_id, String title){

        //Статус успешно или нет
        boolean editStatus = false;

        //Выполняем запрос
        DocEditAsync docEdit = new DocEditAsync();
        docEdit.execute(owner_id, doc_id, title);

        try {
            editStatus = docEdit.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Обновляем список
        ((MainActivity)cntxt).setRecyclerView(String.valueOf(MainPresenter.type));

        return editStatus;
    }

    //Отправляем запрос
    private class DocEditAsync extends AsyncTask<String, Void, Boolean> {

        //Переменная статуса ответа
        boolean ans;

        @Override
        protected Boolean  doInBackground(String... params) {

            //Получаем параметры для отправки
            int owner_id = Integer.parseInt(params[0]);
            int doc_id = Integer.parseInt(params[1]);
            String title = params[2];



            VKRequest request = new VKRequest("docs.edit",
            VKParameters.from("owner_id", owner_id, "doc_id", doc_id, "title", title));

            request.executeSyncWithListener(new VKRequest.VKRequestListener() {

                @Override
                public void onComplete(VKResponse response) {

                    //Ставим статус
                    ans = true;
                    Log.i(LOG_TAG, "edit SUCCES");



                }


                @Override
                public void onError(VKError error) {

                    Log.e(LOG_TAG, "edit ERROR");
                    Log.e("DOCUMENT_EDIT", " " + error.toString()+ " " + error.errorCode);

                }

                @Override
                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {

                }

            });


            return ans;

        }
    }


}
