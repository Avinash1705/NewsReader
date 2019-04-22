package avinash.com.hikersapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "raw";
    LocationManager locationManager;
    LocationListener locationListener;
    TextView longitude,latitude,accuracy,height,addressView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }

    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }


    //Updating Location
    public void LocationUpdateInfo(Location location) {
        Log.i("LocationUpdateInfo: ", location.toString());

        longitude=findViewById(R.id.LongitudeTextView);
        latitude=findViewById(R.id.LatitudeTextView);
        accuracy=findViewById(R.id.accuracyTextView);
        height=findViewById(R.id.heightTextView);
        addressView=findViewById(R.id.addressTextView);

        longitude.setText("Latitude"+location.getLatitude());
        latitude.setText("Logitude"+location.getLatitude());
        accuracy.setText("Accuracy"+location.getAccuracy());
        height.setText("Height"+location.getAltitude());

        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            String address="Location Not Found";
            List<Address> listaddress=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if((listaddress !=null) && (listaddress.size()>0)){
                Log.i(TAG, "LocationUpdateInfo: "+listaddress.get(0).toString());

                address="";
                if(listaddress.get(0).getSubThoroughfare()!=null){
                    address+=listaddress.get(0).getSubThoroughfare()+"";
                }
                if (listaddress.get(0).getThoroughfare()!=null){
                    address+=listaddress.get(0).getThoroughfare()+"\n";
                }

                if (listaddress.get(0).getLocality()!=null){
                    address+=listaddress.get(0).getLocality()+"\n";
                }

                if (listaddress.get(0).getPostalCode()!=null){
                    address+=listaddress.get(0).getPostalCode()+"\n";
                }

                if (listaddress.get(0).getCountryName()!=null) {
                    address += listaddress.get(0).getCountryName() + "\n";
                }
            }
           addressView.setText(address);
            Log.i(TAG, "LocationUpdateInfo: "+address);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LocationUpdateInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {

                startListening();
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location !=null){
                        LocationUpdateInfo(location);
                    }
                }
            }
        }
    }
