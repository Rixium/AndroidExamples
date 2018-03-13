package com.rixium.statisticshelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CalculationActivity extends AppCompatActivity {

    // Holds our data sets.
    double[] dataSetOne;
    double[] dataSetTwo;

    // Boolean to see if we have passed an array via intent.
    boolean hasDs1 = false;
    boolean hasDs2 = false;

    // Textviews for our data.
    TextView dataSet1TextView;
    TextView dataSet2TextView;
    TextView duelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        // Get the arrays from the intent.
        dataSetOne = getIntent().getDoubleArrayExtra("data1");
        dataSetTwo = getIntent().getDoubleArrayExtra("data2");


        // If the arrays aren't null, we can see the flag, and their text views.
        if(getIntent().getDoubleArrayExtra("data1") != null) {
            dataSet1TextView = findViewById(R.id.ds1tv);
            hasDs1 = true;
        }

        if( getIntent().getDoubleArrayExtra("data2") != null) {
            dataSet2TextView = findViewById(R.id.ds2tv);
            hasDs2 = true;

        }

        // For calculations involving both arrays.
        duelTextView = findViewById(R.id.dtv);

        // Calculate everything from this method.
        CalculateAll();
    }

    private void CalculateAll() {
        // Strings for the text views.
        String s1 = "";
        String s2 = "";

        // For calculating values involving both data sets.
        if(hasDs1 && hasDs2) {
            if(dataSetOne.length == dataSetTwo.length) {
                double val = CalculatePearson(dataSetOne, dataSetTwo);
                duelTextView.setText("Pearsons: " + val);
            }
        }

        // Calculating independent values.
        if(hasDs1) {
            double mean = CalculateMean(dataSetOne);
            double median = CalculateMedian(dataSetOne);
            double range = CalculateRange(dataSetOne);
            String mode = CalculateMode(dataSetOne);
            double sd = CalculateStandardDeviation(dataSetOne);

            s1 += "Mean: " + mean;
            s1 += "\nMedian: " + median;
            s1 += "\nRange: " + range;
            s1 += "\nMode: " + mode;
            s1 += "\nStandard Deviation: " + sd;

            dataSet1TextView.setText(s1);
        }

        if(hasDs2) {
            double mean2 = CalculateMean(dataSetTwo);
            double median2 = CalculateMedian(dataSetTwo);
            double range2 = CalculateRange(dataSetTwo);
            String mode2 = CalculateMode(dataSetTwo);
            double sd2 = CalculateStandardDeviation(dataSetTwo);

            s2 += "Mean: " + mean2;
            s2 += "\nMedian: " + median2;
            s2 += "\nRange: " + range2;
            s2 += "\nMode: " + mode2;
            s2 += "\nStandard Deviation: " + sd2;

            dataSet2TextView.setText(s2);
        }
    }

    /* Sorts the array, and finds the highest occuring value within the array.
     * Returns N/A if the count is 1, or the mostoccurringcount occurs more than once.
     */

    private String CalculateMode(double[] data) {
        double[] sorted = data.clone();
        Arrays.sort(sorted);

        double mostOccurring = sorted[0];
        int mostOccurringCount = 1;
        int mostOccurringFrequency = 1;
        int count = 1;

        for(int i = 1; i < sorted.length; i++) {
            if(sorted[i] == sorted[i - 1]) {
                count++;
                if(count > mostOccurringCount) {
                    mostOccurring = sorted[i - 1];
                    mostOccurringCount = count;
                    mostOccurringFrequency = 1;
                } else if (count == mostOccurringCount) {
                    mostOccurringFrequency++;
                }
            } else {
                count = 1;
            }
        }

        if(mostOccurringCount == 1 || mostOccurringFrequency > 1) return "N/A";
        return mostOccurring + "";
    }

    /* First sorts the array, and then finds the median based on whether the
     * Array contains an even or odd number of values.
     */

    private double CalculateMedian(double[] data) {
        double[] sorted = data.clone();
        Arrays.sort(sorted);

        if (sorted.length % 2 != 0) {
            return sorted[sorted.length / 2];
        }

        return (sorted[(sorted.length / 2) - 1] + sorted[sorted.length / 2]) / 2;
    }

    // Sorts the array, then calculates the last minus the first.
    private double CalculateRange(double[] data) {
        double[] sorted = data.clone();
        Arrays.sort(sorted);

        return sorted[sorted.length - 1] - sorted[0];
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    // Adds every value together, and divides the sum by the length.
    private double CalculateMean(double[] data) {
        double sum = 0;
        for(int i = 0; i < data.length; i++) {
            sum += data[i];
        }

        return (sum / data.length);
    }

    // Calculates Pearsons' R using the formula.
    private double CalculatePearson(double[] data1, double[] data2){
        double mean1 = CalculateMean(data1);
        double mean2 = CalculateMean(data2);
        double sum = 0;
        double sq1 = 0;
        double sq2 = 0;

        for(int i = 0; i < data1.length; i++) {
            double sum1 = data1[i] - mean1;
            double sum2 = data2[i] - mean2;
            sum += ((data1[i] - mean1) * (data2[i] - mean2));
            sq1 += Math.pow(data1[i] - mean1, 2);
            sq2 += Math.pow(data2[i] - mean2, 2);
        }

        return sum / Math.sqrt(sq1 * sq2);
    }

    // Back to main screen if back button pressed
    // We pass back the data sets, for spinner re-population.

    public void backButtonPress(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        if(hasDs1) {
            intent.putExtra("data1", dataSetOne);
        }

        if(hasDs2) {
            intent.putExtra("data2", dataSetTwo);
        }

        startActivity(intent);
    }

    // Calculates the SD using the formula.

    private double CalculateStandardDeviation(double[] data) {
        double sum = 0;
        double mean = CalculateMean(data);
        for (int i = 0; i < data.length; i++)
        {
            sum += Math.pow(mean - data[i], 2);
        }

        return Math.sqrt(sum / data.length);
    }

}
