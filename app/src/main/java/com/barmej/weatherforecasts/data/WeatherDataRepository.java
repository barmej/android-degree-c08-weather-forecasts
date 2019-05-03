package com.barmej.weatherforecasts.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.barmej.weatherforecasts.data.entity.ForecastLists;
import com.barmej.weatherforecasts.data.entity.WeatherForecasts;
import com.barmej.weatherforecasts.data.entity.WeatherInfo;
import com.barmej.weatherforecasts.data.network.NetworkUtils;
import com.barmej.weatherforecasts.utils.OpenWeatherDataParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherDataRepository {

    private static final String TAG = WeatherDataRepository.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static WeatherDataRepository sInstance;
    private NetworkUtils mNetworkUtils;
    private Call<WeatherInfo> mWeatherCall;
    private Call<WeatherForecasts> mForecastsCall;

    /**
     * @param context Context to use for some initializations
     */
    private WeatherDataRepository(Context context) {
        mNetworkUtils = NetworkUtils.getInstance(context);
    }

    /**
     * Method used to get an instance of WeatherDataRepository class
     *
     * @param context Context to use for some initializations
     * @return an instance of WeatherDataRepository class
     */
    public static WeatherDataRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null)
                    sInstance = new WeatherDataRepository(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    /**
     * Get current weather data
     *
     * @return LiveData object to be notified when data change
     */
    public LiveData<WeatherInfo> getWeatherInfo() {

        // Create MutableLiveData to observe data changes
        final MutableLiveData<WeatherInfo> weatherInfoLiveData = new MutableLiveData<>();

        // Create a new WeatherInfo call using Retrofit API interface
        mWeatherCall = mNetworkUtils.getApiInterface().getWeatherInfo(mNetworkUtils.getQueryMap());

        // Add request to the queue to be executed asynchronously
        mWeatherCall.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(@NonNull Call<WeatherInfo> call, @NonNull Response<WeatherInfo> response) {
                if (response.code() == 200) {
                    // Get WeatherInfo object from response body
                    WeatherInfo weatherInfo = response.body();
                    if (weatherInfo != null) {
                        weatherInfoLiveData.setValue(weatherInfo);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherInfo> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        return weatherInfoLiveData;
    }

    /**
     * Get forecasts data
     *
     * @return LiveData object to be notified when data change
     */
    public LiveData<ForecastLists> getForecastsInfo() {

        // Create MutableLiveData to observe data changes
        final MutableLiveData<ForecastLists> forecastsLiveData = new MutableLiveData<>();

        // Create a new WeatherForecasts call using Retrofit API interface
        mForecastsCall = mNetworkUtils.getApiInterface().getForecasts(mNetworkUtils.getQueryMap());

        // Add request to the queue to be executed asynchronously
        mForecastsCall.enqueue(new Callback<WeatherForecasts>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecasts> call, @NonNull Response<WeatherForecasts> response) {
                if (response.code() == 200) {
                    WeatherForecasts weatherForecasts = response.body();
                    if (weatherForecasts != null) {
                        ForecastLists forecastLists = OpenWeatherDataParser.getForecastsDataFromWeatherForecasts(weatherForecasts);
                        forecastsLiveData.setValue(forecastLists);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecasts> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        return forecastsLiveData;
    }

    /**
     * Cancel all data requests
     */
    public void cancelDataRequests() {
        mWeatherCall.cancel();
        mForecastsCall.cancel();
    }


}