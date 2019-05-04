package com.barmej.weatherforecasts.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.barmej.weatherforecasts.data.entity.ForecastLists;

@Dao
public interface ForecastDao {

    @Query("SELECT * FROM forecasts")
    LiveData<ForecastLists> getForecasts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addForecastsList(ForecastLists forecasts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateForecastsList(ForecastLists forecasts);

    @Delete
    void deleteForecastsList(ForecastLists forecasts);

    @Query("DELETE FROM forecasts")
    void deleteAllForecastsInfo();

}
