package com.tanapruk.reversegeocode;

import android.content.Context;
import android.location.Address;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * Created by florian on 01/11/2016.
 * (c) Polarsteps
 */

public class CountryGeocode implements Closeable {


    private static CountryGeocode mInstance = new CountryGeocode();

    private WeakReference<GeocodeList> mGeocodeList;
    private GeocodeConfiguration mConfiguration = GeocodeConfiguration.createDefault();
    private Context context;

    public static CountryGeocode withContext(Context context) {
        CountryGeocode mInstance = CountryGeocode.mInstance;
        mInstance.setContext(context);
        return mInstance;
    }

    public Callable<Address> lookupCountryName(final Double lat, final Double lng) {
        return new Callable<Address>() {
            @Override
            public Address call() throws Exception {
                GeocodeList geocodeListLazy = getGeocodeListLazy();

                if (geocodeListLazy == null) {
                    throw new IllegalArgumentException("Could not load geocode list");
                }

                GeocodeList.Geocode geocode = geocodeListLazy.getGeocode(lat, lng);
                if (geocode == null) {
                    throw new NoCountryFoundException(lat, lng);
                }
                Address retLoc = new Address(Locale.ENGLISH);
                retLoc.setLatitude(lat);
                retLoc.setLongitude(lng);
                retLoc.setCountryCode(geocode.id);
                retLoc.setCountryName(geocode.name);

                return retLoc;
            }
        };
    }

    @Nullable
    private GeocodeList getGeocodeListFromJSON(Context context) {
        String jsonString = getJSONFromAsset(context);
        Gson gson = new Gson();
        return gson.fromJson(jsonString, GeocodeList.class);
    }

    @Nullable
    private String getJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(mConfiguration.getJsonInput());
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Nullable
    private GeocodeList getGeocodeListLazy() {
        if (mGeocodeList != null && mGeocodeList.get() != null) {
            return mGeocodeList.get();
        }

        GeocodeList geocodeListFromJSON = getGeocodeListFromJSON(context);
        mGeocodeList = new WeakReference<GeocodeList>(geocodeListFromJSON);
        return geocodeListFromJSON;
    }

    public void setConfiguration(GeocodeConfiguration mConfiguration) {
        this.mConfiguration = mConfiguration;
        mGeocodeList.clear();
    }

    private void setContext(Context context) {
        this.context = context;
    }

    @Override

    public void close() throws IOException {
        mGeocodeList.clear();
    }

    private class NoCountryFoundException extends Exception {
        NoCountryFoundException(Double lat, Double lng) {
            super(String.format("Could not find any country locally that matches the coordinates: %s %s", lat + "", lng + ""));
        }
    }
}
