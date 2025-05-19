package com.ondejka.breathexercise;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String logCode = "AboutActivity_OnCr_";
        Log.i(logCode + "001:", "OK");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView textAppName = findViewById(R.id.appNameTextView);
        TextView textAuthor = findViewById(R.id.authorTextView);
        TextView textVersion = findViewById(R.id.versionTextView);

        textAppName.setText(getString(R.string.app_name));
        textAuthor.setText("Author: R.O.");
        textVersion.setText("Version: " + BuildConfig.VERSION_NAME);
    }
}