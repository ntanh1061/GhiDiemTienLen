package com.nonamejx.ghidiemtienlen.activity;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.adapter.TrackingResultAdapter;
import com.nonamejx.ghidiemtienlen.common.Constants;
import com.nonamejx.ghidiemtienlen.common.DividerItemDecoration;
import com.nonamejx.ghidiemtienlen.common.RecyclerTouchListener;
import com.nonamejx.ghidiemtienlen.common.TurnResultDialogListener;
import com.nonamejx.ghidiemtienlen.database.DataCenter;
import com.nonamejx.ghidiemtienlen.fragment.dialog.ConfirmEndGameDialog;
import com.nonamejx.ghidiemtienlen.fragment.dialog.ConfirmExitDialog;
import com.nonamejx.ghidiemtienlen.fragment.dialog.TurnResultDialog;
import com.nonamejx.ghidiemtienlen.model.Game;
import com.nonamejx.ghidiemtienlen.prefs.Setting;
import com.nonamejx.ghidiemtienlen.prefs.SharedPrefsManager;
import com.nonamejx.ghidiemtienlen.utils.MyUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by noname
 * on 11/11/2016.
 */
@EActivity(R.layout.activity_tracking)
public class TrackingActivity extends AppCompatActivity implements TurnResultDialogListener {
    @ViewById(R.id.tvPlayer1)
    TextView tvPlayer1;
    @ViewById(R.id.tvPlayer2)
    TextView tvPlayer2;
    @ViewById(R.id.tvPlayer3)
    TextView tvPlayer3;
    @ViewById(R.id.tvPlayer4)
    TextView tvPlayer4;
    @ViewById(R.id.llResult)
    LinearLayout llResult;
    @ViewById(R.id.recyclerViewTrackingResult)
    RecyclerView recyclerViewTrackingResult;
    TextView[] tvFinalResults;

    TrackingResultAdapter adapter;

    private Game game;
    private Integer currentTurn = -1;
    private boolean isEndGame = false;

    @AfterViews
    void afterView() {
        game = (Game) getIntent().getSerializableExtra(Constants.INTENT_KEY_GAME_OBJECT);
        tvPlayer1.setText(game.getPlayerNames()[0]);
        tvPlayer2.setText(game.getPlayerNames()[1]);
        tvPlayer3.setText(game.getPlayerNames()[2]);
        tvPlayer4.setText(game.getPlayerNames()[3]);

        tvFinalResults = new TextView[4];
        tvFinalResults[0] = (TextView) findViewById(R.id.tvResult1);
        tvFinalResults[1] = (TextView) findViewById(R.id.tvResult2);
        tvFinalResults[2] = (TextView) findViewById(R.id.tvResult3);
        tvFinalResults[3] = (TextView) findViewById(R.id.tvResult4);

        if (SharedPrefsManager.getInstance(this).getSetting(Setting.SHOW_CURRENT_RESULT)) {
            llResult.setVisibility(View.VISIBLE);
        }

        recyclerViewTrackingResult.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrackingResultAdapter(this, currentTurn, game);
        recyclerViewTrackingResult.setAdapter(adapter);
        recyclerViewTrackingResult.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewTrackingResult.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerViewTrackingResult, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                TurnResultDialog.newInstance(getResources().getString(R.string.update_score), game.getPlayerNames(), game.getResult()[position], position).show(getSupportFragmentManager(), "Title");
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_turn_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_add_turn_result) {
            // check is end game
            if (isEndGame) {
                // show confirm dialog
                ConfirmEndGameDialog.newInstance(game.getGameId()).show(getSupportFragmentManager(), "title");
            } else {
                TurnResultDialog.newInstance(getResources().getString(R.string.add_score), game.getPlayerNames(), null, currentTurn).show(getSupportFragmentManager(), "Title");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateFinalResult() {
        if (SharedPrefsManager.getInstance(this).getSetting(Setting.SHOW_CURRENT_RESULT)) {
            int[] finalResult = game.calculateFinalResult();
            for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
                tvFinalResults[i].setText(String.valueOf(finalResult[i]));
            }

            // change color
            if (currentTurn >= 0) {
                // clear background
                for (TextView tvFinalResult : tvFinalResults) {
                    tvFinalResult.setBackgroundColor(0);
                    tvFinalResult.setTextColor(getResources().getColor(R.color.black));
                }

                int[] minPos = MyUtils.getMinPositions(game.calculateFinalResult());
                int[] maxPos = MyUtils.getMaxPositions(game.calculateFinalResult());
                for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
                    if (minPos[i] > -1) {
                        tvFinalResults[i].setBackgroundResource(R.drawable.shape_red_background);
                        tvFinalResults[i].setTextColor(getResources().getColor(R.color.white));
                    }
                    if (maxPos[i] > -1) {
                        tvFinalResults[i].setBackgroundResource(R.drawable.shape_green_background);
                        tvFinalResults[i].setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        }
    }

    private void updateRecyclerView() {
        if (recyclerViewTrackingResult != null) {
            adapter.updateAdapter(this.currentTurn);
        }
    }

    private void addResult(int[] result) {
        currentTurn++;
        if (game != null) {
            System.arraycopy(result, 0, game.getResult()[currentTurn], 0, Constants.NUMBER_OF_PLAYERS);
        }
    }

    private void updateResult(int position, int[] result) {
        if (game != null) {
            System.arraycopy(result, 0, game.getResult()[position], 0, Constants.NUMBER_OF_PLAYERS);
        }
    }

    @Override
    public void onBackPressed() {
        if (isEndGame) {
            super.onBackPressed();
        } else {
            // vibrate
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(100);
            // show confirm dialog
            ConfirmExitDialog.newInstance(getResources().getString(R.string.confirm_exit_tracking)).show(getSupportFragmentManager(), "Title");
        }
    }

    @Override
    public void onReturnTurnResult(int turnResultPosition, int[] turnResult) {
        if (turnResultPosition <= currentTurn) {
            // update result
            updateResult(turnResultPosition, turnResult);
            updateRecyclerView();
        } else {
            // add result
            addResult(turnResult);
            updateRecyclerView();
        }
        updateFinalResult();

        // check is end game
        if ((turnResultPosition + 1) == game.getNumberOfTurns()) {
            isEndGame = true;
            // add to database
            DataCenter.getInstance().addGame(game);
            // show confirm dialog
            ConfirmEndGameDialog.newInstance(game.getGameId()).show(getSupportFragmentManager(), "title");
        }
    }
}