package com.example.kkingsbe.elogbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RefreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String AccessCode = bundle.getString("Access Code");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Intent intent = new Intent(RefreshActivity.this, MainActivity.class);
        Bundle newBundle = new Bundle();
        newBundle.putString("Access Code", AccessCode);
        intent.putExtras(newBundle);
        startActivity(intent);
    }
}
