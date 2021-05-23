package com.example.android_project.ui.forecast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.android_project.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ForecastFragment extends Fragment {

    TextView result;

    class Weather extends AsyncTask<String, Void, String> {  //First String means URL is in String, Void mean nothing, Third String means Return type will be String

        @Override
        protected String doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);
        result = root.findViewById(R.id.resut);

        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("http://api.openweathermap.org/data/2.5/weather?q=Krasnoyarsk&appid=85fbe60a2c3d66dc8e4acba9cc11c936").get();
            Log.i("contentData", content);

            //JSON
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main");
            String windData = jsonObject.getString("wind");
            double visibility;
            Log.i("weatherData",weatherData);

            JSONArray array = new JSONArray(weatherData);

            String main = "";
            String description = "";
            String temperature = "";
            String pressure = "";
            String feels_like = "";
            String wind_speed = "";

            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }

            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");
            feels_like = mainPart.getString("feels_like");
            pressure = mainPart.getString("pressure");

            JSONObject windPart = new JSONObject(windData);
            wind_speed = windPart.getString("speed");

            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            int visibiltyInKilometer = (int) visibility / 1000;

            Log.i("Temperature", temperature);
            temperature = String.valueOf(Float.valueOf(temperature) - 273);
            feels_like = String.valueOf(Float.valueOf(feels_like) - 273);



            /*Log.i("main",main);
            Log.i("description",description);*/

            String resultText = "Main :                     " + main +
                    "\nDescription :        " + description +
                    "\nTemperature :        " + temperature + "*C" +
                    "\nFeels like :        " + feels_like + "*C" +
                    "\nPressure :        " + pressure + "hPa" +
                    "\nVisibility :              " + visibiltyInKilometer + " KM" +
                    "\nWind speed :        " + wind_speed + "m/s";

            result.setText(resultText);

            //Now we will show this result on screen

        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
}