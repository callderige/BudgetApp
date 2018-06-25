package com.example.cliff.budgetapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private final String USER_SETTINGS = "userSettings";
    private final String NET_INCOME = "netIncome";
    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.app_bar, "Settings"));

        mSharedPreferences = getBaseContext().getSharedPreferences(USER_SETTINGS, MODE_PRIVATE);

        EditText netIncomeEditText = findViewById(R.id.edit_net_income);
        netIncomeEditText.setText(String.format(Locale.US, "%.2f", mSharedPreferences.getFloat(NET_INCOME, 0)));

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
                EditText netIncomeEditText = findViewById(R.id.edit_net_income);
                if (checkNetIncome(netIncomeEditText.getText().toString())) {
                    String netIncomeTextFormat = String.format(Locale.US, "%.2f", Float.parseFloat(netIncomeEditText.getText().toString()));
                    StringBuilder toastText = new StringBuilder("Recorded net income as $" + netIncomeTextFormat);
                    float netIncome = Float.parseFloat(netIncomeEditText.getText().toString());

                    Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_LONG).show();
                    Editor editor = mSharedPreferences.edit();
                    editor.putFloat(NET_INCOME, netIncome);
                    editor.apply();
                    finishActivity();
                } else {
                    Snackbar.make(view, "Please enter a valid number", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

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

    private boolean checkNetIncome(String netIncome) {
        boolean hasPassed = false;
        byte passedChecks = 0;
        int decimalPlace = netIncome.indexOf('.');

        if (netIncome.length() - decimalPlace <= 3 || decimalPlace == -1) {
            passedChecks += 1;
            Log.d("testing_passed", netIncome.length() + " : " + decimalPlace +"");
        } else {
            passedChecks = 0;
            Log.d("testing_failed", netIncome.length() + " : " + decimalPlace +"");
        }

        try {
            Float.parseFloat(netIncome);
            passedChecks += 1;
        } catch (NumberFormatException numberFormatException) {
            passedChecks = 0;
            Log.d("FormatException", numberFormatException.toString());
        }

        if (passedChecks == 2) {
            hasPassed = true;
        }

        return hasPassed;
    }

}
