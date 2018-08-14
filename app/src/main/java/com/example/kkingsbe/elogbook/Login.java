package com.example.kkingsbe.elogbook;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText AccessCode;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AccessCode = findViewById(R.id.AccessCode);
    }

    public void signIn(View v){
        code = AccessCode.getText().toString();
        Intent intent = new Intent(Login.this, LoginWait.class);
        Bundle bundle = new Bundle();
        bundle.putString("Access Code", code);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0, bundle);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // Check which request we're responding to
        if (requestCode == 0) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                Bundle newBundle = new Bundle();
                newBundle.putString("Access Code", code);
                intent.putExtras(newBundle);
                startActivity(intent);
            }
        }
    }
}
