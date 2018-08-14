package com.example.kkingsbe.elogbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@DynamoDBTable(tableName = "elogbook-mobilehub-1998989384-AccessCodes")

class AccessCodesDO {
    private String _accesscode;

    @DynamoDBHashKey(attributeName = "accesscode")
    @DynamoDBAttribute(attributeName = "accesscode")
    public String getAccesscode() {
        return _accesscode;
    }

    public void setAccesscode(final String _accesscode) {
        this._accesscode = _accesscode;
    }

}


public class LoginWait extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_wait);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String AccessCode = bundle.getString("Access Code");

        Thread authenticationThread = new Thread(new Runnable() {

            @Override
            public void run() {
                boolean correctCode = false;
                try  {
                    correctCode = authenticate(AccessCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (correctCode){
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            }
        });

        authenticationThread.start();

    }

    public boolean authenticate(final String AccessCode) {
        boolean Successful = false;
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

        AccessCodesDO accesscodesitem = dynamoDBMapper.load(
                AccessCodesDO.class,
                AccessCode);
        Log.d("Login: ", accesscodesitem.getAccesscode());
        if (accesscodesitem.getAccesscode() != null) {
            Successful = true;
        }
        return Successful;
    }
}
