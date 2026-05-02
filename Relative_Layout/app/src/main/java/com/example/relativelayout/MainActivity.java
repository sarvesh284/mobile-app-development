package com.example.relativelayout;





import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This links the Java code to the XML file we just edited
        setContentView(R.layout.activity_main);
    }
}