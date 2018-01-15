package com.example.mohamed.earthquakesmapss;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Data.EquakesHTTPClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String TAG = MapsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new GetGeometry().execute();


    }

    public double lat = new GetGeometry().getLat();
    public double lon = new GetGeometry().getLon();
    public double mags = new GetGeometry().getMags();




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println(lat);
        System.out.println(lon);
        System.out.println(mags);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("power:  " + mags));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    public class GetGeometry extends AsyncTask<Void, Void, Void> {
        public double lat;
        public double lon;
        public double mags;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getMags() {
            return mags;
        }

        public void setMags(double mags) {
            this.mags = mags;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MapsActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            EquakesHTTPClient sh = new EquakesHTTPClient();
            // Making a request to url and getting response
            String url = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    // get json file
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    //get the table object features
                    JSONArray feature = jsonObj.getJSONArray("features");
                    for (int i = 0; i < feature.length(); i++) {
                        //get mag
                        JSONObject block = feature.getJSONObject(i);
                        JSONObject properties = block.getJSONObject("properties");
                        mags = properties.getDouble("mag");
                        setMags(mags);


                        // Getting JSON Array node
                        JSONObject geo = block.getJSONObject("geometry");
                        JSONArray coordinates = geo.getJSONArray("coordinates");

                        // looping through All geo
                        for (int j = 0; j < coordinates.length(); j++) {
                            JSONObject c = coordinates.getJSONObject(j);
                            double lon = c.getDouble("longitude");
                            setLon(lon);
                            j++;
                            JSONObject c1 = coordinates.getJSONObject(j);
                            double lat = c1.getDouble("latitude");
                            setLat(lat);
                            j++;
                            JSONObject c3 = coordinates.getJSONObject(j);
                            double depth = c3.getDouble("depth");


                        }

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            System.out.println(lon);
            System.out.println(lat);
            System.out.println(mags);
            return null;
        }


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */


    }
}