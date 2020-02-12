package ru.brainix.ept.vkbox.activity.main;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.vk.sdk.util.VKUtil;

import java.util.List;
import ru.brainix.ept.vkbox.activity.login.LoginActivity;
import ru.brainix.ept.vkbox.activity.statistic.StatisticActivity;
import ru.brainix.ept.vkbox.activity.fragment.BottomNavigationDrawerFragment;
import ru.brainix.ept.vkbox.R;
import ru.brainix.ept.vkbox.docs.data.DocumentAdapter;
import ru.brainix.ept.vkbox.docs.data.DocumentModel;
import ru.brainix.ept.vkbox.docs.fragment.DocDeleteFragment;
import ru.brainix.ept.vkbox.docs.fragment.DocUploadWindow;


public class MainActivity extends AppCompatActivity implements IViewMain  {

    public MainPresenter mPresenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton fab;

    private TextView txt;
    private ProgressBar pb;
    private View uploadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);
        mPresenter.viewCreate();


        fab.setOnClickListener(view -> {
            uploadView=view;
            mPresenter.uploadClicked();
        });

        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.refreshSwyped();
            return;
        });

    }


    @Override
    public void setUi(){

        BottomAppBar toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);

        TextView appNameText = findViewById(R.id.textAppName);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/fontmain.ttf");
        appNameText.setTypeface(myTypeface);

        txt = findViewById(R.id.noFile);
        pb = findViewById(R.id.progressBar2);
        fab = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.main_list);
        refreshLayout = findViewById(R.id.swiperefresh);


    }

    @Override
    public void uploadDocWindow(){
        //Создаем окно загрузки
        DocUploadWindow window = new DocUploadWindow();
        window.getPopUp(uploadView);
    }

    @Override
    public void goToLoginUnkownBitch(){
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        this.finish();

    }

    @Override
    public void textVisible(){

        txt.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void pbVisible(){

        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void pbInvivible(){

        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void textInvisible(){

        txt.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void fileIncorrect(){

        Toast.makeText(this, "Размер файла больше 200Мб", Toast.LENGTH_LONG).show();

    }

    @Override
    public void setRecyclerView(String typeDoc, List<DocumentModel> list){

        DocumentAdapter mDocumentAdapter = new DocumentAdapter(this, list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        recyclerView.setAdapter(mDocumentAdapter);

        if(refreshLayout.isRefreshing()){

            refreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void bottomMenu(){
        BottomNavigationDrawerFragment bottomNavDrawerFragment = new BottomNavigationDrawerFragment();
        bottomNavDrawerFragment.show(getSupportFragmentManager(), bottomNavDrawerFragment.getTag());
    }

    @Override
    public void settingMenu(){
        Intent intent = new Intent(this, StatisticActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottomappbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:

                mPresenter.bottomClicked();
                return true;

            case R.id.app_bar_search:

                mPresenter.menuClicked();
                return true;

            default:
                return super.onOptionsItemSelected(item);
      }
    }

    @Override
    public void getDialogFragment(String owner_id, String doc_id){


        DocDeleteFragment dialog = new DocDeleteFragment();

        Bundle args = new Bundle();
        args.putString("owner_id", owner_id);
        args.putString("doc_id", doc_id);
        dialog.setArguments(args);

        dialog.show(getSupportFragmentManager(), "custom");


    }
}



