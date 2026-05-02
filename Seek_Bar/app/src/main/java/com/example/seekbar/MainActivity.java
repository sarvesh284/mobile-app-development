package com.example.seekbar;



import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declare variables for our UI components
    SeekBar seekbar;
    TextView Text_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Enable modern Edge-to-Edge display
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 2. Standard boilerplate to handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3. Initialize the components by finding them in the XML
        Text_message = findViewById(R.id.t);
        seekbar = findViewById(R.id.seekbar);

        // 4. Set the Listener for the SeekBar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // This method triggers whenever the slider moves
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView size based on progress (adding 1 ensures it's never 0)
                Text_message.setTextSize(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // This is called when you first touch the slider
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // This is called when you let go of the slider
            }
        });
    }
}