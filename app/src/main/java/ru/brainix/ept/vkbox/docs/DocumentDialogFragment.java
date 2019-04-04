package ru.brainix.ept.vkbox.docs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import ru.brainix.ept.vkbox.MainActivity;
import ru.brainix.ept.vkbox.MainPresenter;
import ru.brainix.ept.vkbox.R;

public class DocumentDialogFragment extends DialogFragment {

    //Метка для лога
    private final String LOG_TAG = "DialogFragment  ";

    //Создаем диалоговое окно
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Переменные для запроса, номер документа и юзера
        final String owner_id = getArguments().getString("owner_id");
        final String doc_id = getArguments().getString("doc_id");

        //Создаем алертдиалог
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setMessage(R.string.acces_deletefrag)
                .setPositiveButton(R.string.yes_deletefrag, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Отправляем запрос на удаление с данными
                        DocumentDelete del = new DocumentDelete();
                        boolean state = del.deleteDoc(owner_id, doc_id);

                        //Обновляем данные в списке
                        ((MainActivity)getContext()).setRecyclerView(String.valueOf(MainPresenter.type));

                        //Пишем в лог состояние
                        if(state){ Log.i(LOG_TAG, " doc delete TRUE"); }

                        else{ Log.i(LOG_TAG, " doc delete FALSE"); }

                    }
                })
                .setNegativeButton(R.string.no_deletefrag, null)
                .create();
    }


}