package ru.brainix.ept.vkbox.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import ru.brainix.ept.vkbox.R;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);


        TextView appNameText = findViewById(R.id.textAppName);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/fontmain.ttf");

        appNameText.setTypeface(myTypeface);

        MainPresenter mainPresenter = new MainPresenter();

        TextView textView = findViewById(R.id.countText);
        textView.setText(mainPresenter.getStringCount());

    }




}
