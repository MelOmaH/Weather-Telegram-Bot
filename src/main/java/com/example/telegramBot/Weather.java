package com.example.telegramBot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+message
                + "&units=metric&appid=b527e0ce18c78aa688dbe343eddc75dd");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getInt("temp"));
        model.setFeelsLike(main.getInt("feels_like"));
        model.setHumidity(main.getDouble("humidity"));

        JSONObject sys = object.getJSONObject("sys");
        model.setCountry(sys.getString("country"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i=0; i<getArray.length(); i++){
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }

        return "City: " +model.getName() + "\n" +
                "Country: " +model.getCountry() + "\n" +
                "Weather: " + model.getMain() + "\n"+
                "Temperature: " + model.getTemp() + "°" + "\n" +
                "Feels like: " + model.getFeelsLike() + "°" + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "http://openweathermap.org/img/wn/" +model.getIcon()+ ".png";
    }
}
