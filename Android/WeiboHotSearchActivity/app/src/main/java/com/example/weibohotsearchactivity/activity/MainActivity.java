package com.example.weibohotsearchactivity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.weibohotsearchactivity.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button listView = findViewById(R.id.listView);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });
        Button barChart = findViewById(R.id.barChart);
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barChartIntent = new Intent(MainActivity.this, BarChartActivity.class);
                startActivity(barChartIntent);
            }
        });
        Button pieChart = findViewById(R.id.pieChart);
        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barChartIntent = new Intent(MainActivity.this, PieChartActivity.class);
                startActivity(barChartIntent);
            }
        });
        Button lineChart = findViewById(R.id.lineChart);
        lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barChartIntent = new Intent(MainActivity.this, LineChartActivity.class);
                startActivity(barChartIntent);
            }
        });
        Button scatterChart = findViewById(R.id.scatterChart);
        scatterChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barChartIntent = new Intent(MainActivity.this, ScatterChartActivity.class);
                startActivity(barChartIntent);
            }
        });
        Button terms = findViewById(R.id.terms);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttpTerms();
            }
        });
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .build();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/weibo")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showRepose(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendRequestWithOkHttpTerms() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .build();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/terms")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showReposeTerms(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showRepose(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                    intent.putExtra("list", response);
                    startActivity(intent);
            }
        });
    }

    private void showReposeTerms(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, TermsActivity.class);
                intent.putExtra("listTerms", response);
                startActivity(intent);
            }
        });
    }
}