package com.barmej.weatherforecasts.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "forecasts")
public class ForecastLists {

    @PrimaryKey
    private int id = 0;
    @ColumnInfo(name = "hours_forecasts")
    private List<Forecast> hoursForecasts = null;
    @ColumnInfo(name = "days_forecasts")
    private List<List<Forecast>> daysForecasts = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Forecast> getHoursForecasts() {
        return hoursForecasts;
    }

    public void setHoursForecasts(List<Forecast> hoursForecasts) {
        this.hoursForecasts = hoursForecasts;
    }

    public List<List<Forecast>> getDaysForecasts() {
        return daysForecasts;
    }

    public void setDaysForecasts(List<List<Forecast>> daysForecasts) {
        this.daysForecasts = daysForecasts;
    }

}