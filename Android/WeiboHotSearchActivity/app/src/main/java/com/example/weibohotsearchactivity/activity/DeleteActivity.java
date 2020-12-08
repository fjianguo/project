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

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        final EditText deleteId = (EditText) findViewById(R.id.delete);
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
                                        .url("http://10.0.2.2:8080/weibo/delete")
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
        getMenuInflater().inflate(R.menu.delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_insert:
                Intent intentInsert = new Intent(DeleteActivity.this, InsertActivity.class);
                startActivity(intentInsert);
                break;
            case R.id.item_update:
                Intent intentUpdate = new Intent(DeleteActivity.this, UpdateActivity.class);
                startActivity(intentUpdate);
                break;
            case R.id.item_return:
                finish();
                break;
            case R.id.item_select:
                Intent intentSelect = new Intent(DeleteActivity.this, SelectActivity.class);
                startActivity(intentSelect);
                break;
            case R.id.item_all:
                sendRequestWithOkHttp();
                break;
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
                Intent intent = new Intent(DeleteActivity.this, ListViewActivity.class);
                intent.putExtra("list",response);
                startActivity(intent);
            }
        });
    }
}