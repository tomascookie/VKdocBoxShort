package ru.brainix.ept.vkbox.activity.main;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Callable;

import ru.brainix.ept.vkbox.common.App;
import ru.brainix.ept.vkbox.docs.data.DocumentModel;
import ru.brainix.ept.vkbox.room.Dao;
import ru.brainix.ept.vkbox.room.Database;
import ru.brainix.ept.vkbox.room.Entity;

public class MainPresenter implements IPresenterMain{

    private MainModel mModel;
    private IViewMain mainView;
    private int type = 0;


    MainPresenter(IViewMain mainView){

        this.mainView = mainView;
        mModel = new MainModel(this);

    }


    public void uploadClicked(){
        mainView.uploadDocWindow();

    }

    public void refreshSwyped(){

        List<DocumentModel> list = mModel.getDocList(String.valueOf(type));

        if(list.size()!=0){

            mainView.textInvisible();
            mainView.setRecyclerView(String.valueOf(type), list);

        }

        else{

            mainView.textVisible();
        }

    }

    public void viewCreate(){

        mainView.setUi();

        List<DocumentModel> list = mModel.getDocList("0");

        if(list.size()!=0){

            mainView.textInvisible();
            mainView.setRecyclerView("0", list);

        }

        else{

            mainView.textVisible();
        }


        checkStatus();
    }

    public void bottomClicked(){

        mainView.bottomMenu();
    }

    public void menuClicked(){

        mainView.settingMenu();
    }

    public void menuSelected(int menuItem){
        type=menuItem;
        refreshSwyped();
    }

    public void preExec(){
        mainView.pbVisible();
    }

    public void postExec(){
        mainView.pbInvivible();
    }


    private void checkStatus(){
        Observable.fromCallable(new CallableLongAction())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((Consumer<Boolean>) status -> {
                if(!status){
                    mainView.goToLoginUnkownBitch();
                }
            });
    }

    private boolean checkAutentification(){

        Database db = App.getInstance().getDatabase();
        Dao dao = db.dao();
        Entity entity = dao.getByName("Status");
        if(entity==null){return false;}
        else{return entity.status;}


    }

    private class CallableLongAction implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            return checkAutentification();
        }
    }

}
