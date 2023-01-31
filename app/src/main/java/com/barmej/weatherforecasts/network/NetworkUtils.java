package com.barmej.weatherforecasts.network;

import android.content.Context;

import com.barmej.weatherforecasts.R;
import com.barmej.weatherforecasts.utils.SharedPreferencesHelper;

import java.util.HashMap;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This utility class will be used to communicate with the weather servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /*
     * OpenWeatherMap's API Url
     */
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    /**
     * Current Weather endpoint
     */
    private static final String WEATHER_ENDPOINT = "weather";

    /**
     * Coming forecasts endpoints
     */
    private static final String FORECAST_ENDPOINT = "forecast";

    /**
     * The query parameter allows us to determine the location
     */
    private static final String QUERY_PARAM = "q";


    /* The FORMAT parameter allows us to designate whether we want JSON or XML from our API */
    private static final String FORMAT_PARAM = "mode";

    /**
     * Units parameter allows us to designate whether we want metric units or imperial units
     */
    private static final String UNITS_PARAM = "units";

    /**
     * Lang parameter to specify the language of the response
     */
    private static final String LANG_PARAM = "lang";

    /**
     * The app id allow us to pass our API key to OpenWeatherMap to be a valid response
     */
    private static final String APP_ID_PARAM = "appid";

    /**
     * The FORMAT we want our API to return
     */
    private static final String FORMAT = "json";

    /**
     * Object used for the purpose of synchronize lock
     */
    private static final Object LOCK = new Object();


    /**
     * Instance of this class for Singleton
     */
    private static NetworkUtils sInstance;

    /**
     * Instance of the application context
     */
    private Context mContext;

    /**
     * Instance of Volley OpenWeatherApiInterface
     */
    private OpenWeatherApiInterface mApiInterface;


    /**
     * @param context Context to use for some initializations
     */
    private NetworkUtils(Context context) {
        // getApplicationContext() is key, it keeps your application safe from leaking the
        // Activity or BroadcastReceiver if you pass it instead of application context
        mContext = context.getApplicationContext();

        // Init retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Get an instance of OpenWeatherApiInterface implementation
        mApiInterface = retrofit.create(OpenWeatherApiInterface.class);

    }

    /**
     * Method used to get an instance of NetworkUtils class
     *
     * @param context Context to use for some initializations
     * @return an instance of NetworkUtils class
     */
    public static synchronized NetworkUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) sInstance = new NetworkUtils(context);
            }
        }
        return sInstance;
    }

    /**
     * Builds the query parameters map to get the weather data using a location.
     * This location is based on the query capabilities of the weather provider that we are using.
     *
     * @param context  context object to use for reading string resources
     * @return The URL to use to query the weather server.
     */
    public static HashMap getQueryMap(Context context) {
        HashMap map = new HashMap();
        map.put(QUERY_PARAM, SharedPreferencesHelper.getPreferredWeatherLocation(context));
        map.put(UNITS_PARAM, SharedPreferencesHelper.getPreferredMeasurementSystem(context));
        map.put(LANG_PARAM, Locale.getDefault().getLanguage());
        map.put(FORMAT_PARAM, FORMAT);
        map.put(APP_ID_PARAM, context.getString(R.string.api_key));
        return map;
    }

    /**
     * Get an instance of OpenWeatherApiInterface
     *
     * @return an instance of OpenWeatherApiInterface
     */
    public OpenWeatherApiInterface getApiInterface() {
        return mApiInterface;
    }


}