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
import com.nonamejx.ghidiemtienlen.prefs.Setting;
import com.nonamejx.ghidiemtienlen.prefs.SharedPrefsManager;

/**
 * Created by noname
 * on 12/11/2016.
 */
public class TrackingResultAdapter extends RecyclerView.Adapter<TrackingResultAdapter.TrackingResultViewHolder> {
    private Context mContext;
    private Integer currentPosition;
    private Game mGame;

    public TrackingResultAdapter(Context context, Integer currentPosition, Game game) {
        this.mContext = context;
        this.currentPosition = currentPosition;
        this.mGame = game;
    }


    @Override
    public TrackingResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_table_results, parent, false);
        return new TrackingResultViewHolder((v));
    }

    @Override
    public void onBindViewHolder(TrackingResultViewHolder holder, int position) {
        for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
            holder.tvResults[i].setText(String.valueOf(mGame.getResult()[position][i]));
        }
        if (SharedPrefsManager.getInstance(mContext).getSetting(Setting.SHOW_NUMBER_OF_TURNS)) {
            holder.tvResults[4].setText(String.valueOf(position + 1));
        }
    }

    @Override
    public int getItemCount() {
        return this.currentPosition + 1;
    }

    public void updateAdapter(int lineCount) {
        this.currentPosition = lineCount;
        notifyDataSetChanged();
    }

    class TrackingResultViewHolder extends RecyclerView.ViewHolder {
        final TextView[] tvResults;

        public TrackingResultViewHolder(View itemView) {
            super(itemView);
            tvResults = new TextView[5];
            tvResults[0] = (TextView) itemView.findViewById(R.id.tvResult1);
            tvResults[1] = (TextView) itemView.findViewById(R.id.tvResult2);
            tvResults[2] = (TextView) itemView.findViewById(R.id.tvResult3);
            tvResults[3] = (TextView) itemView.findViewById(R.id.tvResult4);
            tvResults[4] = (TextView) itemView.findViewById(R.id.tvIndex);
        }
    }
}
