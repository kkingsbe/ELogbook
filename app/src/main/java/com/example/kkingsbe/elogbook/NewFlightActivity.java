package com.example.kkingsbe.elogbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

        Intent intent = new Intent(NewFlightActivity.this, NewFlightSave.class);
        Bundle bundle = new Bundle();
        bundle.putString("Access Code", AccessCode);
        bundle.putString("Date", dateText.getText().toString());
        bundle.putString("Airframe", airframeText.getText().toString());
        bundle.putString("Departing Airport", departureText.getText().toString());
        bundle.putString("Arriving Airport", arrivalText.getText().toString());
        bundle.putString("Flight Time", flighttimeText.getText().toString());
        bundle.putString("Tail Number", tailnumberText.getText().toString());
        intent.putExtras(bundle);
        startActivityForResult(intent, 0, bundle);
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
