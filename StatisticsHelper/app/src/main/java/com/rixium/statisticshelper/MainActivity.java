package com.rixium.statisticshelper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout;
    List<Double> dataOneList = new LinkedList<>();
    List<Double> dataTwoList = new LinkedList<>();

    Spinner dataSpinner1;
    Spinner dataSpinner2;

    TextView calculationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.linearLayout);

        dataSpinner1 = findViewById(R.id.dataSet1);
        dataSpinner2 = findViewById(R.id.dataSet2);

        calculationView = findViewById(R.id.calculation);

        if(getIntent().getDoubleArrayExtra("data1") != null) {
            double[] data1 = getIntent().getDoubleArrayExtra("data1");

            for(int i = 0; i < data1.length; i++) {
                dataOneList.add(data1[i]);
            }
        }

        if(getIntent().getDoubleArrayExtra("data2") != null) {
            double[] data2 = getIntent().getDoubleArrayExtra("data2");

            for(int i = 0; i < data2.length; i++) {
                dataTwoList.add(data2[i]);
            }
        }

        refreshData();
    }

    // Converts our lists to an array.
    private double[] CreateArrayFromList(List<Double> list) {
        double[] arr = new double[list.size()];

        for(int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }

        return arr;
    }

    // Switch to our calculation array.
    private void startCalculationActivity() {
        if(dataOneList.size() == 0 && dataTwoList.size() == 0) {
            Toast.makeText(this, "Cannot calculate empty data sets.", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, CalculationActivity.class);

        if(dataOneList.size() > 0) {
            double[] data1Array = CreateArrayFromList(dataOneList);
            intent.putExtra("data1", data1Array);
        }

        if(dataTwoList.size() > 0) {
            double[] data2Array = CreateArrayFromList(dataTwoList);
            intent.putExtra("data2", data2Array);
        }

        startActivity(intent);
    }

    public void onButtonPress(View view) {
        Button b = (Button) view;

        // Depending on the button id.
        switch(b.getId()) {
            case R.id.calculateButton:
                startCalculationActivity();
                break;
            case R.id.data1Button:
                inputData(dataOneList);
                break;
            case R.id.data2Button:
                inputData(dataTwoList);
                break;
        }
    }

    // Populates the spinners with the list values.
    private void refreshData() {
        ArrayAdapter<Double> dataAdapter1 = new ArrayAdapter<Double>(this, android.R.layout.simple_spinner_item, dataOneList);
        ArrayAdapter<Double> dataAdapter2 = new ArrayAdapter<Double>(this, android.R.layout.simple_spinner_item, dataTwoList);
        dataSpinner1.setAdapter(dataAdapter1);
        dataSpinner2.setAdapter(dataAdapter2);
    }

    // Popup and input data.
    private void inputData(final List<Double> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Data");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    double val = Double.parseDouble(input.getText().toString());
                    list.add(val);
                    refreshData();
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "NaN", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
