package com.example.watch.School;

import com.google.android.gms.maps.model.LatLng;

public class LocationInfo {

    public LatLng LastKnownLocation;

    public  LocationInfo ()
    { }

    public  LocationInfo (LatLng L) {
        this.LastKnownLocation = L ;
    }
}
