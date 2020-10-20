package com.example.weibohotsearchactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = (Button) findViewById(R.id.send_request);
        sendRequest.setOnClickListener(this);
        final EditText insertRank = (EditText) findViewById(R.id.insert_rank);
        final EditText insertTitle = (EditText) findViewById(R.id.insert_title);
        final EditText insertView = (EditText) findViewById(R.id.insert_view);
        final EditText updateId = (EditText) findViewById(R.id.update_id);
        final EditText updateRank = (EditText) findViewById(R.id.update_rank);
        final EditText updateTitle = (EditText) findViewById(R.id.update_title);
        final EditText updateView = (EditText) findViewById(R.id.update_view);
        final EditText deleteId = (EditText) findViewById(R.id.delete);
        Button subInsert = (Button) findViewById(R.id.submit_insert);
        subInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.submit_delete) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("search_rank", insertRank.getText().toString())
                                        .add("search_title", insertTitle.getText().toString())
                                        .add("search_view", insertView.getText().toString())
                                        .build();
                                Request request = new Request.Builder()
                                        .url("http://10.0.2.2:8080/WeiboHotSearch/Insert")
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String responseDate = response.body().toString();
                                Toast.makeText(MainActivity.this,responseDate,Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        Button subUpdate = (Button) findViewById(R.id.submit_update);
        subUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.submit_delete) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("id", updateId.getText().toString())
                                        .add("search_rank", updateRank.getText().toString())
                                        .add("search_title", updateTitle.getText().toString())
                                        .add("search_view", updateView.getText().toString())
                                        .build();
                                Request request = new Request.Builder()
                                        .url("http://10.0.2.2:8080/WeiboHotSearch/Edit")
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String responseDate = response.body().toString();
                                Toast.makeText(MainActivity.this,responseDate,Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        Button subDelete = (Button) findViewById(R.id.submit_delete);
        subDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.submit_delete) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("id", deleteId.getText().toString())
                                        .build();
                                Request request = new Request.Builder()
                                        .url("http://10.0.2.2:8080/WeiboHotSearch/Delete")
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String responseDate = response.body().toString();
                                Toast.makeText(MainActivity.this,responseDate,Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.send_request){
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/WeiboHotSearch/DB")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showRepose(responseData);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showRepose(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String[] data = new String[jsonArray.length()];
                    for (int i = 0;i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String id = jsonObject.getString("Id");
                        String rank = jsonObject.getString("Rank");
                        String title = jsonObject.getString("Title");
                        String view = jsonObject.getString("View");
//                        data[i] = id + " " + rank + " " + title + " " + view + ".";
                        data[i] = rank + " " + title + " " + view + ".";
                    }
                    Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                    intent.putExtra("text",data);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}