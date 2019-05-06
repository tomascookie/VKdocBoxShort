package ru.brainix.ept.vkbox.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import ru.brainix.ept.vkbox.R;


public class LoginActivity extends AppCompatActivity {

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        TextView appNameText = findViewById(R.id.textAppName);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/fontmain.ttf");

        appNameText.setTypeface(myTypeface);

        //Обрабатываем кнопку входа
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Указываем разрешения и открываем окно авторизации
                VKSdk.login(LoginActivity.this, VKScope.DOCS);
            }

        });


    }


    //Обрабатываем результат аутентификации
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {

                // Пользователь успешно авторизовался
                mPresenter = new MainPresenter();
                mPresenter.update(LoginActivity.this);
                goToMain();

            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Toast.makeText(LoginActivity.this, "Что-то пошло не так...", Toast.LENGTH_SHORT).show();
            }


        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Переходим на основной экран
    private void goToMain(){

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        this.finish();

    }

}
