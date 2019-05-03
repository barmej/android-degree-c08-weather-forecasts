package com.barmej.weatherforecasts.data.entity;

import java.util.List;

public class WeatherForecasts {

    private List<Forecast> list = null;

    public List<Forecast> getList() {
        return list;
    }

    public void setList(List<Forecast> list) {
        this.list = list;
    }

}