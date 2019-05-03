package com.barmej.weatherforecasts.network;

import com.barmej.weatherforecasts.entity.WeatherForecasts;
import com.barmej.weatherforecasts.entity.WeatherInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface OpenWeatherApiInterface {

    /**
     * Current Weather endpoint
     */
    String WEATHER_ENDPOINT = "weather";

    /**
     * Coming forecasts endpoints
     */
    String FORECAST_ENDPOINT = "forecast";


    @GET(WEATHER_ENDPOINT)
    Call<WeatherInfo> getWeatherInfo(@QueryMap Map<String, String> queryParameters);

    @GET(FORECAST_ENDPOINT)
    Call<WeatherForecasts> getForecasts(@QueryMap Map<String, String> queryParameters);

}
