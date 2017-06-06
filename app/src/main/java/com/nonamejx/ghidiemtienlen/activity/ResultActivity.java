package com.nonamejx.ghidiemtienlen.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.common.Constants;
import com.nonamejx.ghidiemtienlen.fragment.BarChartResultFragment;
import com.nonamejx.ghidiemtienlen.fragment.TableResultFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by noname
 * on 10/11/2016.
 */
@EActivity(R.layout.activity_result)
public class ResultActivity extends AppCompatActivity {
    private static int VIEWPAGER_RESULT_FRAGMENTS_COUNT = 2;

    @ViewById(R.id.viewPagerResult)
    ViewPager viewPagerResult;

    private String gameId;

    private TableResultFragment tableResultFragment;
    private BarChartResultFragment barChartResultFragment;

    @AfterViews
    void afterViews() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameId = extras.getString(Constants.INTENT_KEY_GAME_ID);
            tableResultFragment = TableResultFragment.newInstance(gameId);
            barChartResultFragment = BarChartResultFragment.newInstance(gameId);
            viewPagerResult.setAdapter(new ViewPagerResultAdapter(getSupportFragmentManager()));
        }
    }

    private class ViewPagerResultAdapter extends FragmentStatePagerAdapter {

        public ViewPagerResultAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return tableResultFragment;
                case 1:
                    return barChartResultFragment;
                default:
                    return tableResultFragment;
            }
        }

        @Override
        public int getCount() {
            return VIEWPAGER_RESULT_FRAGMENTS_COUNT;
        }
    }

}
