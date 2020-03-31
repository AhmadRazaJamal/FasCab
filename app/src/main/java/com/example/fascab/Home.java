package com.example.fascab;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.navigation.NavigationView;
import com.google.android.gms.maps.model.MarkerOptions;

public class Home extends AppCompatActivity
        implements OnMapReadyCallback {

    private static final String TAG = Home.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private GoogleMap mMap;
    /** A google map variable to implement and manipulate the map */

    boolean flag = false ;
    /** A flag to track the page that was switched to view profile [ e.g. access from ontrip or onroute ] */

    Polyline currentpolyLine ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_settings_menu);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /** Sets the top toolbar on home page */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        /** Passing each menu ID as a set of Ids because each
        * menu should be considered as top level destinations. */
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_preferences, R.id.nav_payment,
                R.id.nav_trips, R.id.nav_help)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /** Get the SupportMapFragment and register for the callback
        * when the map is ready for use. **/
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /** Apply custom spinner text by overwriting default spinner item text class **/
        Spinner spinner = findViewById(R.id.favoriteSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.favSpinner, R.layout.spinner_item);
        spinner.setAdapter(adapter);

        addListener();

    }


    /**
     * Method for listeners of various types for different types of input from user 
     *
     */
    public void addListener() {

        final CheckBox blackList = findViewById(R.id.blackList_check) ;

        blackList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    TextView blackList = findViewById(R.id.blackListtatus_txt) ;
                    blackList.setText("Yes");
                }

            }
        });

        final Spinner favoriteSpinner = findViewById(R.id.favoriteSpinner) ;

        favoriteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView blackList = findViewById(R.id.favValue_txt) ;
                blackList.setText(favoriteSpinner.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        final EditText rating = findViewById(R.id.ratingEdittxt);

        rating.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                TextView blackList = findViewById(R.id.ratingValue_txt) ;
                blackList.setText(rating.getText());
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready for use.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        try {
            /** Customise the styling of the base map using a JSON object defined
            * in a raw resource file. **/
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json2));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        }
        catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        /** Position the map's camera near Sydney, Australia as default */

        LatLng latLng = new LatLng(49.8880, -119.4960);
        googleMap.addMarker(new MarkerOptions().position(latLng)
                .title("Marker in Kelowna"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }

    /**
     * Calls the taxi when the book now button is pressed
     * The layout for onroute taxi is displayed with hardcoded display of onroute and ontrip for now
     */
    public void bookNow(View view){
        findViewById(R.id.homesweethome).setVisibility(View.GONE);
        findViewById(R.id.onroute).setVisibility(View.VISIBLE);
        /** Make the onroute page display and the home page layout disappear*/

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.onroute_map);
        mapFragment.getMapAsync(this);
        /** Initialize map for onroute page*/

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
        /** A delay function for displaying ontrip page after 5 seconds if user isn't viewing driver profile*/

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
        /** A delay function for displaying arrived page after 10 seconds if user isn't viewing driver profile*/

        mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.ontrip_map);
        mapFragment.getMapAsync(this);
        /** Initialize map for ontrip page*/

        mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.arrived_map);
        mapFragment.getMapAsync(this);
        /** Initialize map for arrived page*/


        checkRunnable = new Runnable() {
            @Override
            public void run() {
            if(findViewById(R.id.driverprofile).getVisibility() == View.GONE ) {
                /** If the user is on the view driver profile page then don't load the on trip and arrived page */
                mHandler.postDelayed(mRunnable, 5 * 1000);
                mHandler.postDelayed(nRunnable, 10 * 1000);
            }
        }
        };
        /** A delay function for running the delayed function for arrived and ontrip page if user is on driver profile
         * page, waiting 5 seconds before executing
         * So if driver profile page isn't opened for 5 seconds, this will execute
         * */

        mHandler.postDelayed(checkRunnable,5*1000);

        /** A function for running delayed functions for running arrived and ontrip page */


    }

    /**
     * Method to display the drivers profile
     *
     */
    public void viewProfile(View view) {

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.driverProfile_map);
        mapFragment.getMapAsync(this);

        /** Initialize map for driverProfile page*/


        if(findViewById(R.id.onroute).getVisibility() == View.VISIBLE){
            findViewById(R.id.onroute).setVisibility(View.GONE);
            findViewById(R.id.driverprofile).setVisibility(View.VISIBLE);
            flag = false ;
        }/** If driver profile is opened from onroute page **/

        else{
            findViewById(R.id.ontrip).setVisibility(View.GONE);
            findViewById(R.id.driverprofile).setVisibility(View.VISIBLE);
            flag = true ;
        }/** If driver profile is opened from ontrip page **/
    }

    /**
     * Method to go back to map from drivers profile, that is th onroute or ontrip page
     *
     */
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
            /** Delayed functions to run and open the ontrip and arrived page */

        } /** If driver profile is opened from onroute page **/

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

        } /** If driver profile is opened from ontrip page **/
    }


    /**
     * Method to go to the leave review page so user can leave a review
     *
     */
    public void leaveReview(View view){

        findViewById(R.id.arrived).setVisibility(View.GONE);
        findViewById(R.id.reviewPage).setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.review_map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Method to go to the submitted review page so user can view the review
     *
     */
    public void submitReview(View view){

        findViewById(R.id.reviewPage).setVisibility(View.GONE);
        findViewById(R.id.reviewRecieved).setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.reviewRecieved_map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Method to create the home menu item in the toolbar
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_toolbar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Method to take user back to home and start a new activity
     *
     */
    public void backHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    /**
     * Method to take user back to home when user clicks on the home toolbar icon
     *
     */
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


