package com.barmej.weatherforecasts.data.database.converters;

import androidx.room.TypeConverter;

import com.barmej.weatherforecasts.data.entity.Forecast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HoursForecastsConverter {

    @TypeConverter
    public static List<Forecast> fromString(String value) {
        Type listType = new TypeToken<List<Forecast>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Forecast> list) {
        return new Gson().toJson(list);
    }

}
