package ru.brainix.ept.vkbox.activity.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import ru.brainix.ept.vkbox.R;
import ru.brainix.ept.vkbox.activity.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements IViewLogin {

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this);
        mPresenter.activityStarted();

        Button btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(v -> mPresenter.loginStarted());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.activityDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {
                mPresenter.loginComplete();

            }

            @Override
            public void onError(VKError error) {
                mPresenter.loginError();
            }


        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void setTitleFont(){

        TextView appNameText = findViewById(R.id.textAppName);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/fontmain.ttf");

        appNameText.setTypeface(myTypeface);
    }

    @Override
    public void tryLogin() {
        VKSdk.login(LoginActivity.this, VKScope.DOCS);
    }

    @Override
    public void loginComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void loginError() {
        Toast.makeText(LoginActivity.this, "Что-то пошло не так...", Toast.LENGTH_SHORT).show();
    }

}
