package com.rixium.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    Timer timer = new Timer();
    TextView timerView;

    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    // Runs when activity is created.
    // Uses for static setup, creating views, allows retrieval of bundle from previous state.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout.
        setContentView(R.layout.activity_main);
        // Get the TextView.
        timerView = findViewById(R.id.timerView);


        // If the bundle isn't null.
        if(savedInstanceState != null) {
            // We can populate our variables with the saved bundle variables.
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        // Finally, run the timer.
        runTimer();
    }

    // Runs before onStart, when reopening app.
    // Once the activity has stopped, this calls just before the app is started again.
    @Override
    public void onRestart() {
        super.onRestart();
    }


    // This is called just after onCreate, and is follow by onResume if activity is focused.
    // onStop is called after this if activity loses focus.
    @Override
    public void onStart() {
        super.onStart();
    }

    // This is called after onPause(), depending on if activity has stopped, and not visible.
    @Override
    public void onStop() {
        super.onStop();
        Log.d("Stopped", "Activity has stopped.");
    }

    // Runs after onStop, before activity destroys.
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Runs when app loses focus.
    // Activity doesn't resume until this finishes.
    // Keep this computationally minimal.
    // onResume calls next, if activity regains focus,
    // otherwise, onDestroy calls, if activity is destroyed.
    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // Runs when app regains or gains focus.
    // Called after onStart.
    @Override
    public void onResume() {
        super.onResume();
        running = wasRunning;
    }

    // This is to manage our button presses.
    public void onButtonPress(View view) {
        // Get the button related to the press.
        Button pressedButton = (Button) view;
        // Get the button text.
        String buttonText = String.valueOf(pressedButton.getText());

        // Depending on the button text, do something related to its functionality.
        if(buttonText == getText(R.string.startText)) {
            running = true;
        } else if (buttonText == getText(R.string.stopText)) {
            running = false;
        } else if (buttonText == getText(R.string.resetText)) {
            running = false;
            seconds = 0;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        // Saving our bundle variables.
        bundle.putInt("seconds", seconds);
        bundle.putBoolean("running", running);
        bundle.putBoolean("wasRunning", wasRunning);
    }

    private void runTimer() {
        // Create our handler, for the timer.
        final Handler handler = new Handler();

        // A new Run method for our handler.
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Get the hours for display.
                int hours = seconds / 3600;
                // Minutes for display.
                int minutes = (seconds % 3600) / 60;
                // Get the seconds for display.
                int secs = seconds % 60;

                // Create a string using the above variables.
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                // Setting the text of our textview.
                timerView.setText(time);

                // If the app is running, we can increment seconds.
                if(running) {
                    seconds++;
                }

                // Finally, ready the handler for another timer iteration.
                // Every 1000 ms or second.
                handler.postDelayed(this, 1000);
            }
        });

    }
}