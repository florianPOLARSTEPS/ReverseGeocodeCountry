package com.tanapruk.reversegeocode;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
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

    Callable<String> lookupCountryName(final Double lat, final Double lng) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                GeocodeList geocodeListLazy = getGeocodeListLazy();

                if (geocodeListLazy == null) {
                    throw new IllegalArgumentException("Could not load geocode list");
                }

                String countryName = geocodeListLazy.getCountryName(lat, lng);
                if (!mConfiguration.isKeepOpen()) {
                    mGeocodeList.clear();
                }
                return countryName;
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
}
