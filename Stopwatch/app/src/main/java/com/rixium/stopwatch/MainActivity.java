package com.rixium.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerView = findViewById(R.id.timerView);

        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    // Runs after onCreate.
    @Override
    public void onStart() {
        super.onStart();
        if(wasRunning) running = true;
    }

    // Runs before onStart, when reopening app.
    @Override
    public void onRestart() {
        super.onRestart();
    }

    // Runs when activity stops, or is hidden from user, no longer visible.
    @Override
    public void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    // Runs after onStop, before activity destroys.
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Runs when app loses focus
    @Override
    public void onPause() {
        super.onPause();
    }

    // Runs when app regains focus.
    @Override
    public void onResume() {
        super.onResume();
    }

    // This is to manage our button presses.
    public void onButtonPress(View view) {
        Button pressedButton = (Button) view;
        String buttonText = String.valueOf(pressedButton.getText());

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
        bundle.putInt("seconds", seconds);
        bundle.putBoolean("running", running);
        bundle.putBoolean("wasRunning", wasRunning);
    }

    private void runTimer() {
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timerView.setText(time);

                if(running) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });

    }
}