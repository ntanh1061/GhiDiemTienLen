package com.nonamejx.ghidiemtienlen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.activity.ResultActivity_;
import com.nonamejx.ghidiemtienlen.common.Constants;
import com.nonamejx.ghidiemtienlen.model.Game;
import com.nonamejx.ghidiemtienlen.utils.MyUtils;

/**
 * Created by noname
 * on 10/11/2016.
 */
public class TableResultAdapter extends RecyclerView.Adapter<TableResultAdapter.TurnDetailResultViewHolder> {
    private static final int TYPE_DETAIL_RESULT = 0;
    private static final int TYPE_FINAL_RESULT = 1;
    private final Context mContext;
    private final Game mGame;

    public TableResultAdapter(Context context, Game game) {
        mContext = context;
        mGame = game;
    }

    @Override
    public TurnDetailResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_DETAIL_RESULT) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_table_results, parent, false);
            return new TurnDetailResultViewHolder((v));
        } else if (viewType == TYPE_FINAL_RESULT) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_table_result_final_result, parent, false);
            return new TurnDetailResultViewHolder((v));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(TurnDetailResultViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_DETAIL_RESULT) {
            for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
                holder.tvResults[i].setText(mGame.getResult()[position][i] + "");
            }
            holder.tvResults[4].setText(position + 1 + "");
        } else {
            for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
                int[] finalResult = mGame.calculateFinalResult();
                holder.tvResults[i].setText(finalResult[i] + "");
            }

            // change color
            int[] minPos = MyUtils.getMinPositions(mGame.calculateFinalResult());
            int[] maxPos = MyUtils.getMaxPositions(mGame.calculateFinalResult());
            for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
                if (minPos[i] > -1) {
                    holder.tvResults[i].setBackgroundResource(R.drawable.shape_red_background);
                    holder.tvResults[i].setTextColor(mContext.getResources().getColor(R.color.white));
                }
                if (maxPos[i] > -1) {
                    holder.tvResults[i].setBackgroundResource(R.drawable.shape_green_background);
                    holder.tvResults[i].setTextColor(mContext.getResources().getColor(R.color.white));
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == mGame.getNumberOfTurns() ? TYPE_FINAL_RESULT : TYPE_DETAIL_RESULT;
    }

    @Override
    public int getItemCount() {
        return mGame.getNumberOfTurns() + 1;
    }

    public class TurnDetailResultViewHolder extends RecyclerView.ViewHolder {
        final TextView[] tvResults;
        final Button btnBack;

        public TurnDetailResultViewHolder(View itemView) {
            super(itemView);
            tvResults = new TextView[5];
            tvResults[0] = (TextView) itemView.findViewById(R.id.tvResult1);
            tvResults[1] = (TextView) itemView.findViewById(R.id.tvResult2);
            tvResults[2] = (TextView) itemView.findViewById(R.id.tvResult3);
            tvResults[3] = (TextView) itemView.findViewById(R.id.tvResult4);
            tvResults[4] = (TextView) itemView.findViewById(R.id.tvIndex);
            btnBack = (Button) itemView.findViewById(R.id.btnBack);
            if (btnBack != null) {
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((ResultActivity_) mContext).finish();
                    }
                });
            }
        }
    }
}