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
import com.nonamejx.ghidiemtienlen.common.TurnResultDialogListener;
import com.shawnlin.numberpicker.NumberPicker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by noname
 * on 12/11/2016.
 */
@EFragment(R.layout.dialog_add_turn_result)
public class TurnResultDialog extends DialogFragment {
    @FragmentArg
    String dialogTitle;
    @FragmentArg
    String[] players;
    @FragmentArg
    int[] result;
    @FragmentArg
    int turnResultPosition;

    @ViewById(R.id.tvConfirmTitle)
    TextView tvConfirmTitle;
    @ViewById(R.id.tvPlayer1)
    TextView tvPlayer1;
    @ViewById(R.id.tvPlayer2)
    TextView tvPlayer2;
    @ViewById(R.id.tvPlayer3)
    TextView tvPlayer3;
    @ViewById(R.id.tvPlayer4)
    TextView tvPlayer4;
    @ViewById(R.id.btnOK)
    Button btnOK;
    @ViewById(R.id.numberPicker1)
    NumberPicker numberPicker1;
    @ViewById(R.id.numberPicker2)
    NumberPicker numberPicker2;
    @ViewById(R.id.numberPicker3)
    NumberPicker numberPicker3;
    @ViewById(R.id.numberPicker4)
    NumberPicker numberPicker4;

    public static TurnResultDialog newInstance(String title, String[] players, int[] result, int turnResultPosition) {
        return TurnResultDialog_.builder().dialogTitle(title).players(players).result(result).turnResultPosition(turnResultPosition).build();
    }

    @AfterViews
    void afterViews() {
        tvConfirmTitle.setText(dialogTitle);
        tvPlayer1.setText(players[0]);
        tvPlayer2.setText(players[1]);
        tvPlayer3.setText(players[2]);
        tvPlayer4.setText(players[3]);
        if (result != null) {
            setResult(result);
        } else {
            turnResultPosition++;
        }
    }

    private void setResult(int[] result) {
        numberPicker1.setValue(result[0]);
        numberPicker2.setValue(result[1]);
        numberPicker3.setValue(result[2]);
        numberPicker4.setValue(result[3]);
    }

    private int[] getResult() {
        int[] returnResult = new int[4];
        returnResult[0] = numberPicker1.getValue();
        returnResult[1] = numberPicker2.getValue();
        returnResult[2] = numberPicker3.getValue();
        returnResult[3] = numberPicker4.getValue();
        return returnResult;
    }

    @Click(R.id.btnOK)
    void btnOKClick() {
        TurnResultDialogListener turnResultDialogListener = (TurnResultDialogListener) getActivity();
        turnResultDialogListener.onReturnTurnResult(turnResultPosition, getResult());
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
}
