package com.example.filehandling;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    Button b1, b2;
    TextView tv;
    EditText ed1;
    String data;
    // Private internal file name
    private String file = "mydata.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Initialize UI components
        b1 = findViewById(R.id.button);     // Save Button
        b2 = findViewById(R.id.button2);    // Load Button
        ed1 = findViewById(R.id.editText);  // Input Field
        tv = findViewById(R.id.textview2);  // Output Display

        // Handle System Bar Padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. SAVE BUTTON logic
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = ed1.getText().toString();
                try {
                    // Open file for writing (MODE_APPEND adds text to existing content)
                    FileOutputStream fOut = openFileOutput(file, MODE_APPEND);
                    fOut.write(data.getBytes());
                    fOut.close();

                    ed1.setText(""); // Clear input after saving
                    Toast.makeText(getBaseContext(), "File Saved Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 3. LOAD BUTTON logic
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Open file for reading
                    FileInputStream fin = openFileInput(file);
                    int c;
                    StringBuilder temp = new StringBuilder();

                    // Read one character at a time until end of file (-1)
                    while ((c = fin.read()) != -1) {
                        temp.append((char) c);
                    }

                    // Display content in TextView
                    tv.setText(temp.toString());
                    fin.close();

                    Toast.makeText(getBaseContext(), "File Loaded Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    tv.setText("No file found or error reading file.");
                    e.printStackTrace();
                }
            }
        });
    }
}