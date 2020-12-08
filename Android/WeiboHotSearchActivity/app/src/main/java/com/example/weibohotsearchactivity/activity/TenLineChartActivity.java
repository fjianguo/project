package com.example.weibohotsearchactivity.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.weibohotsearchactivity.R;
import com.example.weibohotsearchactivity.model.LineCircleChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TenLineChartActivity extends AppCompatActivity {

    private String[] titleList = new String[10];
    private LineData lineData;
    private ArrayList<ILineDataSet> iLineDataSet = new ArrayList<>();
    private LineDataSet lineData1;
    private LineDataSet lineData2;
    private LineDataSet lineData3;
    private LineDataSet lineData4;
    private LineDataSet lineData5;
    private LineDataSet lineData6;
    private LineDataSet lineData7;
    private LineDataSet lineData8;
    private LineDataSet lineData9;
    private LineDataSet lineData10;
    private ArrayList<Entry> list1 = new ArrayList<>();
    private ArrayList<Entry> list2 = new ArrayList<>();
    private ArrayList<Entry> list3 = new ArrayList<>();
    private ArrayList<Entry> list4 = new ArrayList<>();
    private ArrayList<Entry> list5 = new ArrayList<>();
    private ArrayList<Entry> list6 = new ArrayList<>();
    private ArrayList<Entry> list7 = new ArrayList<>();
    private ArrayList<Entry> list8 = new ArrayList<>();
    private ArrayList<Entry> list9 = new ArrayList<>();
    private ArrayList<Entry> list10 = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        TenTitle();
    }

    private ArrayList<Entry> sendRequestWithOkHttp(final String name, final ArrayList<Entry> list) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("title", name)
                    .build();
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/api/weibo")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONArray jsonArray = new JSONArray(responseData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String views = jsonObject.getString("view");
                list.add(new Entry(i, Float.parseFloat(views)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void TenTitle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .build();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/rank/weibo")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < 10; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String titles = jsonObject.getString("title");
                        titleList[i] = titles;
                    }
                    drawLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private LineDataSet setData(List<Entry> data, String name) {
        LineDataSet dataSet = new LineDataSet(data, name);
        dataSet.setDrawValues(false);
        //设置曲线值的圆点是实心还是空心
        dataSet.setDrawCircleHole(false);
        //设置显示值的字体大小
        dataSet.setValueTextSize(9f);
        //线模式为圆滑曲线（默认折线）
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int IValue = (int) value;
                return String.valueOf(IValue);
            }
        });
        return dataSet;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void drawLine() {
        final LineCircleChart mLineChart = findViewById(R.id.lineChart);
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置那几个位置的圆点显示
        List<Integer> position = new ArrayList<>();
        for (int i = 0; i < 60; i = i + 5) {
            position.add(i);
        }
//        LineChartCircleRenderer.setCirclePoints(position);
        lineData1 = setData(sendRequestWithOkHttp(titleList[0], list1), titleList[0]);
        lineData1.setColor(Color.parseColor("#feb64d"));
        lineData2 = setData(sendRequestWithOkHttp(titleList[1], list2), titleList[1]);
        lineData2.setColor(Color.parseColor("#ff7c7c"));
        lineData3 = setData(sendRequestWithOkHttp(titleList[2], list3), titleList[2]);
        lineData3.setColor(Color.parseColor("#9287e7"));
        lineData4 = setData(sendRequestWithOkHttp(titleList[3], list4), titleList[3]);
        lineData4.setColor(Color.parseColor("#6200ee"));
        lineData5 = setData(sendRequestWithOkHttp(titleList[4], list5), titleList[4]);
        lineData5.setColor(Color.parseColor("#03dac5"));
        lineData6 = setData(sendRequestWithOkHttp(titleList[5], list6), titleList[5]);
        lineData6.setColor(Color.parseColor("#ff07cb"));
        lineData7 = setData(sendRequestWithOkHttp(titleList[6], list7), titleList[6]);
        lineData7.setColor(Color.parseColor("#ffeb61"));
        lineData8 = setData(sendRequestWithOkHttp(titleList[7], list8), titleList[7]);
        lineData8.setColor(Color.parseColor("#ffacba"));
        lineData9 = setData(sendRequestWithOkHttp(titleList[8], list9), titleList[8]);
        lineData9.setColor(Color.parseColor("#ff0000"));
        lineData10 = setData(sendRequestWithOkHttp(titleList[9], list10), titleList[9]);
        lineData10.setColor(Color.parseColor("#00ff00"));
        iLineDataSet.add(lineData1);
        iLineDataSet.add(lineData2);
        iLineDataSet.add(lineData3);
        iLineDataSet.add(lineData4);
        iLineDataSet.add(lineData5);
        iLineDataSet.add(lineData6);
        iLineDataSet.add(lineData7);
        iLineDataSet.add(lineData8);
        iLineDataSet.add(lineData9);
        iLineDataSet.add(lineData10);
        lineData = new LineData(iLineDataSet);
        mLineChart.setData(lineData);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12, true);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(60f);
        xAxis.setDrawGridLines(false);

        YAxis leftYAxis = mLineChart.getAxisLeft();
        YAxis rightYAxis = mLineChart.getAxisRight();
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setAxisMinimum(0);
        leftYAxis.setAxisMaximum(5000000);

        rightYAxis.setEnabled(false);
        Legend legend = mLineChart.getLegend();
        legend.setTextColor(Color.BLACK); //设置Legend 文本颜色
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setFormSize(12f);
        legend.setTextSize(14f);
        legend.setWordWrapEnabled(true);
        legend.setMaxSizePercent(0);
        legend.setEnabled(false);
        legend.setDrawInside(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDragEnabled(false);
        MarkerViewActivity mv = new MarkerViewActivity(this, xAxis.getValueFormatter());
        mv.setChartView(mLineChart);
        mLineChart.setMarker(mv);
    }

}