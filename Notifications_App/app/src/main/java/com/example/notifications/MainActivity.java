package com.example.notifications;





import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String channel_id = "my_channel_01"; // Consistent ID
        Button b = findViewById(R.id.button);

        // 1. REQUEST PERMISSION for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // 2. CREATE CHANNEL (Required for API 26+)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel nc = new NotificationChannel(channel_id, "Standard Notifications", NotificationManager.IMPORTANCE_HIGH);
                    nm.createNotificationChannel(nc);
                }

                // 3. BUILD NOTIFICATION
                // Use "ic_launcher" as a fallback icon if "logo" is missing
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, channel_id)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Notification Alert")
                        .setContentText("This is my first push notification!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true); // Removes notification when clicked

                // 4. ISSUE NOTIFICATION
                nm.notify((int) System.currentTimeMillis(), mBuilder.build());
            }
        });
    }
}