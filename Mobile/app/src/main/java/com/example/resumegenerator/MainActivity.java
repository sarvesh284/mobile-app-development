package com.example.resumegenerator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText etFullName, etJobTitle, etEmail, etPhone, etSummary, etExperience, etEducation, etSkills, etProjects;
    private Button btnGenerate;
    
    // Change this to your local IP address if running on a real device, e.g., "http://192.168.1.100:5002/generate"
    // Use 10.0.2.2 for Android Emulator connecting to local host
    private static final String BACKEND_URL = "http://10.0.2.2:5002/generate";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        btnGenerate.setOnClickListener(v -> generateResume());
    }

    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etJobTitle = findViewById(R.id.etJobTitle);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etSummary = findViewById(R.id.etSummary);
        etExperience = findViewById(R.id.etExperience);
        etEducation = findViewById(R.id.etEducation);
        etSkills = findViewById(R.id.etSkills);
        etProjects = findViewById(R.id.etProjects);
        btnGenerate = findViewById(R.id.btnGenerate);
    }

    private void generateResume() {
        btnGenerate.setEnabled(false);
        btnGenerate.setText("Generating...");
        
        String fullName = etFullName.getText().toString().trim();
        if (fullName.isEmpty()) {
            Toast.makeText(this, "Full Name is required", Toast.LENGTH_SHORT).show();
            btnGenerate.setEnabled(true);
            btnGenerate.setText("Generate Resume");
            return;
        }

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("full_name", fullName);
            jsonParam.put("job_title", etJobTitle.getText().toString().trim());
            jsonParam.put("email", etEmail.getText().toString().trim());
            jsonParam.put("phone", etPhone.getText().toString().trim());
            jsonParam.put("summary", etSummary.getText().toString().trim());
            jsonParam.put("experience", etExperience.getText().toString().trim());
            jsonParam.put("education", etEducation.getText().toString().trim());
            jsonParam.put("skills", etSkills.getText().toString().trim());
            jsonParam.put("projects", etProjects.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        executorService.execute(() -> {
            boolean success = false;
            File pdfFile = null;
            try {
                URL url = new URL(BACKEND_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/pdf");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    String fileName = "resume_" + fullName.replace(" ", "_") + ".pdf";
                    pdfFile = new File(downloadsDir, fileName);
                    
                    FileOutputStream fos = new FileOutputStream(pdfFile);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    fos.close();
                    is.close();
                    success = true;
                } else {
                    Log.e("ResumeApp", "Server returned: " + responseCode);
                }
                conn.disconnect();
            } catch (Exception e) {
                Log.e("ResumeApp", "Error generating resume", e);
            }

            final boolean finalSuccess = success;
            final File finalFile = pdfFile;
            
            mainHandler.post(() -> {
                btnGenerate.setEnabled(true);
                btnGenerate.setText("Generate Resume");
                if (finalSuccess && finalFile != null) {
                    Toast.makeText(MainActivity.this, "Resume saved to Downloads", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to generate resume", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}