package com.example.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RESTfulAsyncTask extends AsyncTask<String, String, String> {
    private final ProgressDialog progressDialog;
    private final String url, httpMethod;
    private final Map<String, String> requestHeaders;
    private final JSONObject requestBody;
    private final AsyncCallback asyncCallback;
    private Set<Map.Entry<String, List<String>>> responseHeaders;
    private int statusCode;

    public RESTfulAsyncTask(Context context, String url, String httpMethod, String loadingMsg, Map<String, String> requestHeaders, JSONObject requestBody, AsyncCallback asyncCallback) {
        super();
        this.url = url;
        this.httpMethod = httpMethod;
        if (loadingMsg != null) {
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setMessage(loadingMsg);
            this.progressDialog.setCancelable(false);
        } else {
            this.progressDialog = null;
        }
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.asyncCallback = asyncCallback;
        this.statusCode = -1;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.progressDialog != null) {
            this.progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder result = new StringBuilder();
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(this.url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(this.httpMethod);
                urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                if (!this.httpMethod.equals("GET")) {
                    urlConnection.setDoOutput(true);
                }
                if (this.requestHeaders != null) {
                    for (Map.Entry<String, String> entry : this.requestHeaders.entrySet()) {
                        urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                if (this.requestBody != null) {
                    OutputStream os = urlConnection.getOutputStream();
                    byte[] requestInput = this.requestBody.toString().getBytes();
                    os.write(requestInput, 0, requestInput.length);
                }
                this.statusCode = urlConnection.getResponseCode();
                InputStream in = this.statusCode == HttpURLConnection.HTTP_OK ? urlConnection.getInputStream() : urlConnection.getErrorStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                while (data != -1) {
                    result.append((char) data);
                    data = isw.read();
                }
                this.responseHeaders = urlConnection.getHeaderFields().entrySet();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        try {
            JSONObject responseBody = new JSONObject(s);
            if (this.statusCode != -1) {
                this.asyncCallback.responseCallback(this.statusCode, this.responseHeaders, responseBody);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
