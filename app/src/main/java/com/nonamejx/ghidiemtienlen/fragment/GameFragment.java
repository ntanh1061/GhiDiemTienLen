package com.nonamejx.ghidiemtienlen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.activity.MainActivity;
import com.nonamejx.ghidiemtienlen.activity.ResultActivity_;
import com.nonamejx.ghidiemtienlen.adapter.GameAdapter;
import com.nonamejx.ghidiemtienlen.common.Constants;
import com.nonamejx.ghidiemtienlen.common.DividerItemDecoration;
import com.nonamejx.ghidiemtienlen.common.RecyclerTouchListener;
import com.nonamejx.ghidiemtienlen.database.DataCenter;
import com.nonamejx.ghidiemtienlen.fragment.dialog.ConfirmDeleteGameDialog;
import com.nonamejx.ghidiemtienlen.model.Game;

import org.androidannotations.annotations.EFragment;

import java.util.List;

/**
 * Created by noname
 * on 09/11/2016.
 */
@EFragment
public class GameFragment extends Fragment {
    private List<Game> mGames;
    private GameAdapter mAdapter;

    public static GameFragment newInstance() {
        return GameFragment_.builder().build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mGames = DataCenter.getInstance().getAllGames();
        mAdapter = new GameAdapter(getContext(), mGames);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_clear_all_data, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear_all_data:
                DataCenter.getInstance().deleteAllGames();
                updateRecyclerView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mGames = DataCenter.getInstance().getAllGames();
        updateRecyclerView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        RecyclerView mRecyclerViewGames = (RecyclerView) v.findViewById(R.id.recyclerViewGames);
        mRecyclerViewGames.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewGames.setAdapter(mAdapter);
        mRecyclerViewGames.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerViewGames.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerViewGames, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getContext(), ResultActivity_.class);
                i.putExtra(Constants.INTENT_KEY_GAME_ID, DataCenter.getInstance().getAllGames().get(position).getGameId());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                ConfirmDeleteGameDialog.newInstance(DataCenter.getInstance().getAllGames().get(position).getGameId(), getResources().getString(R.string.confirm_delete_game)).show(getFragmentManager(), "Title");
            }
        }));
        Button btnNewGame = (Button) v.findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getContext()).switchFragment(NewGameFragment.newInstance(), true);
            }
        });
        return v;
    }

    public void updateRecyclerView() {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
