package ru.brainix.ept.vkbox.docs;

import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import ru.brainix.ept.vkbox.R;

public class DocumentUploadPopUpWindow {

    //Метка для лога
    private final String LOG_TAG = "DocUpload ";

    //Название загружаемого файла
    private String fileName;

    //Загружаемый файл
    private File uploadableFile;

    //Метод отображения окна
    public void getPopUp(View view) {

        /** Параметры настройки отображения окна **/
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.upload_activity, null);

        int width = LinearLayout.LayoutParams.FILL_PARENT;
        int height = LinearLayout.LayoutParams.FILL_PARENT;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        /** ==================================== **/

        //Объявляем эдиттекст и запрещаем редактирование т.к файл еще не выбран
        EditText textFileName = popupView.findViewById(R.id.textFileNameDetail);
        textFileName.setEnabled(false);

        //Обработчик кнопки выбора файла
        ImageView buttonChoose = popupView.findViewById(R.id.buttonChoose);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Создаем диалоговое окно через библиотеку для выбора фалйа
                new ChooserDialog(view.getContext())
                        .withStartFile(Environment.getExternalStorageDirectory().getAbsolutePath())
                        .withChosenListener(new ChooserDialog.Result() {

                            @Override
                            public void onChoosePath(String path, File pathFile) {

                                //Получаем имя и расположение выбранного файла
                                uploadableFile = pathFile;
                                fileName = pathFile.getName();

                                //Устанавливаем полученное имя файла и разрешаем редактирование
                                textFileName.setText(fileName);
                                textFileName.setEnabled(true);

                                }
                            })

                            //Еo handle the back key pressed or clicked outside the dialog:
                            .withOnCancelListener(new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) {
                                    Log.i(LOG_TAG, "dialog file choose CANCEL");
                                    dialog.cancel(); // MUST have
                                }
                            })
                            .build()
                            .show();






            }
        });

        //Обработка кнопки загрузки
        Button buttonUpload = popupView.findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Если файл выбран
                if(uploadableFile!=null) {

                    String finalFileName = textFileName.getText().toString();

                    //Отправляем запрос на загрузку с именем файла
                    DocumentUploader documentUploader = new DocumentUploader();
                    documentUploader.ups(uploadableFile, finalFileName,view.getContext());

                    //Скипаем окно
                    popupWindow.dismiss();


                }
            }
        });


        //Закрываем окно при нажатии вне его
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


}
