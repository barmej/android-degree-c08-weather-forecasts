package com.barmej.weatherforecasts.data.database.converters;

import androidx.room.TypeConverter;

import com.barmej.weatherforecasts.data.entity.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class WeatherListConverter {

    @TypeConverter
    public static List<Weather> fromString(String value) {
        Type listType = new TypeToken<List<Weather>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Weather> list) {
        return new Gson().toJson(list);
    }

}
