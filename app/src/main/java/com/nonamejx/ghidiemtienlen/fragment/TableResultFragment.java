package com.nonamejx.ghidiemtienlen.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.adapter.TableResultAdapter;
import com.nonamejx.ghidiemtienlen.common.Constants;
import com.nonamejx.ghidiemtienlen.common.DividerItemDecoration;
import com.nonamejx.ghidiemtienlen.database.DataCenter;
import com.nonamejx.ghidiemtienlen.model.Game;
import com.nonamejx.ghidiemtienlen.utils.MyUtils;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.io.File;

/**
 * Created by noname
 * on 10/11/2016.
 */
@EFragment
public class TableResultFragment extends Fragment {
    @FragmentArg
    String mGameId;

    private RecyclerView mRecyclerViewTableResult;
    private TableResultAdapter mAdapter;
    private LinearLayout llPlayersName;

    private Game mGame;

    public static TableResultFragment newInstance(String gameId) {
        return TableResultFragment_.builder().mGameId(gameId).build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mGame = DataCenter.getInstance().getGame(mGameId);
        mAdapter = new TableResultAdapter(getActivity(), mGame);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_table_result, container, false);
        llPlayersName = (LinearLayout) v.findViewById(R.id.llPlayersName);
        mRecyclerViewTableResult = (RecyclerView) v.findViewById(R.id.recyclerViewTableResult);
        mRecyclerViewTableResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewTableResult.setAdapter(mAdapter);
        mRecyclerViewTableResult.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        TextView[] tvPlayers = new TextView[4];
        tvPlayers[0] = (TextView) v.findViewById(R.id.tvPlayer1);
        tvPlayers[1] = (TextView) v.findViewById(R.id.tvPlayer2);
        tvPlayers[2] = (TextView) v.findViewById(R.id.tvPlayer3);
        tvPlayers[3] = (TextView) v.findViewById(R.id.tvPlayer4);
        for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
            tvPlayers[i].setText(mGame.getPlayerNames()[i]);
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_share) {
            shareImage(MyUtils.saveBitmap(takeScreenShotWithRecyclerView(), getResources().getString(R.string.file_name_result_screenshot)));
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareImage(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, new String("GhiDiemDanhBai"));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, new String("GhiDiemDanhBai"));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(intent, "share via"));
    }

    /*
    * This function followed here: https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
    * */
    private Bitmap takeScreenShotWithRecyclerView() {
        Bitmap bigBitmap = null;
        if (mAdapter != null) {
            int size = mAdapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = -1; i < size; i++) {
                if (i == -1) {
                    // add players name bitmap
                    llPlayersName.setDrawingCacheEnabled(true);
                    llPlayersName.buildDrawingCache();
                    Bitmap drawingCache = llPlayersName.getDrawingCache();
                    if (drawingCache != null) {
                        bitmaCache.put(String.valueOf(i), drawingCache);
                    }
                    height += llPlayersName.getMeasuredHeight();
                } else {
                    TableResultAdapter.TurnDetailResultViewHolder holder = mAdapter.createViewHolder(mRecyclerViewTableResult, mAdapter.getItemViewType(i));
                    mAdapter.onBindViewHolder(holder, i);
                    holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(mRecyclerViewTableResult.getWidth(), View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                    holder.itemView.setDrawingCacheEnabled(true);
                    holder.itemView.buildDrawingCache();
                    Bitmap drawingCache = holder.itemView.getDrawingCache();
                    if (drawingCache != null) {
                        bitmaCache.put(String.valueOf(i), drawingCache);
                    }
                    height += holder.itemView.getMeasuredHeight();
                }
            }

            bigBitmap = Bitmap.createBitmap(mRecyclerViewTableResult.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            for (int i = -1; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
            }
        }
        return bigBitmap;
    }
}
