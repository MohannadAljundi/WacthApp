package com.example.watch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;


public class LocationGetAutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_get_auto);

        String ApiKey = getResources().getString(R.string.google_app_id);

        Places.initialize(getApplicationContext(), ApiKey);
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteSupportFragment  = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.**);

    }
}
