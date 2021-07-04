package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}