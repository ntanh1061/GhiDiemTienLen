package com.nonamejx.ghidiemtienlen.fragment.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.activity.ResultActivity_;
import com.nonamejx.ghidiemtienlen.common.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by noname
 * on 12/11/2016.
 */
@EFragment(R.layout.dialog_confirm_end_game)
public class ConfirmEndGameDialog extends DialogFragment {
    @FragmentArg
    String gameId;

    @ViewById(R.id.tvConfirmMessage)
    TextView tvMessage;
    @ViewById(R.id.btnEndGame)
    Button btnOK;
    @ViewById(R.id.btnCancel)
    Button btnCancel;

    public static ConfirmEndGameDialog newInstance(String gameId) {
        return ConfirmEndGameDialog_.builder().gameId(gameId).build();
    }

    @AfterViews
    void afterViews() {
        tvMessage.setText(getResources().getString(R.string.end_game_message));
    }

    @Click(R.id.btnEndGame)
    void btnOKClick() {
        Intent i = new Intent(getContext(), ResultActivity_.class);
        i.putExtra(Constants.INTENT_KEY_GAME_ID, gameId);
        startActivity(i);
        getActivity().finish();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }

}
