package com.example.contextmenu;


import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RelativeLayout r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Initialize Views
        textView = findViewById(R.id.text);
        r = findViewById(R.id.rel);

        // 2. Register the TextView for the Context Menu
        // Without this line, the menu will NOT appear on long-press
        registerForContextMenu(textView);

        // Handle System Bar Padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rel), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 3. Create the Context Menu items
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Choose a color");

        // Arguments: GroupID, ItemID, Order, Title
        menu.add(0, v.getId(), 0, "Yellow");
        menu.add(0, v.getId(), 0, "Gray");
        menu.add(0, v.getId(), 0, "Cyan");
    }

    // 4. Handle Menu Item Clicks
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String title = item.getTitle().toString();

        if (title.equals("Yellow")) {
            r.setBackgroundColor(Color.YELLOW);
        } else if (title.equals("Gray")) {
            r.setBackgroundColor(Color.GRAY);
        } else if (title.equals("Cyan")) {
            r.setBackgroundColor(Color.CYAN);
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }
}