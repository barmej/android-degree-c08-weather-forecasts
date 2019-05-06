package com.barmej.weatherforecasts.data.database.converters;

import androidx.room.TypeConverter;

import com.barmej.weatherforecasts.data.entity.Forecast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * This class used by Room to convert List of hours forecasts from & to json string
 */
public class HoursForecastsConverter {

    @TypeConverter
    public static List<Forecast> fromString(String value) {
        // Convert json string to List using Gson library
        Type listType = new TypeToken<List<Forecast>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Forecast> list) {
        // Convert List to json string using Gson library
        return new Gson().toJson(list);
    }

}
