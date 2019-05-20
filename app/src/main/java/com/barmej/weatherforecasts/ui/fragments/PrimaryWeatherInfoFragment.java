package com.barmej.weatherforecasts.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.barmej.weatherforecasts.R;
import com.barmej.weatherforecasts.data.entity.WeatherInfo;
import com.barmej.weatherforecasts.databinding.FragmentPrimaryWeatherInfoBinding;
import com.barmej.weatherforecasts.viewmodel.MainViewModel;

/**
 * A fragment that show primary weather information like high and low temperatures, weather icon,
 * weather description, today's date and current city.
 * You can create an instance of this fragment and embed or add to other activity or fragment!
 */
public class PrimaryWeatherInfoFragment extends Fragment {

    /**
     * An instance of auto generated data binding class
     */
    private FragmentPrimaryWeatherInfoBinding mBinding;

    /**
     * Required empty public constructor
     */
    public PrimaryWeatherInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout using data binding class
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_primary_weather_info, container, false);
        // Return the inflated view object
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            // Get a handle on the MainViewModel of the host Activity
            MainViewModel mainViewModel = ViewModelProviders.of(activity).get(MainViewModel.class);
            // Observe data change to populate UI with the new data
            mainViewModel.getWeatherInfoLiveData().observe(this, new Observer<WeatherInfo>() {
                @Override
                public void onChanged(@Nullable WeatherInfo weatherInfo) {
                    // Add java object to data binding to update data on UI
                    mBinding.setWeatherInfo(weatherInfo);
                }
            });
        }

    }


}
