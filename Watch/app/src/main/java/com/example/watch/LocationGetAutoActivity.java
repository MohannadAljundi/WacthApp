package com.example.watch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.lang.reflect.Array;
import java.util.Arrays;


public class LocationGetAutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_get_auto);

        final TextView Result = findViewById(R.id.textView) ;

        String ApiKey = getResources().getString(R.string.google_app_id);
        Places.initialize(getApplicationContext(), ApiKey);

        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteSupportFragment  = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        assert autocompleteSupportFragment != null;
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG,Place.Field.ADDRESS,Place.Field.NAME));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                Result.setText(place.getName());
                LatLng latLng = place.getLatLng();

                if(latLng == null){
                    Log.e("Place : " , "is Null");
                    return;
                }

                Intent resultIntent = new Intent();

                resultIntent.putExtra("lat",latLng.latitude);
                resultIntent.putExtra("lng",latLng.longitude);
                resultIntent.putExtra("address",place.getAddress());

                setResult(RESULT_OK,resultIntent);
                finish();

            }

            @Override
            public void onError(@NonNull Status status) {
                Result.setText(status.toString());
                Log.e("Plaxes " ,"Error : " + status.toString());
            }
        });

    }
}
