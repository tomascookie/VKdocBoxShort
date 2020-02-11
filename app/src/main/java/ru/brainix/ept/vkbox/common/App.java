package ru.brainix.ept.vkbox.common;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.res.Configuration;

import android.util.Log;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ru.brainix.ept.vkbox.activity.login.LoginActivity;
import ru.brainix.ept.vkbox.activity.main.MainPresenter;
import ru.brainix.ept.vkbox.room.Dao;
import ru.brainix.ept.vkbox.room.Database;
import ru.brainix.ept.vkbox.room.Entity;

public class App extends Application {

    public static App instance;

    private Database database;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        vkAccessTokenTracker.startTracking();

        instance = this;
        database = Room.databaseBuilder(this, Database.class, "database")
            .build();

    }

    public static App getInstance() {
        return instance;
    }

    public Database getDatabase() {
        return database;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    //Трекер изменения токена
    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {

        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            //Todo: add async update method here
            //Если равен нулю, то обновляем БД
            Log.e("XS_App",newToken.accessToken);

            if (newToken == null) {

                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        Database db = App.getInstance().getDatabase();
                        Dao dao = db.dao();
                        Entity entity = dao.getByName("Status");
                        entity.status = false;
                        dao.insert(entity);
                    }
                })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                    		() -> {
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    },
												throwable -> {
                    			Log.e("XS_App",throwable.getMessage());
                    		}
												);


            }
        }
    };

}