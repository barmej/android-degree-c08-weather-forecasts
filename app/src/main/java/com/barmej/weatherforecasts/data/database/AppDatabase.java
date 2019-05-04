package com.barmej.weatherforecasts.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.barmej.weatherforecasts.data.database.converters.DaysForecastsConverter;
import com.barmej.weatherforecasts.data.database.converters.HoursForecastsConverter;
import com.barmej.weatherforecasts.data.database.converters.WeatherListConverter;
import com.barmej.weatherforecasts.data.database.dao.ForecastDao;
import com.barmej.weatherforecasts.data.database.dao.WeatherInfoDao;
import com.barmej.weatherforecasts.data.entity.ForecastLists;
import com.barmej.weatherforecasts.data.entity.WeatherInfo;

@Database(entities = {WeatherInfo.class, ForecastLists.class}, version = 1, exportSchema = false)
@TypeConverters({WeatherListConverter.class, HoursForecastsConverter.class, DaysForecastsConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Instance of this class for Singleton
     */
    private static final Object LOCK = new Object();

    /**
     * Database file name
     */
    private static final String DATABASE_NAME = "weather_db";

    /**
     * Instance of this class for Singleton
     */
    private static AppDatabase sInstance;

    /**
     * Method used to get an instance of AppDatabase class
     *
     * @param context Context to use for Room initializations
     * @return an instance of AppDatabase class
     */
    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME
                ).build();
            }
        }
        return sInstance;
    }

    /**
     * Return object of WeatherInfoDao to read, write, delete and update weather info
     * @return an instance of weatherInfoDao
     */
    public abstract WeatherInfoDao weatherInfoDao();

    /**
     * Return object of ForecastDao to read, write, delete and update forecasts info
     * @return an instance of weatherInfoDao
     */
    public abstract ForecastDao forecastDao();

}
