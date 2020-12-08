package com.example.weibohotsearchactivity.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.weibohotsearchactivity.R;
import com.example.weibohotsearchactivity.model.LineChartCircleRenderer;
import com.example.weibohotsearchactivity.model.LineCircleChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
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

public class LineChartActivity extends AppCompatActivity {

    private List<Entry> entries = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        Intent intent = getIntent();
        String titles = intent.getStringExtra("title");
        sendRequestWithOkHttp(titles);
        final LineCircleChart mLineChart = findViewById(R.id.lineChart);
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置那几个位置的圆点显示
        List<Integer> position = new ArrayList<>();
        for (int i = 0;i < 60;i = i + 5 ) {
            position.add(i);
        }
        LineChartCircleRenderer.setCirclePoints(position);
        LineDataSet lineDataSet = new LineDataSet(entries, titles);
        lineDataSet.setDrawValues(false);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        //设置显示值的字体大小
        lineDataSet.setValueTextSize(9f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int IValue = (int) value;
                return String.valueOf(IValue);
            }
        });
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
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
        legend.setEnabled(true);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setDrawGridBackground(false);
        MarkerViewActivity mv = new MarkerViewActivity(this,xAxis.getValueFormatter());
        mv.setChartView(mLineChart);
        mLineChart.setMarker(mv);
    }

    private void sendRequestWithOkHttp(final String title) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("title",title)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/api/weibo")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0;i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String view = jsonObject.getString("view");
                        entries.add(new Entry(i,Float.parseFloat(view)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}