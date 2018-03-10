package com.rixium.beeradvisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Calls as soon as the activity is created, ** CONSTRUCTOR style **
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Triggered when the Find Beer button is pressed.
    // Parameter requests view, related to the GUI component the action was performed on.
    public void onClickFindBeer(View view) {

        // Reference to layout spinner.
        Spinner beerSpinner = findViewById(R.id.color);

        // Reference to layout textView.
        TextView textView = findViewById(R.id.brands);

        // Create a beer expert for use.
        BeerExpert beerExpert = new BeerExpert();

        // Get the current selected colour of the beerspinner.
        String selectedColour = String.valueOf(beerSpinner.getSelectedItem());

        // Get a new list of brands from the beer expert, using the selected colour.
        List<String> selectedBrands = beerExpert.getBrands(selectedColour);

        // For building our string list.
        StringBuilder stringBuilder = new StringBuilder();

        // Iterate through each brand in our list.
        for(String s : selectedBrands) {
            // Append our string builder with the brand, and a new line character, for display.
            stringBuilder.append(s + "\n");
        }

        // Finally, set the text of the textview equal to our built string.
        textView.setText(stringBuilder);
    }

}
