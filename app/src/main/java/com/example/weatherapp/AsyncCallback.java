package com.example.weatherapp;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AsyncCallback {
    void responseCallback(int statusCode, Set<Map.Entry<String, List<String>>> headers, JSONObject body);
}
