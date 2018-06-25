package com.example.cliff.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EntryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        //Default title is set to new bill
        setTitle(getString(R.string.app_bar, "New Bill"));

        Button saveButton = findViewById(R.id.btn_entry_save);
        saveButton.setOnClickListener(this);
        Button cancelButton = findViewById(R.id.btn_entry_cancel);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_entry_save: {
                finishActivity();
                break;
            }
            case R.id.btn_entry_cancel: {
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

    private void finishActivity() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
