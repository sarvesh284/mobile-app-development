package com.example.inputcontrols;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ToggleButton togglebutton;
    TextView textview;
    RatingBar ratingBar;
    ProgressBar progressBarCircle;
    Button btnSubmit, btnProgress;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
        togglebutton = findViewById(R.id.toggleButton);
        textview = findViewById(R.id.textView);
        ratingBar = findViewById(R.id.ratingBar);
        progressBarCircle = findViewById(R.id.progress);
        btnSubmit = findViewById(R.id.button);
        btnProgress = findViewById(R.id.button1);
        spinner = findViewById(R.id.sp1);

        // 1. Toast - Welcome Message
        Toast.makeText(getApplicationContext(), "Welcome to Android Program", Toast.LENGTH_SHORT).show();

        // 2. Spinner Implementation
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this, "Selected: " + text, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // 3. RatingBar & Alert Dialog (on Submit Button click)
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Rating Toast
                String rating = String.valueOf(ratingBar.getRating());
                Toast.makeText(MainActivity.this, "Rating: " + rating, Toast.LENGTH_LONG).show();

                // Build Alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to close this application?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) { finish(); }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) { dialog.cancel(); }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Alert");
                alert.show();
            }
        });

        // 4. Progress Bar (Circular)
        btnSubmit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                progressBarCircle.setVisibility(View.VISIBLE);
                return true;
            }
        });

        // 5. Progress Dialog (Horizontal)
        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setCancelable(true);
                progressDialog.setMessage("File downloading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgress(0);
                progressDialog.setMax(100);
                progressDialog.show();
            }
        });
    }

    // Toggle Button Handler (linked via android:onClick in XML)
    public void onToggleClick(View view) {
        if (togglebutton.isChecked()) {
            textview.setText("Toggle is ON");
        } else {
            textview.setText("Toggle is OFF");
        }
    }
}