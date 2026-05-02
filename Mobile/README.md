# Mobile Resume Builder

This is a mobile Android application for generating ATS-friendly PDF resumes. It uses a Python backend for PDF generation.

## Requirements

1. Android Studio to build and run the mobile app.
2. Python 3.x to run the backend server.

## Setting up the Backend

1. Navigate to the `backend` folder:
   ```bash
   cd Mobile/backend
   ```
2. (Optional) Create a virtual environment:
   ```bash
   python -m venv venv
   source venv/bin/activate  # On Windows, use `venv\Scripts\activate`
   ```
3. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```
4. Run the Flask server:
   ```bash
   python app.py
   ```
   The backend will run on `http://0.0.0.0:5002`.

## Setting up the Mobile App

1. Open the `Mobile` folder in Android Studio.
2. The `MainActivity.java` file is configured to connect to `http://10.0.2.2:5002/generate` which is the localhost of your machine from the Android Emulator.
3. If you run the app on a physical device, update `BACKEND_URL` in `MainActivity.java` to point to your machine's local IP address (e.g., `http://192.168.1.X:5002/generate`).
4. Build and run the app. Fill in your details and click **Generate Resume**.
5. The generated PDF will be downloaded to your device's `Downloads` folder.
