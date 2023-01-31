package com.barmej.weatherforecasts.data.network;

import com.barmej.weatherforecasts.data.entity.WeatherForecasts;
import com.barmej.weatherforecasts.data.entity.WeatherInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Retrofit API interface used to access data from OpenWeatherMap APIs
 */
public interface OpenWeatherApiInterface {

    /**
     * Current Weather endpoint
     */
    String WEATHER_ENDPOINT = "weather";

    /**
     * Coming forecasts endpoints
     */
    String FORECAST_ENDPOINT = "forecast";


    /**
     * @param queryParameters query parameters in a HashMap object
     * @return object of Retrofit Callback
     */
    @GET(WEATHER_ENDPOINT)
    Call<WeatherInfo> getWeatherInfo(@QueryMap HashMap<String, String> queryParameters);

    /**
     * @param queryParameters query parameters in a HashMap object
     * @return object of Retrofit Callback
     */
    @GET(FORECAST_ENDPOINT)
    Call<WeatherForecasts> getForecasts(@QueryMap HashMap<String, String> queryParameters);

}