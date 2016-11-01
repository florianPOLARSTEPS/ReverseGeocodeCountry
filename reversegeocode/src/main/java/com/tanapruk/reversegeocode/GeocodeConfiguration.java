package com.tanapruk.reversegeocode;

/**
 * Created by florian on 01/11/2016.
 * (c) Polarsteps
 */

public class GeocodeConfiguration {

    private String jsonInput;

    private boolean keepOpen;

    public String getJsonInput() {
        return jsonInput;
    }

    public boolean isKeepOpen() {
        return keepOpen;
    }

    private GeocodeConfiguration(String jsonInput, boolean keepOpen) {
        this.jsonInput = jsonInput;
        this.keepOpen = keepOpen;
    }

    public static GeocodeConfiguration createDefault() {
        return new GeocodeConfiguration("ReverseGeocodeCountryWithName.json", false);
    }


    public class GeocodeConfigurationBuilder {
        private String jsonInput;
        private boolean keepOpen;

        public GeocodeConfiguration build() {
            return new GeocodeConfiguration(jsonInput, keepOpen);
        }

        public GeocodeConfigurationBuilder setJsonInput(String jsonInput) {
            this.jsonInput = jsonInput;
            return this;
        }

        public GeocodeConfigurationBuilder setKeepOpen(boolean keepOpen) {
            this.keepOpen = keepOpen;
            return this;
        }
    }

}
