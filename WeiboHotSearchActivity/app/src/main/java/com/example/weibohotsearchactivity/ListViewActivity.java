package com.example.weibohotsearchactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.Person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weibohotsearchactivity.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<>();
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Intent intent = getIntent();
        final String[] data = intent.getStringArrayExtra("text");
        count = 0;
        int oldCount = count;
        for (count = oldCount; count < oldCount + 15; count++){
            if (count < data.length) {
                list.add(data[count]);
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListViewActivity.this,
                android.R.layout.simple_list_item_1,list);
        final ListView listView = (ListView) findViewById(R.id.list_view1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String test = list.get(position);
                Toast.makeText(ListViewActivity.this,"Click " + test,Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0) { // 不为0则表示有下拉动作
                    if ((firstVisibleItem + visibleItemCount) > totalItemCount - 2)
                    { // 当前第一个完全可见的item再下拉一个页面长度，即变为倒数第二个时
                        // 在此加载数据
                        int oldCount = count;
                        for (count = oldCount; count < oldCount + 15; count++) {
                            if (count < data.length){
                                list.add(data[count]);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

        });
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}