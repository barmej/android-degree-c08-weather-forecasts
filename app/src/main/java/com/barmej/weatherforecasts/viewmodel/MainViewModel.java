package com.barmej.weatherforecasts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.weatherforecasts.data.WeatherDataRepository;
import com.barmej.weatherforecasts.data.entity.ForecastLists;
import com.barmej.weatherforecasts.data.entity.WeatherInfo;

/**
 * ViewModel class that hold data requests and temporary that survive configuration changes
 */
public class MainViewModel extends AndroidViewModel {

    /**
     * An instance of WeatherDataRepository for all data related operations
     */
    private WeatherDataRepository mRepository;

    /**
     * LiveData object to wrap WeatherInfo  data
     */
    private LiveData<WeatherInfo> mWeatherInfoLiveData;

    /**
     * Forecasts object to wrap Forecasts data
     */
    private LiveData<ForecastLists> mForecastListsLiveData;

    /**
     * ViewModel Constructor
     *
     * @param application An instance of application class
     */
    public MainViewModel(@NonNull Application application) {
        super(application);

        // Get instance of WeatherDataRepository
        mRepository = WeatherDataRepository.getInstance(getApplication());

        // Request weather info data from the repository class
        mWeatherInfoLiveData = mRepository.getWeatherInfo();

        // Request forecasts lists  from the repository class
        mForecastListsLiveData = mRepository.getForecastsInfo();

    }

    /**
     * Get a handle of WeatherInfo LiveData object
     *
     * @return A wrapper LiveData object contains the weather info
     */
    public LiveData<WeatherInfo> getWeatherInfoLiveData() {
        return mWeatherInfoLiveData;
    }

    /**
     * Get a handle of ForecastsList LiveData object
     *
     * @return A wrapper LiveData object contains forecasts lists
     */
    public LiveData<ForecastLists> getForecastListsLiveData() {
        return mForecastListsLiveData;
    }

    @Override
    protected void onCleared() {
        // Cancel all ongoing requests
        mRepository.cancelDataRequests();
    }

}
