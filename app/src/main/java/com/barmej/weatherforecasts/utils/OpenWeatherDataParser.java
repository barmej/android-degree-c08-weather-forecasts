package com.barmej.weatherforecasts.utils;

import android.util.Log;

import com.barmej.weatherforecasts.data.entity.Forecast;
import com.barmej.weatherforecasts.data.entity.ForecastLists;
import com.barmej.weatherforecasts.data.entity.WeatherForecasts;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This utility contains methods to handle OpenWeatherMap JSON data.
 */
public class OpenWeatherDataParser {

    private static final String TAG = OpenWeatherDataParser.class.getSimpleName();

    /**
     * Operation status code
     */
    private static final String OWM_MESSAGE_CODE = "cod";

    /**
     * Location information
     */
    private static final String OWM_CITY_NAME = "name";

    /**
     * Date and time
     */
    private static final String OWM_DATE = "dt";

    /**
     * Wind information
     */
    private static final String OWM_WIND = "wind";
    private static final String OWM_WINDSPEED = "speed";
    private static final String OWM_WIND_DIRECTION = "deg";

    /**
     * Main weather Information
     */
    private static final String OWM_MAIN = "main";
    private static final String OWM_TEMPERATURE = "temp";
    private static final String OWM_MAX = "temp_max";
    private static final String OWM_MIN = "temp_min";
    private static final String OWM_PRESSURE = "pressure";
    private static final String OWM_HUMIDITY = "humidity";

    /**
     * Weather condition information
     */
    private static final String OWM_WEATHER = "weather";
    private static final String OWM_WEATHER_DESCRIPTION = "description";
    private static final String OWM_WEATHER_ICON = "icon";

    /**
     * Sunrise and Sunset times
     */
    private static final String OWM_SYS = "sys";
    private static final String OWM_SUNRISE = "sunrise";
    private static final String OWM_SUNSET = "sunset";


    /**
     * Check if there is an error in the response json
     *
     * @param jsonObject the response json object that we got from the server
     * @return whenever true if the response code = 200, false otherwise
     */
    private static boolean isError(JSONObject jsonObject) {
        try {
            // Check the response code to see if there is an error
            if (jsonObject.has(OWM_MESSAGE_CODE)) {
                int errorCode = jsonObject.getInt(OWM_MESSAGE_CODE);
                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        return false;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        Log.e(TAG, "Location Invalid");
                    default:
                        Log.e(TAG, "Server probably down");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * This method parses JSON from a web response and returns a java object contain the forecasts
     * data over various days.
     *
     * @param weatherForecasts object of WeatherResponse that contains the forecasts list we got
     *                         from OpenWeatherMap forecast endpoint
     * @return Object of {@link ForecastLists} contains two arrays, the first one for the next 24hrs forecast and the seconds
     * for the next 4 days forecasts
     */
    public static ForecastLists getForecastsDataFromWeatherForecasts(WeatherForecasts weatherForecasts) {

        List<Forecast> hoursForecasts = new ArrayList<>();
        LinkedHashMap<String, List<Forecast>> daysForecasts = new LinkedHashMap<>();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String currentDay = df.format(new Date());
        int hoursForecastsCount = 0;

        for (int i = 0; i < weatherForecasts.getList().size(); i++) {

            Forecast forecast = weatherForecasts.getList().get(i);

            if (hoursForecastsCount++ < 8) {
                hoursForecasts.add(forecast);
            }

            String date = forecast.getDtTxt().split(" ")[0];

            if (!date.equals(currentDay)) {
                if (daysForecasts.containsKey(date)) {
                    List<Forecast> forecasts = daysForecasts.get(date);
                    assert forecasts != null;
                    forecasts.add(forecast);
                } else {
                    List<Forecast> forecasts = new ArrayList<>();
                    forecasts.add(forecast);
                    daysForecasts.put(date, forecasts);
                }
            }

        }

        ForecastLists forecastsData = new ForecastLists();
        forecastsData.setHoursForecasts(hoursForecasts);
        List<List<Forecast>> listOfDaysForecasts = new ArrayList<>();
        for (Map.Entry entry : daysForecasts.entrySet()) {
            listOfDaysForecasts.add((List<Forecast>) entry.getValue());
        }
        forecastsData.setDaysForecasts(listOfDaysForecasts);

        return forecastsData;
    }


}
