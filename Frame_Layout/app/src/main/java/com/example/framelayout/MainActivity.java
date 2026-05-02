package com.example.framelayout;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This links the Java class to activity_main.xml
        setContentView(R.layout.activity_main);
    }
}
