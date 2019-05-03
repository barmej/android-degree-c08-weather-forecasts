package com.barmej.weatherforecasts.data;

public interface OnDataDeliveryListener<T> {

    void onDataDelivery(T dataObject);

}