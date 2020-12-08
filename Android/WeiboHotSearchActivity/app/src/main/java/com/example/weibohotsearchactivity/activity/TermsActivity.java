package com.example.weibohotsearchactivity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weibohotsearchactivity.R;
import com.example.weibohotsearchactivity.adapter.TermsAdapter;
import com.example.weibohotsearchactivity.adapter.WeiboAdapter;
import com.example.weibohotsearchactivity.damain.Terms;
import com.example.weibohotsearchactivity.damain.Weibo;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TermsActivity extends AppCompatActivity {

    private List<Terms> list = new ArrayList<>();
    private int count = 0;
    private int oldCount;
    private boolean click;
    private int clickCount = 0;
    private Button up;
    private Button down;
    private Button go;
    private EditText editText;
    private ListView listView;
    private TermsAdapter adapter;
    private boolean judge = true;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        initData(count);
        Toolbar toolbar = findViewById(R.id.terms);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.line:
                        Intent intentDiagram = new Intent(TermsActivity.this, TenLineChartActivity.class);
                        startActivity(intentDiagram);
                        break;
                    case R.id.pie:
                        Intent intentPieChart = new Intent(TermsActivity.this, PieChartActivity.class);
                        startActivity(intentPieChart);
                        break;
                    case R.id.bar:
                        Intent intentBarChart = new Intent(TermsActivity.this, BarChartActivity.class);
                        startActivity(intentBarChart);
                        break;
                    case R.id.scatter:
                        Intent intentScatterChart = new Intent(TermsActivity.this, ScatterChartActivity.class);
                        startActivity(intentScatterChart);
                        break;
                    case R.id.delete:
                        Intent intentDelete = new Intent(TermsActivity.this, DeleteActivity.class);
                        startActivity(intentDelete);
                        break;
                    case R.id.update:
                        Intent intentUpdate = new Intent(TermsActivity.this, UpdateActivity.class);
                        startActivity(intentUpdate);
                        break;
                    case R.id.add:
                        Intent intentInsert = new Intent(TermsActivity.this, InsertActivity.class);
                        startActivity(intentInsert);
                        break;
                    case R.id.select:
                        Intent intentSelect = new Intent(TermsActivity.this, SelectActivity.class);
                        startActivity(intentSelect);
                        break;
                    default:
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
        up = findViewById(R.id.up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 1) {
                    judge = false;
                    initData(count - 2);
                } else {
                    Toast.makeText(TermsActivity.this, "已经是第一页了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        down = findViewById(R.id.down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    judge = true;
                    Intent intent = getIntent();
                    String jsonData = intent.getStringExtra("listTerms");
                    JSONArray jsonArray = new JSONArray(jsonData);
                    if (count < (Integer.parseInt(String.valueOf(jsonArray.length())) / 25) + 1) {
                        initData(count);
                    } else {
                        Toast.makeText(TermsActivity.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = getIntent();
                    String jsonData = intent.getStringExtra("listTerms");
                    JSONArray jsonArray = new JSONArray(jsonData);
                    count = Integer.parseInt(editText.getText().toString()) - 1;
                    if (count > (Integer.parseInt(String.valueOf(jsonArray.length())) / 25)) {
                        count = Integer.parseInt(String.valueOf(jsonArray.length())) / 25;
                        initData(Integer.parseInt(String.valueOf(jsonArray.length())) / 25);
                    } else {
                        judge = true;
                        initData(count);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Terms test = list.get(position);
                Toast.makeText(TermsActivity.this, "Click " + test.getTerm(), Toast.LENGTH_SHORT).show();
                if (click) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    listView.invalidate();
                }
            }
        });
    }

    private void initData(int num) {
        try {
            list.clear();
            Intent intent = getIntent();
            String jsonData = intent.getStringExtra("listTerms");
            JSONArray jsonArray = new JSONArray(jsonData);
            oldCount = num * 25;
            if (judge) {
                count = count + 1;
            } else {
                count = count - 1;
            }
            editText = findViewById(R.id.edit_text);
            String page = String.valueOf(count);
            editText.setText(page);
            for (num = oldCount; num < oldCount + 25; num++) {
                if (num < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(num);
                    String id = jsonObject.getString("id");
                    String term = jsonObject.getString("term");
                    String number = jsonObject.getString("number");
                    Terms terms = new Terms(id, term,number);
                    list.add(terms);
                }
            }
            adapter = new TermsAdapter(TermsActivity.this,
                    R.layout.list_item, list);
            listView = findViewById(R.id.list_view1);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete:
                Intent intentDelete = new Intent(TermsActivity.this, DeleteActivity.class);
                startActivity(intentDelete);
                break;
            case R.id.item_update:
                Intent intentUpdate = new Intent(TermsActivity.this, UpdateActivity.class);
                startActivity(intentUpdate);
                break;
            case R.id.item_return:
                finish();
                finish();
                break;
            case R.id.item_insert:
                Intent intentInsert = new Intent(TermsActivity.this, InsertActivity.class);
                startActivity(intentInsert);
                break;
            case R.id.item_select:
                Intent intentSelect = new Intent(TermsActivity.this, SelectActivity.class);
                startActivity(intentSelect);
                break;
            case R.id.delete_search:
                clickCount++;
                if (clickCount % 2 == 0) {
                    click = false;
                    Toast.makeText(TermsActivity.this, "Return Delete", Toast.LENGTH_SHORT).show();
                } else {
                    click = true;
                    Toast.makeText(TermsActivity.this, "Click  Delete", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pie_chart:
                Intent intentPieChart = new Intent(TermsActivity.this, PieChartActivity.class);
                startActivity(intentPieChart);
                break;
            case R.id.bar_chart:
                Intent intentBarChart = new Intent(TermsActivity.this, BarChartActivity.class);
                startActivity(intentBarChart);
                break;
            case R.id.scatter_chart:
                Intent intentScatterChart = new Intent(TermsActivity.this, ScatterChartActivity.class);
                startActivity(intentScatterChart);
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
}