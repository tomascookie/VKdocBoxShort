package ru.brainix.ept.vkbox;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.vk.sdk.util.VKUtil;

import ru.brainix.ept.vkbox.docs.DocumentAdapter;
import ru.brainix.ept.vkbox.docs.DocumentDialogFragment;
import ru.brainix.ept.vkbox.docs.DocumentUploadPopUpWindow;


public class MainActivity extends AppCompatActivity  {

    private final String LOG_TAG = "MainActivity ";

    private MainPresenter mPresenter;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    DocumentAdapter documentAdapter;

    static TextView txt;

    static ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new MainPresenter();

        setContentView(R.layout.activity_main);
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());


        Log.i(LOG_TAG, fingerprints[0]);


        BottomAppBar toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);

        txt = findViewById(R.id.noFile);

        pb = findViewById(R.id.progressBar2);

        TextView appNameText = findViewById(R.id.textAppName);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/myfonts.ttf");

        appNameText.setTypeface(myTypeface);


        //Проверяем аутентификацию пользователя
        boolean check = mPresenter.checkAutentification(getBaseContext());

        if(check) {


            //Устанавливаем стандартные элементы шаблона
            FloatingActionButton fab = findViewById(R.id.floatingActionButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Создаем окно загрузки
                    DocumentUploadPopUpWindow window = new DocumentUploadPopUpWindow();
                    window.getPopUp(view);

                }
            });


            //Отображаем список всех документов пользователя
            recyclerView = findViewById(R.id.main_list);

            //Обрабатываем обновление списка
            refreshLayout = findViewById(R.id.swiperefresh);
            refreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                            // This method performs the actual data-refresh operation.
                            // The method calls setRefreshing(false) when it's finished.
                            setRecyclerView(String.valueOf(MainPresenter.type));
                            return;

                        }
                    }
            );

            setRecyclerView("0");


        }

        else{  goToLoginUnkownBitch(); }

    }


    //Переходим на экран аутентификации
    private void goToLoginUnkownBitch(){

        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        this.finish();

    }



    public static void textVisible(){


        txt.setVisibility(View.VISIBLE);

    }

    public static void pbVisible(){

        pb.setVisibility(View.VISIBLE);

    }

    public static void pbInvivible(){

        pb.setVisibility(View.INVISIBLE);


    }

    public static void textInvisible(){


        txt.setVisibility(View.INVISIBLE);

    }

    public void fileIncorrect(){

        Toast.makeText(this, "Размер файла больше 200Мб", Toast.LENGTH_LONG).show();

    }




    //Обновляем данные списка
    public void setRecyclerView(String typeDoc){


        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        documentAdapter = mPresenter.setDocumentAdapter(getBaseContext(), typeDoc);

        recyclerView.setAdapter(documentAdapter);

        if(refreshLayout.isRefreshing()){        refreshLayout.setRefreshing(false); }

        Log.i(LOG_TAG, "Recycler was settingup");
    }


    //Вызываем диалоговое окно для удаления элемента(убрать в презентер)
    public void getDialogFragment(String owner_id, String doc_id){


        DocumentDialogFragment dialog = new DocumentDialogFragment();

        Bundle args = new Bundle();
        args.putString("owner_id", owner_id);
        args.putString("doc_id", doc_id);
        dialog.setArguments(args);

        dialog.show(getSupportFragmentManager(), "custom");


    }

    //Меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottomappbar_menu, menu);
        // return true so that the menu pop up is opened
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();


        // Операции для выбранного пункта меню
        switch (id) {

            case android.R.id.home:
                BottomNavigationDrawerFragment bottomNavDrawerFragment = new BottomNavigationDrawerFragment();
                bottomNavDrawerFragment.show(getSupportFragmentManager(), bottomNavDrawerFragment.getTag());
                return true;

                case R.id.app_bar_search:
                    Intent intent = new Intent(this, StatisticAct.class);
                    startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
      }
    }
}



