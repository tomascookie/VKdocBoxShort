package ru.brainix.ept.vkbox.activity.statistic;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import ru.brainix.ept.vkbox.R;

public class StatisticActivity extends AppCompatActivity implements IViewStatistic{

    private TextView textView;
    private StatisticPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        textView = findViewById(R.id.countText);

        mPresenter = new StatisticPresenter(this);
        mPresenter.activityStarted();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.activityDestroyed();
    }

    @Override
    public void setTitleFont(){

    TextView appNameText = findViewById(R.id.textAppName);

    Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/fontmain.ttf");

    appNameText.setTypeface(myTypeface);
}

    @Override
    public void setStatisticText(String statisticText){

        textView.setText(statisticText);
    }

}
