package com.example.cliff.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button saveButton = findViewById(R.id.btn_settings_save);
        saveButton.setOnClickListener(this);
        Button cancelButton = findViewById(R.id.btn_settings_cancel);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_settings_cancel: {
                finishActivity();
                break;
            }
            case R.id.btn_settings_save: {
                finishActivity();
                break;
            }
            default: {
                finishActivity();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    public void finishActivity() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

}
