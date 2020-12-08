package com.example.weibohotsearchactivity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.weibohotsearchactivity.R;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.scatter.CircleShapeRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScatterChartActivity extends AppCompatActivity implements OnChartValueSelectedListener, View.OnClickListener {

    private ScatterChart mScatterChart;

    //显示顶点值
    private Button btn_show_values;
    //x轴动画
    private Button btn_anim_x;
    //y轴动画
    private Button btn_anim_y;
    //xy轴动画
    private Button btn_anim_xy;
    private String[] title = new String[5];
    private ArrayList<Entry> yVals1 = new ArrayList<>();
    private ArrayList<Entry> yVals2 = new ArrayList<>();
    private ArrayList<Entry> yVals3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_chart);
        initView();
    }

    //初始化View
    private void initView() {

        //基本控件
        btn_show_values = findViewById(R.id.btn_show_values);
        btn_show_values.setOnClickListener(this);
        btn_anim_x = findViewById(R.id.btn_anim_x);
        btn_anim_x.setOnClickListener(this);
        btn_anim_y = findViewById(R.id.btn_anim_y);
        btn_anim_y.setOnClickListener(this);
        btn_anim_xy = findViewById(R.id.btn_anim_xy);
        btn_anim_xy.setOnClickListener(this);

        //散点图
        mScatterChart = (ScatterChart) findViewById(R.id.mScatterChart);
        mScatterChart.getDescription().setEnabled(false);
        mScatterChart.setOnChartValueSelectedListener(this);

        mScatterChart.setDrawGridBackground(false);
        mScatterChart.setTouchEnabled(true);
        mScatterChart.setMaxHighlightDistance(10f);

        // 支持缩放和拖动
        mScatterChart.setDragEnabled(true);
        mScatterChart.setScaleEnabled(true);

        mScatterChart.setMaxVisibleValueCount(5000000);
        mScatterChart.getAxisLeft().setAxisMinimum(2000000);
        mScatterChart.getAxisLeft().setDrawGridLines(false);
        mScatterChart.setPinchZoom(true);

        Legend l = mScatterChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXOffset(5f);

        mScatterChart.getAxisRight().setEnabled(false);

        XAxis xl = mScatterChart.getXAxis();
        xl.setDrawGridLines(false);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        initData();
    }

    //设置数据
    private void setData() {

        //创建一个数据集,并给它一个类型
        ScatterDataSet set1 = new ScatterDataSet(sendRequestWithOkHttp(title[0], yVals1), title[0]);
        set1.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        //设置颜色
        set1.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        set1.setDrawValues(false);
        ScatterDataSet set2 = new ScatterDataSet(sendRequestWithOkHttp(title[1], yVals2), title[1]);
        set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set2.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[3]);
        set2.setScatterShapeHoleRadius(3f);
        set2.setColor(ColorTemplate.COLORFUL_COLORS[1]);
        set2.setDrawValues(false);
        ScatterDataSet set3 = new ScatterDataSet(sendRequestWithOkHttp(title[2], yVals3), title[2]);
        set3.setShapeRenderer(new CircleShapeRenderer());
        set3.setColor(ColorTemplate.COLORFUL_COLORS[2]);
        set3.setDrawValues(false);

        set1.setScatterShapeSize(12f);
        set2.setScatterShapeSize(12f);
        set3.setScatterShapeSize(12f);

        ArrayList<IScatterDataSet> dataSets = new ArrayList<IScatterDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);

        //创建一个数据集的数据对象
        ScatterData data = new ScatterData(dataSets);
        data.setValueTextSize(12f);

        mScatterChart.setData(data);
        mScatterChart.invalidate();
    }

    private void initData() {
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
                    for (int i = 0; i < 5; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String titles = jsonObject.getString("title");
                        title[i] = titles;
                    }
                    setData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
            for (int i = 0; i < 10; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String views = jsonObject.getString("view");
                list.add(new Entry(i, Float.parseFloat(views)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(ScatterChartActivity.this,"" + e.getY(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //显示顶点值
            case R.id.btn_show_values:
                for (IDataSet set : mScatterChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mScatterChart.invalidate();
                break;
            //x轴动画
            case R.id.btn_anim_x:
                mScatterChart.animateX(3000);
                break;
            //y轴动画
            case R.id.btn_anim_y:
                mScatterChart.animateY(3000);
                break;
            //xy轴动画
            case R.id.btn_anim_xy:
                mScatterChart.animateXY(3000, 3000);
                break;
        }
    }
}