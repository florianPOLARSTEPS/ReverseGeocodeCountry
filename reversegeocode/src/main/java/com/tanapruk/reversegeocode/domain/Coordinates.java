package com.tanapruk.reversegeocode.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by florian on 25/01/2017.
 * (c) Polarsteps
 */

public class Coordinates {

    @SerializedName("polygonset")
    public List<PolygonSet> polygonSetList;

    public List<PolygonSet> getPolygonsetList() {
        return polygonSetList;
    }

    public void setPolygonsetList(List<PolygonSet> polygonSetList) {
        this.polygonSetList = polygonSetList;
    }
}