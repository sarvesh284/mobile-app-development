package com.example.implicitintent;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declare the UI elements
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Standard EdgeToEdge padding logic
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Initialize the views
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        // 2. Set the Click Listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from EditText
                String url = editText.getText().toString();

                // Validation: Ensure the URL starts with http:// or https://
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }

                // 3. Create the Implicit Intent
                // ACTION_VIEW tells Android we want to view something
                // Uri.parse(url) converts the string into a web address format
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the activity (Android will show a list of browsers)
                startActivity(intent);
            }
        });
    }
}
