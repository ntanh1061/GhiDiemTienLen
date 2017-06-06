package com.nonamejx.ghidiemtienlen.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nonamejx.ghidiemtienlen.R;
import com.nonamejx.ghidiemtienlen.common.Constants;
import com.nonamejx.ghidiemtienlen.database.DataCenter;
import com.nonamejx.ghidiemtienlen.model.Game;
import com.nonamejx.ghidiemtienlen.utils.MyUtils;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by noname
 * on 14/11/2016.
 */
@EFragment
public class BarChartResultFragment extends Fragment {
    @FragmentArg
    String mGameId;

    private Game mGame;

    public static BarChartResultFragment newInstance(String gameId) {
        return BarChartResultFragment_.builder().mGameId(gameId).build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mGame = DataCenter.getInstance().getGame(mGameId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chart_result, container, false);
        BarChart barChart = (BarChart) v.findViewById(R.id.barChartResult);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        final int[] finalResults = mGame.calculateFinalResult();
        for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
            barEntries.add(new BarEntry(finalResults[i], i));
        }
        BarDataSet dataSet = new BarDataSet(barEntries, null);

        List<String> labels = Arrays.asList(mGame.getPlayerNames());

        BarData barData = new BarData(labels, dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.setData(barData);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setDescription("");
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.animateY(500);
        barChart.invalidate();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_share) {
            shareImage(MyUtils.saveBitmap(takeScreenShot(), getResources().getString(R.string.file_name_result_screenshot)));
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

    private Bitmap takeScreenShot() {
        Activity activity = getActivity();
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
}