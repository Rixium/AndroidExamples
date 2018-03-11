package com.rixium.messagepass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Initial activity starts here.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout.
        setContentView(R.layout.activity_main);
    }

    // For when the button is pressed.
    public void onSendMessage(View view) {
        // Create an intent so we can switch activity, to apps that
        // allow for ACTION_SEND functionality.
        Intent intent = new Intent(Intent.ACTION_SEND);
        // Find the textbox on the current layout.
        EditText textBox = findViewById(R.id.message);
        // Get the value of the text written in the textBox above.
        String messageString = String.valueOf(textBox.getText());
        // Set the intent type for plain text.
        intent.setType("text/plain");
        // Put the message above as an extra string in the intent.
        // Adding the message as extra text.
        intent.putExtra(Intent.EXTRA_TEXT, messageString);

        // A chooser to force app selection to get intent.
        Intent chosenIntent = Intent.createChooser(intent, getString(R.string.send));

        // Finally, start the activity.
        startActivity(chosenIntent);
    }

}
