package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner);

        final ArrayList<String> list = new ArrayList<>();
        list.add("서울시");
        list.add("경기도");
        list.add("인천시");
        list.add("부산시");
        list.add("대전시");
        list.add("대구시");
        list.add("충청북도");
        list.add("충청남도");
        list.add("세종시");
        list.add("강원도");
        list.add("부산광역시");
        list.add("부산광역시");
        list.add("부산광역시");
        list.add("부산광역시");


        String[] list2 = new String[2];
        list2[0] = "안녕";
        list2[1] = "하세요";

        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RESTfulAsyncTask restfulAsyncTask = new RESTfulAsyncTask(
                        MainActivity.this,
                        "https://sso1.mju.ac.kr/mju/userCheck.do",
                        "POST",
                        "Loading...",
                        null,
                        null,
                        new AsyncCallback() {
                            @Override
                            public void responseCallback(int statusCode, Set<Map.Entry<String, List<String>>> headers, JSONObject body) {
                                Toast.makeText(getApplicationContext(), body.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                restfulAsyncTask.execute();
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        if(System.currentTimeMillis() - lastTime < 1500){
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }

        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();

        lastTime = System.currentTimeMillis();
    }

}