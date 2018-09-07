package com.example.kkingsbe.elogbook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class EditFlightActivity extends Activity {
    DynamoDBMapper dynamoDBMapper;
    EditText DateEditText;
    EditText AirframeEditText;
    EditText DepartingAirportEditText;
    EditText ArrivingAirportEditText;
    EditText FlightTimeEditText;
    EditText TailNumberEditText;
    String AccessCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        AccessCode = bundle.getString("Access Code");
        String Date = bundle.getString("Date");
        String DepartingAirport = bundle.getString("Departing Airport");
        String ArrivingAirport = bundle.getString("Arriving Airport");
        String FlightTime = bundle.getString("Flight Time");
        String TailNumber = bundle.getString("Tail Number");
        String AirFrame = bundle.getString("Airframe");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight);

        DateEditText = findViewById(R.id.dateText);
        AirframeEditText = findViewById(R.id.airframeText);
        DepartingAirportEditText = findViewById(R.id.departureText);
        ArrivingAirportEditText = findViewById(R.id.arrivalText);
        FlightTimeEditText = findViewById(R.id.flighttimeText);
        TailNumberEditText = findViewById(R.id.tailnumberText);

        DateEditText.setText(Date);
        AirframeEditText.setText(AirFrame);
        DepartingAirportEditText.setText(DepartingAirport);
        ArrivingAirportEditText.setText(ArrivingAirport);
        FlightTimeEditText.setText(FlightTime);
        TailNumberEditText.setText(TailNumber);
    }

    public void updateFlight(View v) {
        String Date = DateEditText.getText().toString();
        String AirFrame = AirframeEditText.getText().toString();
        String departure = DepartingAirportEditText.getText().toString();
        String arrival = ArrivingAirportEditText.getText().toString();
        String flighttime = FlightTimeEditText.getText().toString();
        String tailnumber = TailNumberEditText.getText().toString();

        // Check to see if any of the fields are blank
        if (
                        Date.length() < 1 ||
                        AirFrame.length() < 1 ||
                        departure.length() < 1 ||
                        arrival.length() < 1 ||
                        flighttime.length() < 1 ||
                        tailnumber.length() < 1
                ) {
            Toast.makeText(EditFlightActivity.this, "Make sure all fields are filled out", Toast.LENGTH_LONG).show();
        } else {
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

            flight.setAccesscode(AccessCode);
            flight.setAirframe(AirframeEditText.getText().toString());
            flight.setArrivalairport(ArrivingAirportEditText.getText().toString());
            flight.setDate(DateEditText.getText().toString());
            flight.setDepartureairport(DepartingAirportEditText.getText().toString());
            flight.setFlighttime(FlightTimeEditText.getText().toString());
            flight.setTailnumber(TailNumberEditText.getText().toString());

            new Thread(new Runnable() {
                @Override
                public void run() {

                    dynamoDBMapper.save(flight);

                    // Item updated
                }
            }).start();

            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    public void back(View v){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
