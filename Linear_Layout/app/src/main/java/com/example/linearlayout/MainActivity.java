package com.example.linearlayout;



import androidx.appcompat.app.AppCompatActivity; // Modern import
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line links the Java code to the XML layout created in Step 2
        setContentView(R.layout.activity_main);
    }
}