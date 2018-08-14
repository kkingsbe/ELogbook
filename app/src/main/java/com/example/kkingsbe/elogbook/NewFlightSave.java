package com.example.kkingsbe.elogbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class NewFlightSave extends Activity {
    public String AccessCode;
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight_save);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        AccessCode = bundle.getString("Access Code");
        String Date = bundle.getString("Date");
        String Airframe = bundle.getString("Airframe");
        String DepartingAirport = bundle.getString("Departing Airport");
        String ArrivingAirport = bundle.getString("Arriving Airport");
        String FlightTime = bundle.getString("Flight Time");
        String TailNumber = bundle.getString("Tail Number");

        updateTable(AccessCode, Date, Airframe, DepartingAirport, ArrivingAirport, FlightTime, TailNumber);
    }

    private void updateTable(final String accessCode, String date, String airframe, String departingAirport, String arrivingAirport, String flightTime, String tailNumber) {
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

        final FlightsDO flight = new FlightsDO();

        flight.setAccesscode(accessCode);
        flight.setAirframe(airframe);
        flight.setArrivalairport(arrivingAirport);
        flight.setDate(date);
        flight.setDepartureairport(departingAirport);
        flight.setFlighttime(flightTime);
        flight.setTailnumber(tailNumber);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(flight);
                // Item saved

                setResult(Activity.RESULT_OK);
                finish();
            }
        }).start();
    }
}
