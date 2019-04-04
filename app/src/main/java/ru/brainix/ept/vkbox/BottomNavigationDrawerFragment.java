package ru.brainix.ept.vkbox;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bottomsheet, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavigationView navigationView = getView().findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.nav0:
                    ((MainActivity)getContext()).setRecyclerView("0");
                    MainPresenter.type=0;
                    return true;
                case R.id.nav1:
                    ((MainActivity)getContext()).setRecyclerView("1");
                    MainPresenter.type=1;
                    return true;
                case R.id.nav2:
                    ((MainActivity)getContext()).setRecyclerView("2");
                    MainPresenter.type=2;
                    return true;
                case R.id.nav3:
                    ((MainActivity)getContext()).setRecyclerView("3");
                    MainPresenter.type=3;
                    return true;
                case R.id.nav4:
                    ((MainActivity)getContext()).setRecyclerView("4");
                    MainPresenter.type=4;
                    return true;
                case R.id.nav5:
                    ((MainActivity)getContext()).setRecyclerView("5");
                    MainPresenter.type=5;
                    return true;
                case R.id.nav6:
                    ((MainActivity)getContext()).setRecyclerView("6");
                    MainPresenter.type=6;
                    return true;
                case R.id.nav7:
                    ((MainActivity)getContext()).setRecyclerView("7");
                    MainPresenter.type=7;
                    return true;
                case R.id.nav8:
                    ((MainActivity)getContext()).setRecyclerView("8");
                    MainPresenter.type=8;
                    return true;

                default:
                    return true;
            }
        });
    }
}
