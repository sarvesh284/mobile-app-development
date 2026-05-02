package com.example.optionmenu;




import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar; // ADD THIS IMPORT

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    RelativeLayout currentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Initialize the layout
        currentLayout = findViewById(R.id.a);

        // 2. SET UP THE TOOLBAR AS THE ACTION BAR
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Standard padding logic
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.a), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.android) {
            currentLayout.setBackgroundColor(Color.RED);
            return true;
        } else if (id == R.id.java) {
            currentLayout.setBackgroundColor(Color.GREEN);
            return true;
        } else if (id == R.id.kotlin) {
            currentLayout.setBackgroundColor(Color.BLUE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}