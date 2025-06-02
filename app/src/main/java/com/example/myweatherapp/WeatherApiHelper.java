package com.example.myweatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.IOException;


public class WeatherApiHelper {
    public interface WeatherCallback {
        void onSuccess(String weatherInfo, double avgTemp);
    }

    public static void getWeatherData(int stnId, WeatherCallback callback) {
        new Thread(() -> {
            try {
                String api = "http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList"
                        + "?serviceKey=발급받은API키"
                        + "&dataType=JSON&dataCd=ASOS&dateCd=DAY"
                        + "&startDt=20240525&endDt=20240525"
                        + "&stnIds=" + stnId;

                HttpURLConnection conn = (HttpURLConnection) new URL(api).openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) result.append(line);
                reader.close();

                JSONObject today = new JSONObject(result.toString())
                        .getJSONObject("response").getJSONObject("body")
                        .getJSONObject("items").getJSONArray("item")
                        .getJSONObject(0);

                String info = today.getString("tm") + "\n평균: " + today.getString("avgTa")
                        + "℃\n최저: " + today.getString("minTa") + "℃\n최고: " + today.getString("maxTa") + "℃";

                callback.onSuccess(info, today.getDouble("avgTa"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
