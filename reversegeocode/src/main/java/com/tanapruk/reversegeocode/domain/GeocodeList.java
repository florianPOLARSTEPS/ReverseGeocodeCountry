package com.tanapruk.reversegeocode.domain;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.annotations.SerializedName;
import com.tanapruk.reversegeocode.Polygon.Point;
import com.tanapruk.reversegeocode.Polygon.Polygon;
import java.util.List;

/**
 * Created by Tanapruk on 8/7/2015.
 */
public class GeocodeList {


    @SerializedName("geocodelist")
    public List<Geocode> geocodeList;

    @Nullable
    public Geocode getGeocode(double latitude, double longitude) {
        Point currentPoint = new Point((float) latitude, (float) longitude);
        for (int i = 0; i < geocodeList.size(); i++) {
            Geocode geocode = geocodeList.get(i);
            List<PolygonSet> polygonSetList = geocode.getPolygonSetList();
            for (int j = 0; j < polygonSetList.size(); j++) {
                PolygonSet polygonSet = polygonSetList.get(j);
                List<LocationPoint> polygonList = polygonSet.getPolygonList();
                Polygon.Builder polygonBuilder = new Polygon.Builder();
                for (int k = 0; k < polygonList.size(); k++) {
                    LocationPoint locationPoint = polygonList.get(k);
                    polygonBuilder.addVertex(new Point((float) locationPoint.getLatitude(), (float) locationPoint.getLongitude()));
                }
                Polygon polygon = polygonBuilder.build();
                if (polygon.contains(currentPoint)) {
                    return geocode;
                }
            }
        }
        return null;
    }














}
