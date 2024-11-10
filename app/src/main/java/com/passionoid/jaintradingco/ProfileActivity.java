package com.passionoid.jaintradingco;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends Activity {

    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username", DISPLAY_EMAIL_KEY = "email";
    String mDisplayName, mDisplayEmail;
    TextView userName, emailID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupDisplayName();
        userName = findViewById(R.id.username);
        emailID = findViewById(R.id.mEmail);

        userName.setText(mDisplayName);
        emailID.setText(mDisplayEmail);


    }


    private void setupDisplayName(){
        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);
        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);
        mDisplayEmail = prefs.getString(RegisterActivity.DISPLAY_EMAIL_KEY, null);
        if(mDisplayName == null) mDisplayName = "Anonymous";
        if(mDisplayEmail == null) mDisplayEmail = "anonymous@anonymail.com";
    }
}
