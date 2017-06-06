package com.nonamejx.ghidiemtienlen.activity;

import android.support.v4.app.Fragment;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.fragment.GameFragment;
import com.nonamejx.ghidiemtienlen.fragment.dialog.ConfirmDeleteGameDialog;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements ConfirmDeleteGameDialog.IDialogReturn {

    private GameFragment gameFragment;

    @Override
    protected Fragment createFragment() {
        gameFragment = GameFragment.newInstance();
        return gameFragment;
    }

    @Override
    public void afterView() {
    }

    @Override
    public void onReturnOK() {
        if (gameFragment != null) {
            gameFragment.updateRecyclerView();
        }
    }
}
