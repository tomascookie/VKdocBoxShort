package ru.brainix.ept.vkbox.docs;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import ru.brainix.ept.vkbox.MainActivity;
import ru.brainix.ept.vkbox.MainPresenter;
import ru.brainix.ept.vkbox.R;


public class DocumentPopUpWindow {

    private String titleDoc;
    private String dateDoc;
    private String sizeDoc;
    private String fileTypeDoc;
    private String ownerId;
    private String docId;
    private String urlDoc;

    //Конструктор класса
    DocumentPopUpWindow(String titleDoc, String sizeDoc, String dateDoc, String fileTypeDoc, String ownerId, String docId, String urlDoc){

        this.titleDoc = titleDoc;
        this.dateDoc = dateDoc;
        this.sizeDoc = sizeDoc;
        this.fileTypeDoc = fileTypeDoc;
        this.ownerId = ownerId;
        this.docId = docId;
        this.urlDoc = urlDoc;

    }

    //Метод отображения и обработки(переделать по возможности)
    public void getPopUp(final View view) {

        /** Параметры настройки отображения окна **/

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.pop_doclist_layout, null);

        int width = LinearLayout.LayoutParams.FILL_PARENT;
        int height = LinearLayout.LayoutParams.FILL_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        /** ==================================== **/

        //Поле редактирования названия файла
        final EditText titleDocEditText = popupView.findViewById(R.id.textFileNameDetail);
        titleDocEditText.setText(titleDoc);

        //Текстовая иконка расширения
        TextView typeDocText = popupView.findViewById(R.id.imageFileIconDetail);
        typeDocText.setText(fileTypeDoc);

        //Текст размера файла
        TextView sizeText = popupView.findViewById(R.id.textFileSizeDetail);
        sizeText.setText(sizeDoc);

        //Текст даты
        TextView dateText = popupView.findViewById(R.id.textFileDateDetail);
        dateText.setText(dateDoc);

        //Кнопка редактирования обработка
        ImageView buttonEdit = popupView.findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Если изменено название, то редактируем
                if((titleDocEditText.getText().toString()) != titleDoc) {

                    //Отправляем запрос на редактирование
                    DocumentEdit documentEdit = new DocumentEdit();
                    documentEdit.editDoc(view.getContext(), ownerId, docId, titleDocEditText.getText().toString());

                }

                Toast.makeText(view.getContext(), R.string.pop_change_name, Toast.LENGTH_SHORT).show();

            }
        });

        //Кнопка удаления обработка
        ImageView buttonDelete = popupView.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Вызываем метод диалогового окна через активность(поменять)
                ((MainActivity)view.getContext()).getDialogFragment(ownerId, docId);
                 //Завершаем окно
                 popupWindow.dismiss();

            }
        });

        //Кнопка загрузки обработка
        ImageView buttonDownload = popupView.findViewById(R.id.buttonDownload);
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Отправляем запрос на загрузку ссылки
                DocumentDownload documentDownload = new DocumentDownload();
                documentDownload.docDownload(urlDoc, view.getContext());



            }
        });

        //Кнопка получения ссылки обработка
        ImageView buttonShare = popupView.findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Отправляем запрос на копирование ссылки
                DocumentCopyUrl documentCopyUrl = new DocumentCopyUrl();
                documentCopyUrl.docCopy(urlDoc, view.getContext());
            }
        });



        //Метод для скрытия окна
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


}
