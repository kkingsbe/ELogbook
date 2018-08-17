package com.example.kkingsbe.elogbook;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "elogbook-mobilehub-1998989384-Flights")

class FlightsDO {
    private String _accesscode;
    private String _date;
    private String _airframe;
    private String _arrivalairport;
    private String _departureairport;
    private String _flighttime;
    private String _tailnumber;

    @DynamoDBHashKey(attributeName = "accesscode")
    @DynamoDBAttribute(attributeName = "accesscode")
    public String getAccesscode() {
        return _accesscode;
    }

    public void setAccesscode(final String _accesscode) {
        this._accesscode = _accesscode;
    }
    @DynamoDBRangeKey(attributeName = "date")
    @DynamoDBAttribute(attributeName = "date")
    public String getDate() {
        return _date;
    }

    public void setDate(final String _date) {
        this._date = _date;
    }
    @DynamoDBAttribute(attributeName = "airframe")
    public String getAirframe() {
        return _airframe;
    }

    public void setAirframe(final String _airframe) {
        this._airframe = _airframe;
    }
    @DynamoDBAttribute(attributeName = "arrivalairport")
    public String getArrivalairport() {
        return _arrivalairport;
    }

    public void setArrivalairport(final String _arrivalairport) {
        this._arrivalairport = _arrivalairport;
    }
    @DynamoDBAttribute(attributeName = "departureairport")
    public String getDepartureairport() {
        return _departureairport;
    }

    public void setDepartureairport(final String _departureairport) {
        this._departureairport = _departureairport;
    }
    @DynamoDBAttribute(attributeName = "flighttime")
    public String getFlighttime() {
        return _flighttime;
    }

    public void setFlighttime(final String _flighttime) {
        this._flighttime = _flighttime;
    }
    @DynamoDBAttribute(attributeName = "tailnumber")
    public String getTailnumber() {
        return _tailnumber;
    }

    public void setTailnumber(final String _tailnumber) {
        this._tailnumber = _tailnumber;
    }

}

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private FlightAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String AccessCode;

    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        AccessCode = bundle.getString("Access Code");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_profile:
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_flights:
                        Toast.makeText(MainActivity.this, "Flights", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_explore:
                        Toast.makeText(MainActivity.this, "Explore", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        setTitle("My Flights");

        populateCards(AccessCode);
    }

    private void populateCards(final String accessCode) {
        final boolean[] anyFlights = {false};
        final ArrayList<ExampleFlight> flightList = new ArrayList<>();

        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

        Thread flightsThread = new Thread(new Runnable() {

            @Override
            public void run() {
                List<FlightsDO> flightsList = getFlights(accessCode); //Gets all flights with a specific accessCode

                if(flightsList.size() > 0){
                    for(int x = 0; x < flightsList.size(); x++){
                        flightList.add(new ExampleFlight(
                                flightsList.get(x).getFlighttime(),
                                flightsList.get(x).getAirframe(),
                                flightsList.get(x).getTailnumber(),
                                flightsList.get(x).getDate(),
                                flightsList.get(x).getDepartureairport() + " => " + flightsList.get(x).getArrivalairport()));
                    }
                    anyFlights[0] = true;
                }

            }
        });

        flightsThread.start();


        //flightList.add(new ExampleFlight("1.1 Hours", "PA-28", "N80FT", "8/5/2018", "KGAI => KGAI"));
        //flightList.add(new ExampleFlight("1.5 Hours", "PA-28", "N80FT", "8/9/2018", "KGAI => KGAI"));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(anyFlights[0]){
            mRecyclerView = findViewById(R.id.recyclerview);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new FlightAdapter(flightList);

            mRecyclerView.setLayoutManager((mLayoutManager));
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new FlightAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Log.d("Position: ",flightList.get(position).toString());
                }

                @Override
                public void onDeleteClick(int position) {
                    removeItem(position);
                }

                @Override
                public void onEditClick(int position) {
                    editItem(position);
                }
            });
        }

    }

    private void editItem(final int position) {
        final FlightsDO[] flight = new FlightsDO[1];

        new Thread(new Runnable() {
            @Override
            public void run() {
                flight[0] = getFlight(position, AccessCode);
            }
        }).start();

        Intent intent = new Intent(MainActivity.this, EditFlightActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Access Code", AccessCode);

        try {
            Thread.sleep(100); //Dont know how this works, but it keeps it from crashing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bundle.putString("Date", flight[0].getDate());
        bundle.putString("Airframe", flight[0].getAirframe());
        bundle.putString("Departing Airport", flight[0].getDepartureairport());
        bundle.putString("Arriving Airport", flight[0].getArrivalairport());
        bundle.putString("Flight Time", flight[0].getFlighttime());
        bundle.putString("Tail Number", flight[0].getTailnumber());
        intent.putExtras(bundle);
        startActivityForResult(intent,0,bundle);
    }

    public FlightsDO getFlight(int position, String AccessCode){
        return getFlights(AccessCode).get(position);
    }

    public void removeItem(final int position) {
        final Thread removeFlight = new Thread(new Runnable() {
            @Override
            public void run() {
                List<FlightsDO> Flights = getFlights(AccessCode);
                FlightsDO flightItem = new FlightsDO();

                flightItem.setTailnumber(Flights.get(position).getTailnumber());
                flightItem.setFlighttime(Flights.get(position).getFlighttime());
                flightItem.setDepartureairport(Flights.get(position).getDepartureairport());
                flightItem.setDate(Flights.get(position).getDate());
                flightItem.setArrivalairport(Flights.get(position).getArrivalairport());
                flightItem.setAirframe(Flights.get(position).getAirframe());
                flightItem.setAccesscode(Flights.get(position).getAccesscode());

                dynamoDBMapper.delete(flightItem);
                // Item deleted
                Looper.prepare();
                Toast.makeText(MainActivity.this,
                        "Flight Deleted", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Delete Flight");
        builder.setMessage("Are you sure that you want to delete this flight?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeFlight.start();
                        refreshFlights();
                    }
                });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void refreshFlights(){
        recreate();
    }

    private List<FlightsDO> getFlights(String accessCode){
        FlightsDO search = new FlightsDO();
        search.setAccesscode(accessCode);

        DynamoDBQueryExpression<FlightsDO> queryExpression = new DynamoDBQueryExpression<FlightsDO>()
                .withHashKeyValues(search).withScanIndexForward(true);

        List<FlightsDO> flightsList = dynamoDBMapper.query(FlightsDO.class, queryExpression);
        return flightsList;
    }

    public void edit(View v) {
        Log.d("Edit button clicked: ",Integer.toString(v.getId()));
    }

    public void delete(View v) {
        Log.d("Delete button clicked: ",Integer.toString(v.getId()));
    }

    public void newFlight(View v) {
        Log.d("New Flight Clicked: ",Integer.toString(v.getId()));
        Intent intent = new Intent(MainActivity.this, NewFlightActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Access Code", AccessCode);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            refreshFlights();
        }
    }

}
