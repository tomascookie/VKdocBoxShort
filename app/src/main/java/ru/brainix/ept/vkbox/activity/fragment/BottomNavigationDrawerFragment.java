package ru.brainix.ept.vkbox.activity.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.brainix.ept.vkbox.R;
import ru.brainix.ept.vkbox.activity.main.MainActivity;
import ru.brainix.ept.vkbox.activity.main.MainPresenter;

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
                    ((MainActivity)getContext()).mPresenter.menuSelected(0);
                    break;
                case R.id.nav1:
                    ((MainActivity)getContext()).mPresenter.menuSelected(1);
                    break;
                case R.id.nav2:
                    ((MainActivity)getContext()).mPresenter.menuSelected(2);
                    break;
                case R.id.nav3:
                    ((MainActivity)getContext()).mPresenter.menuSelected(3);
                    break;
                case R.id.nav4:
                    ((MainActivity)getContext()).mPresenter.menuSelected(4);
                    break;
                case R.id.nav5:
                    ((MainActivity)getContext()).mPresenter.menuSelected(5);
                    break;
                case R.id.nav6:
                    ((MainActivity)getContext()).mPresenter.menuSelected(6);
                    break;
                case R.id.nav7:
                    ((MainActivity)getContext()).mPresenter.menuSelected(7);
                    break;
                case R.id.nav8:
                    ((MainActivity)getContext()).mPresenter.menuSelected(8);
                    break;

            }
            dismiss();
            return true;
        });
    }
}
