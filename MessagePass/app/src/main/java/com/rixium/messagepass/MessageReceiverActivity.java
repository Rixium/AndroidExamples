package com.rixium.messagepass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageReceiverActivity extends AppCompatActivity {

    // Constant so we know the value of our extra_message intent key.
    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set our layout.
        setContentView(R.layout.activity_message_receiver);

        // Get a reference to the textview.
        TextView messageBox = findViewById(R.id.messageBox);
        // Set the text equal to the passed message from the intent.
        messageBox.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
    }

}