package com.example.weibohotsearchactivity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weibohotsearchactivity.R;
import com.example.weibohotsearchactivity.damain.Weibo;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        final EditText updateId = findViewById(R.id.update_id);
        final EditText updateRank = findViewById(R.id.update_rank);
        final EditText updateTitle = findViewById(R.id.update_title);
        final EditText updateView = findViewById(R.id.update_view);
        Button subUpdate = findViewById(R.id.submit_update);
        subUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.submit_update) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject();
                                json.put("id",updateId.getText().toString());
                                json.put("rank",updateRank.getText().toString());
                                json.put("title",updateTitle.getText().toString());
                                json.put("view",updateView.getText().toString());
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody = RequestBody.create(JSON, json.toString());
                                Request request = new Request.Builder()
                                        .url("http://10.0.2.2:8080/weibo/update")
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                sendRequestWithOkHttp();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_insert:
                Intent intentInsert = new Intent(UpdateActivity.this, InsertActivity.class);
                startActivity(intentInsert);
                break;
            case R.id.item_delete:
                Intent intentDelete = new Intent(UpdateActivity.this, DeleteActivity.class);
                startActivity(intentDelete);
                break;
            case R.id.item_return:
                finish();
                break;
            case R.id.item_select:
                Intent intentSelect = new Intent(UpdateActivity.this, SelectActivity.class);
                startActivity(intentSelect);
                break;
            case R.id.item_all:
                sendRequestWithOkHttp();
            default:
        }
        return true;
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
                    Intent intent = new Intent(UpdateActivity.this, ListViewActivity.class);
                    intent.putExtra("list",response);
                    startActivity(intent);
            }
        });
    }
}