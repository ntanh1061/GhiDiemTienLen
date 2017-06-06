package com.nonamejx.ghidiemtienlen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.common.Constants;
import com.nonamejx.ghidiemtienlen.model.Game;
import com.nonamejx.ghidiemtienlen.utils.MyUtils;

import java.util.List;

/**
 * Created by noname
 * on 08/11/2016.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private final Context mContext;
    private final List<Game> mGames;

    public GameAdapter(Context context, List<Game> games) {
        mContext = context;
        mGames = games;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_games, parent, false);
        return new GameViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        cleanBackground(holder.tvResults);
        for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
            holder.tvPlayers[i].setText(mGames.get(position).getPlayerNames()[i]);
            holder.tvResults[i].setText(String.valueOf(mGames.get(position).calculateFinalResult()[i]));
        }

        // change color
        int[] minPos = MyUtils.getMinPositions(mGames.get(position).calculateFinalResult());
        int[] maxPos = MyUtils.getMaxPositions(mGames.get(position).calculateFinalResult());
        for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
            if (minPos[i] > -1) {
                holder.tvResults[i].setBackgroundResource(R.drawable.shape_red_background);
            }
            if (maxPos[i] > -1) {
                holder.tvResults[i].setBackgroundResource(R.drawable.shape_green_background);
            }
        }
    }

    private void cleanBackground(TextView[] textViews) {
        for (TextView tv : textViews) {
            tv.setBackgroundResource(R.drawable.shape_transparent_background);
        }
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {
        final TextView[] tvPlayers, tvResults;

        public GameViewHolder(View itemView) {
            super(itemView);
            tvPlayers = new TextView[4];
            tvResults = new TextView[4];
            tvPlayers[0] = (TextView) itemView.findViewById(R.id.tvPlayer1);
            tvPlayers[1] = (TextView) itemView.findViewById(R.id.tvPlayer2);
            tvPlayers[2] = (TextView) itemView.findViewById(R.id.tvPlayer3);
            tvPlayers[3] = (TextView) itemView.findViewById(R.id.tvPlayer4);
            tvResults[0] = (TextView) itemView.findViewById(R.id.tvResult1);
            tvResults[1] = (TextView) itemView.findViewById(R.id.tvResult2);
            tvResults[2] = (TextView) itemView.findViewById(R.id.tvResult3);
            tvResults[3] = (TextView) itemView.findViewById(R.id.tvResult4);
        }
    }
}
