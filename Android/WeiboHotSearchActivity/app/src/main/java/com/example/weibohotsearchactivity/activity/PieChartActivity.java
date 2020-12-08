package com.example.weibohotsearchactivity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.weibohotsearchactivity.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PieChartActivity extends AppCompatActivity {

    private List<PieEntry> list = new ArrayList<>();
    private float[] view = new float[10];
    private String[] title = new String[10];
    private String data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        Button button = findViewById(R.id.linechart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null){
                    Intent intent = new Intent(PieChartActivity.this,LineChartActivity.class);
                    intent.putExtra("title",data);
                    startActivity(intent);
                }
            }
        });

        initData();

        final PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setNoDataText("没有数据");

        final PieDataSet pieDataSet = new PieDataSet(list,"");
        pieDataSet.setColors(Color.parseColor("#feb64d"),
                Color.parseColor("#ff7c7c"),
                Color.parseColor("#9287e7"),
                Color.parseColor("#6200ee"),
                Color.parseColor("#03dac5"),
                Color.parseColor("#ff07cb"),
                Color.parseColor("#ffeb61"),
                Color.parseColor("#ffacba"),
                Color.parseColor("#ff0000"),
                Color.parseColor("#00ff00"));
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieData.setValueTextColor(Color.parseColor("#000000"));
        pieData.setValueTextSize(12);
        Description description = new Description();
        description.setText("");
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setFormSize(12f);
        legend.setTextSize(14f);
        legend.setWordWrapEnabled(true);
        pieChart.setDescription(description);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(false);
        pieChart.animateXY(1000,1000);
        pieChart.invalidate();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null){return;}
                for (int i = 0; i < view.length;i++){
                    if (view[i] == e.getY()){
                        data = title[i];
                        Toast.makeText(PieChartActivity.this,title[i],Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected() {
                data = null;
            }
        });
    }
    private void initData(){
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
                        String views = jsonObject.getString("view");
                        view[i] = Float.parseFloat(views);
                        title[i] = titles;
                        list.add(new PieEntry(view[i],title[i]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}