package com.example.cliff.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class EntryActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner mBudgetTypeSpinner;
    private Spinner mExpenseTypeSpinner;
    private ViewFlipper mViewFlipper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        //Default title is set to new bill
        setTitle(getString(R.string.app_bar, "New Bill"));

        mViewFlipper = findViewById(R.id.flipper_form);

        mBudgetTypeSpinner = findViewById(R.id.spinner_budget_type);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.budget_type_array, android.R.layout.simple_spinner_dropdown_item);
        mBudgetTypeSpinner.setAdapter(arrayAdapter);
        mBudgetTypeSpinner.setOnItemSelectedListener(new onSpinnerItemSelected());

        mExpenseTypeSpinner = findViewById(R.id.spinner_expense_type);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.expense_type_array, android.R.layout.simple_spinner_dropdown_item);
        mExpenseTypeSpinner.setAdapter(arrayAdapter1);
        mExpenseTypeSpinner.setOnItemSelectedListener(new onSpinnerItemSelected());

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

    public class onSpinnerItemSelected implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            switch (adapterView.getId()) {
                case R.id.spinner_budget_type: {
                    if (position == 0) {
                        mViewFlipper.setDisplayedChild(0);
                        setTitle(getString(R.string.app_bar, "New Bill"));
                    } else {
                        mViewFlipper.setDisplayedChild(1);
                        setTitle(getString(R.string.app_bar, "New Expense"));
                    }
                    break;
                }

                default: {

                    break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private void finishActivity() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
