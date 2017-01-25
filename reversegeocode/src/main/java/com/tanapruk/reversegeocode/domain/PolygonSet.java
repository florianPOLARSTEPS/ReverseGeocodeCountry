package com.tanapruk.reversegeocode.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by florian on 25/01/2017.
 * (c) Polarsteps
 */

public class PolygonSet {
    @SerializedName("polygon")
    public List<LocationPoint> polygonList;

    public List<LocationPoint> getPolygonList() {
        return polygonList;
    }

    private void setPolygonList(List<LocationPoint> polygonList) {
        this.polygonList = polygonList;
    }
}