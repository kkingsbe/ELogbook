package com.example.kkingsbe.elogbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class NewFlightActivity extends AppCompatActivity {
    public String AccessCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight);
        setTitle("New Flight");

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        AccessCode = bundle.getString("Access Code");
    }

    public void saveNewFlight(View v) {
        EditText dateText = findViewById(R.id.dateText);
        EditText airframeText = findViewById(R.id.airframeText);
        EditText tailnumberText = findViewById(R.id.tailnumberText);
        EditText flighttimeText = findViewById(R.id.flighttimeText);
        EditText departureText = findViewById(R.id.departureText);
        EditText arrivalText = findViewById(R.id.arrivalText);

        String date = dateText.getText().toString();
        String airframe = airframeText.getText().toString();
        String departure = departureText.getText().toString();
        String arrival = arrivalText.getText().toString();
        String flighttime = flighttimeText.getText().toString();
        String tailnumber = tailnumberText.getText().toString();

        if (
                date.length() < 1 ||
                airframe.length() < 1 ||
                departure.length() < 1 ||
                arrival.length() < 1 ||
                flighttime.length() < 1 ||
                tailnumber.length() < 1
            ){
            Toast.makeText(NewFlightActivity.this, "Make sure all fields are filled out", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(NewFlightActivity.this, NewFlightSave.class);
            Bundle bundle = new Bundle();
            bundle.putString("Access Code", AccessCode);
            bundle.putString("Date", date);
            bundle.putString("Airframe", airframe);
            bundle.putString("Departing Airport", departure);
            bundle.putString("Arriving Airport", arrival);
            bundle.putString("Flight Time", flighttime);
            bundle.putString("Tail Number", tailnumber);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0, bundle);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 0){
            if (resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK);
                finish();
            }
            else{
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        }
    }
}
