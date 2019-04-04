package ru.brainix.ept.vkbox;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.brainix.ept.vkbox.data.SettingModel;
import ru.brainix.ept.vkbox.data.SettingsAdapter;
import ru.brainix.ept.vkbox.data.SettingsBaseContract;
import ru.brainix.ept.vkbox.docs.DocumentAdapter;
import ru.brainix.ept.vkbox.docs.DocumentModel;

public class MainPresenter {

    public static int type = 0;

    private MainModel mModel;
    private SettingsAdapter adapter;
    private DocumentAdapter mDocumentAdapter;



    //Запускаем проверку аутентификации и отдаем статус
    public boolean checkAutentification(Context cntxt){

        CheckAutent check = new CheckAutent();
        check.execute(cntxt);
        boolean autentState = false;

        try {
            autentState = check.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return autentState;
    }

    //Обновляем информацию аутентификации
    public void update(Context cntxt){

        int autState;
        if(checkAutentification(cntxt)){autState=0;}else{autState=1;}

        String name = SettingsBaseContract.SettingsParamName.COLUMN_NAME;
        SettingModel user = new SettingModel( 1, name, autState);

        adapter.open();

        adapter.update(user);

        adapter.close();


    }

    //Устанавливаем адаптер списком документов пользователя
    public DocumentAdapter setDocumentAdapter(Context cntxt, String typeDoc){

        mModel = new MainModel();

        List<DocumentModel> list = mModel.getDocList(typeDoc);

        if(list.size()!=0){MainActivity.textInvisible();}
        else{MainActivity.textVisible();}

        mDocumentAdapter = new DocumentAdapter(cntxt, list);



        return mDocumentAdapter;
    }

    //Проверяем аутентификацию запросом к БД
    private class CheckAutent extends AsyncTask<Context, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Context... cntxt) {


            adapter = new SettingsAdapter(cntxt[0]);

            adapter.open();
            SettingModel setting = adapter.getData(1);
            int currentState = setting.getState();
            adapter.close();

            if(currentState==0){
                return false;
            }
            else {
                return  true;
            }

        }
    }

    //Обновляем счетчик статистики файлов
    public String getStringCount(){

        MainModel mainModel = new MainModel();
        String count = mainModel.getCount();
        count = count + "/2000";

        return count;
    }


}
