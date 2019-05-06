package com.barmej.weatherforecasts.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.barmej.weatherforecasts.data.entity.WeatherInfo;

/**
 * Data access object interface used by Room to query, insert, update and delete
 * weather info from database
 */
@Dao
public interface WeatherInfoDao {

    @Query("SELECT * FROM weather_info LIMIT 1")
    LiveData<WeatherInfo> getWeatherInfo();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addWeatherInfo(WeatherInfo weatherInfo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWeatherInfo(WeatherInfo weatherInfo);

    @Delete
    void deleteWeatherInfo(WeatherInfo weatherInfo);

    @Query("DELETE FROM weather_info")
    void deleteAllWeatherInfo();

}
