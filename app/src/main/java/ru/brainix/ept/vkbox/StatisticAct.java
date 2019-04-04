package ru.brainix.ept.vkbox;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatisticAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);



        TextView appNameText = findViewById(R.id.textAppName);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/myfonts.ttf");

        appNameText.setTypeface(myTypeface);

        MainPresenter mainPresenter = new MainPresenter();

        TextView textView = findViewById(R.id.countText);
        textView.setText(mainPresenter.getStringCount());

    }




}
