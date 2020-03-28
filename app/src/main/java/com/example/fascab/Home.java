package com.example.fascab;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A styled map using JSON styles from a raw resource.
 */
public class Home extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final String TAG = Home.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private GoogleMap mMap;

    boolean flag = false ;
    // A flag to track the page that was switched to view profile [ e.g. access from ontrip or onroute ]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_settings_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_preferences, R.id.nav_payment,
                R.id.nav_trips, R.id.nav_help)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Get the SupportMapFragment and register for the callback
        // when the map is ready for use.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Apply custom spinner text by overwriting default spinner item text class
        Spinner spinner = findViewById(R.id.favoriteSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.favSpinner, R.layout.spinner_item);
        spinner.setAdapter(adapter);

    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready for use.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        // Position the map's camera near Sydney, Australia.

        LatLng latLng = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(latLng)
                .title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                latLng
                , 13));

    }

    public void bookNow(View view){
        findViewById(R.id.homesweethome).setVisibility(View.GONE);
        findViewById(R.id.onroute).setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        final Runnable mRunnable;
        final Runnable nRunnable;
        Runnable checkRunnable;
        final Handler mHandler=new Handler();

        mRunnable=new Runnable() {

            @Override
            public void run() {
                if(findViewById(R.id.driverprofile).getVisibility() == View.GONE) {
                    findViewById(R.id.onroute).setVisibility(View.GONE);
                    findViewById(R.id.ontrip).setVisibility(View.VISIBLE);
                }
            }
        };

        nRunnable = new Runnable() {
            @Override
            public void run() {
                // If the user is on the view driver profile page then don't load the on arrived page
                if(findViewById(R.id.driverprofile).getVisibility() == View.GONE) {
                    findViewById(R.id.ontrip).setVisibility(View.GONE);
                    findViewById(R.id.arrived).setVisibility(View.VISIBLE);
                }
            }
        };

        mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);

        mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.arrived_map);
        mapFragment.getMapAsync(this);


        checkRunnable = new Runnable() {
            @Override
            public void run() {
            if(findViewById(R.id.driverprofile).getVisibility() == View.GONE ) {
                // If the user is on the view driver profile page then don't load the on trip and arrived page
                mHandler.postDelayed(mRunnable, 5 * 1000);
                mHandler.postDelayed(nRunnable, 10 * 1000);
            }
        }
        };

        mHandler.postDelayed(checkRunnable,5*1000);

    }

    public void viewProfile(View view) {

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.driverProfile_map);
        mapFragment.getMapAsync(this);

        if(findViewById(R.id.onroute).getVisibility() == View.VISIBLE){
            findViewById(R.id.onroute).setVisibility(View.GONE);
            findViewById(R.id.driverprofile).setVisibility(View.VISIBLE);
            flag = false ;
        }
        else{
            findViewById(R.id.ontrip).setVisibility(View.GONE);
            findViewById(R.id.driverprofile).setVisibility(View.VISIBLE);
            flag = true ;
        }
    }

    public void backToMap(View view){

        if(flag == false){
            findViewById(R.id.onroute).setVisibility(View.VISIBLE);
            findViewById(R.id.driverprofile).setVisibility(View.GONE);

            Runnable mRunnable;
            Runnable nRunnable;
            Handler mHandler=new Handler();

            mRunnable = new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.onroute).setVisibility(View.GONE);
                    findViewById(R.id.ontrip).setVisibility(View.VISIBLE);
                }
            };

            nRunnable = new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.ontrip).setVisibility(View.GONE);
                    findViewById(R.id.arrived).setVisibility(View.VISIBLE);
                }
            };

            mHandler.postDelayed(mRunnable, 7 * 1000);
            mHandler.postDelayed(nRunnable, 13 * 1000);

        }
        else if(flag == true){
            findViewById(R.id.ontrip).setVisibility(View.VISIBLE);
            findViewById(R.id.driverprofile).setVisibility(View.GONE);

            Runnable nRunnable;
            Handler mHandler=new Handler();

            nRunnable = new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.ontrip).setVisibility(View.GONE);
                    findViewById(R.id.arrived).setVisibility(View.VISIBLE);
                }
            };

            mHandler.postDelayed(nRunnable, 7 * 1000);

        }
    }

    public void leaveReview(View view){

        findViewById(R.id.arrived).setVisibility(View.GONE);
        findViewById(R.id.reviewPage).setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.review_map);
        mapFragment.getMapAsync(this);

    }

    public void submitReview(View view){

        findViewById(R.id.reviewPage).setVisibility(View.GONE);
        findViewById(R.id.reviewRecieved).setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.reviewRecieved_map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void backHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}


