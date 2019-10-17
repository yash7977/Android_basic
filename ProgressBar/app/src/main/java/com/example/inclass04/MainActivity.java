package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView SeekBarResult;
    SeekBar seekBar;
    TextView MaximumResult;
    TextView MinimumResult;
    TextView AverageResult;
    Button GenerateNumber;
    Integer seekbarValue=0;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ProgressBar");
        SeekBarResult = findViewById(R.id.SeekBarResult);
        seekBar = findViewById(R.id.seekBar);
        MaximumResult = findViewById(R.id.MaximumResult);
        MinimumResult = findViewById(R.id.MinimumResult);
        AverageResult = findViewById(R.id.AverageResult);
        GenerateNumber = findViewById(R.id.GenerateNumber);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);





        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbarValue = Integer.valueOf(i);
                SeekBarResult.setText(String.valueOf(i));
                System.out.println(seekbarValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        GenerateNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seekBar.getProgress() == 0) {
                    Toast.makeText(MainActivity.this, "Please select Some Value!", Toast.LENGTH_SHORT).show();
                    ;

                } else {
                    new generateAsyncTask().execute(seekBar.getProgress());

                }
            }
        });


    }

    class generateAsyncTask extends AsyncTask<Integer,Integer,ArrayList<Double>>{

        @Override
        protected ArrayList<Double> doInBackground(Integer... integers) {
           ArrayList<Double> arrayNumbers =  HeavyWork.getArrayNumbers(integers[0]);
           return arrayNumbers;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {
            MinimumResult.setText(String.valueOf(Collections.min(doubles)));
            MaximumResult.setText(String.valueOf(Collections.max(doubles)));
            Double sum = 0.0;
            for (int i = 0; i < doubles.size(); i++){
                sum += doubles.get(i);}

            AverageResult.setText(String.valueOf(sum / doubles.size()));
            System.out.println(sum+"+++"+sum/doubles.size());
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
