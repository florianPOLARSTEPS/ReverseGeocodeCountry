package com.tanapruk.reversegeocodedemo;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tanapruk.reversegeocode.CountryGeocode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnFind = (Button) findViewById(R.id.btn_find_country);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editLatitude = (EditText) findViewById(R.id.edit_latitude);
                EditText editLongitude = (EditText) findViewById(R.id.edit_longitude);
                TextView textCountryId = (TextView) findViewById(R.id.text_country_id);
                TextView textCountryName = (TextView) findViewById(R.id.text_country_name);
                Context context = getApplicationContext();

                Double latitude = Double.valueOf(editLatitude.getText().toString());
                Double longitude = Double.valueOf(editLongitude.getText().toString());


                try {
                    Address address = CountryGeocode.withContext(getApplicationContext()).lookupCountryName(latitude, longitude).call();
                    textCountryId.setText(address.getCountryCode());
                    textCountryName.setText(address.getCountryName());

                    Toast.makeText(context, "ID: " + address.getCountryCode() + "  " + " Name: " + address.getCountryName(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }


}
