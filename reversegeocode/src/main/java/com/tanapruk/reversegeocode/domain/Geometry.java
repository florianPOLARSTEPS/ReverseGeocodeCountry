package com.tanapruk.reversegeocode.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by florian on 25/01/2017.
 * (c) Polarsteps
 */

public class Geometry {

    @SerializedName("type")
    public String type;

    @SerializedName("coordinates")
    public Coordinates coordinates;

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}