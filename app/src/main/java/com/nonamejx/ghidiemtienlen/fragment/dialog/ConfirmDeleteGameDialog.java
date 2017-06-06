package com.nonamejx.ghidiemtienlen.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.activity.MainActivity;
import com.nonamejx.ghidiemtienlen.database.DataCenter;
import com.nonamejx.ghidiemtienlen.model.Game;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by noname
 * on 12/11/2016.
 */
@EFragment(R.layout.dialog_confirm_exit)
public class ConfirmDeleteGameDialog extends DialogFragment {
    @FragmentArg
    String mGameId;
    @FragmentArg
    String message;

    @ViewById(R.id.tvConfirmMessage)
    TextView tvMessage;
    @ViewById(R.id.btnOK)
    Button btnOK;
    @ViewById(R.id.btnCancel)
    Button btnCancel;

    IDialogReturn iDialogReturn;

    public static ConfirmDeleteGameDialog newInstance(String gameId, String message) {
        return ConfirmDeleteGameDialog_.builder().mGameId(gameId).message(message).build();
    }

    @AfterViews
    void afterViews() {
        tvMessage.setText(message);
    }

    @Click(R.id.btnOK)
    void btnOKClick() {
        Game g = DataCenter.getInstance().getGame(mGameId);
        DataCenter.getInstance().deleteGame(g);
        ((MainActivity) getActivity()).onReturnOK();
        dismiss();
    }

    @Click(R.id.btnCancel)
    void btnCancelClick() {
        dismiss();
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

    public interface IDialogReturn {
        void onReturnOK();
    }
}
