package com.tanapruk.reversegeocode.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by florian on 25/01/2017.
 * (c) Polarsteps
 */

public class Geocode {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("geometry")
    public Geometry geometry;

    private String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private Geometry getGeometry() {
        return geometry;
    }

    private String getType() {
        return geometry.getType();
    }

    private Coordinates getCoordinates() {
        return geometry.getCoordinates();
    }

    private boolean isMultiPolygon() {
        return (geometry.getType().equals("MultiPolygon"));
    }

    public List<PolygonSet> getPolygonSetList() {
        return geometry.getCoordinates().getPolygonsetList();
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}