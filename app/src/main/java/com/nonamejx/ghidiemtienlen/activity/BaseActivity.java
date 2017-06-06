package com.nonamejx.ghidiemtienlen.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.database.DataCenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.UUID;

/**
 * Created by noname
 * on 08/11/2016.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {
    @ViewById(R.id.flContainer)
    FrameLayout mFlContainer;

    // Create a single fragment
    protected abstract Fragment createFragment();

    public abstract void afterView();

    @AfterViews
    public void init() {
        afterView();
        // init data
        DataCenter.getInstance();

        // Add fragment
        if (mFlContainer != null) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.flContainer);
            if (fragment == null) {
                fragment = createFragment();
                fm.beginTransaction().add(R.id.flContainer, fragment).commit();
            }
        }
    }

    public void switchFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.flContainer, fragment);
        if (addToBackStack) {
            ft.addToBackStack(UUID.randomUUID().toString());
        }
        ft.commit();
    }
}
