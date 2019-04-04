package ru.brainix.ept.vkbox;

import android.app.Application;
import android.content.res.Configuration;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class ApplicationSetupSDK extends Application {

    //Класс инициализации сдк и отслеживания токена

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        vkAccessTokenTracker.startTracking();

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

            //Если равен нулю, то обновляем БД
            if (newToken == null) {

                MainPresenter mPresenter = new MainPresenter();

                mPresenter.update(getBaseContext());

            }
        }
    };

}