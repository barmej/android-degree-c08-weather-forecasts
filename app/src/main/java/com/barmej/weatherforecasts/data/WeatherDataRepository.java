package com.barmej.weatherforecasts.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.barmej.weatherforecasts.data.database.AppDatabase;
import com.barmej.weatherforecasts.data.entity.ForecastLists;
import com.barmej.weatherforecasts.data.entity.WeatherForecasts;
import com.barmej.weatherforecasts.data.entity.WeatherInfo;
import com.barmej.weatherforecasts.data.network.NetworkUtils;
import com.barmej.weatherforecasts.utils.AppExecutor;
import com.barmej.weatherforecasts.utils.OpenWeatherDataParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherDataRepository {

    private static final String TAG = WeatherDataRepository.class.getSimpleName();

    /**
     * Object used for the purpose of synchronize lock
     */
    private static final Object LOCK = new Object();

    /**
     * Instance of this class for Singleton
     */
    private static WeatherDataRepository sInstance;

    /**
     * Instance of NetworkUtils to perform network operations
     */
    private NetworkUtils mNetworkUtils;

    /**
     * Instance of AppDatabase to perform database operation
     */
    private AppDatabase mAppDatabase;

    /**
     * Instance of AppExecutor to perform tasks on worker threads
     */
    private AppExecutor mAppExecutor;

    /**
     * Retrofit Call objects
     */
    private Call<WeatherInfo> mWeatherCall;
    private Call<WeatherForecasts> mForecastsCall;

    /**
     * @param context Context to use for some initializations
     */
    private WeatherDataRepository(Context context) {
        mNetworkUtils = NetworkUtils.getInstance(context);
        mAppDatabase = AppDatabase.getInstance(context);
        mAppExecutor = AppExecutor.getInstance();
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

        // Get LiveData object from database using Room
        final LiveData<WeatherInfo> weatherInfoLiveData = mAppDatabase.weatherInfoDao().getWeatherInfo();

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
                        updateWeatherInfo(weatherInfo);
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

        // Get LiveData object from database using Room
        final LiveData<ForecastLists> forecastsLiveData = mAppDatabase.forecastDao().getForecasts();

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
                        updateForecastLists(forecastLists);
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


    /**
     * Empty weather info table and save new weather info to database
     *
     * @param weatherInfo new weather info object we got from API
     */
    public void updateWeatherInfo(final WeatherInfo weatherInfo) {
        mAppExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.weatherInfoDao().deleteAllWeatherInfo();
                mAppDatabase.weatherInfoDao().addWeatherInfo(weatherInfo);
            }
        });
    }

    /**
     * Empty forecasts table and save new forecasts info to database
     *
     * @param forecastLists new forecasts list we got from API
     */
    public void updateForecastLists(final ForecastLists forecastLists) {
        mAppExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.forecastDao().deleteAllForecastsInfo();
                mAppDatabase.forecastDao().addForecastsList(forecastLists);
            }
        });
    }


}