package com.example.cliff.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.HashMap;

public class EntryActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner mExpenseTypeSpinner;
    private ViewFlipper mViewFlipper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //Default title is set to new bill
        setTitle(getString(R.string.app_bar, "New Bill"));

        mViewFlipper = findViewById(R.id.flipper_form);

        Spinner BudgetTypeSpinner = findViewById(R.id.spinner_budget_type);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.budget_type_array, android.R.layout.simple_spinner_dropdown_item);
        BudgetTypeSpinner.setAdapter(arrayAdapter);
        BudgetTypeSpinner.setOnItemSelectedListener(new onSpinnerItemSelected());

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
                HashMap<String, String> returnedInsertResult = insertIntoDatabase();
                if (returnedInsertResult.get("status").equals("success")) {
                    Toast.makeText(getApplicationContext(),
                            returnedInsertResult.get("success_information"), Toast.LENGTH_LONG).show();
                    finishActivity();
                } else if (returnedInsertResult.get("status").equals("failed")){
                    Toast.makeText(getApplicationContext(),
                            returnedInsertResult.get("failed_information"), Toast.LENGTH_LONG).show();
                }
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
            if (adapterView.getId() == R.id.spinner_budget_type) {
                if (position == 0) {
                    mViewFlipper.setDisplayedChild(0);
                    setTitle(getString(R.string.app_bar, "New Bill"));
                } else {
                    mViewFlipper.setDisplayedChild(1);
                    setTitle(getString(R.string.app_bar, "New Expense"));
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

    /**
     * Returns a HashMap with values explaining the status of the database insert
     * @return The hashmap containing the results of the database insertion.
     */
    private HashMap<String, String> insertIntoDatabase() {
        HashMap<String, String> resultsToReturn = new HashMap<>();

        switch (mViewFlipper.getDisplayedChild()) {
            //When mViewFlipper equals 0, the bill form is recorded
            case 0: {
                byte passedChecks = 0;

                EditText editText = findViewById(R.id.edit_bill_name);
                String billName = editText.getText().toString();
                if (billName.trim().length() > 0) {
                    passedChecks += 1;
                } else {
                    resultsToReturn.put("status", "failed");
                    resultsToReturn.put("failed_information", "Invalid bill name.");
                    break;
                }

                editText = findViewById(R.id.edit_bill_cost);
                String billCostString = editText.getText().toString();
                int decimalPlace = billCostString.indexOf('.');

                if (billCostString.length() - decimalPlace <= 3 || decimalPlace == -1) {
                    passedChecks += 1;
                } else {
                    resultsToReturn.put("status", "failed");
                    resultsToReturn.put("failed_information", "Invalid bill cost.");
                    break;
                }

                double billCost;
                try {
                    billCost = Float.parseFloat(editText.getText().toString());
                    passedChecks += 1;
                } catch (NumberFormatException numberFormatException) {
                    Log.d("NumberFormatException", numberFormatException.toString());
                    resultsToReturn.put("status", "failed");
                    resultsToReturn.put("failed_information", "Invalid bill cost entry");
                    break;
                }

                DatePicker datePicker = findViewById(R.id.date_picker_bill_due);
                String date = datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth();

                if (passedChecks == 3) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
                    if (databaseHelper.createBill(billName, billCost, 0, date, 0) != -1) {
                        resultsToReturn.put("status", "success");
                        resultsToReturn.put("success_information", "New bill created.");
                    } else {
                        resultsToReturn.put("status", "failed");
                        resultsToReturn.put("failed_information", "Error inserting into database");
                    }
                }

                break;
            }
            //When mViewFlipper equals 1, the expense form is recorded
            case 1: {
                byte passedChecks = 0;

                EditText editText = findViewById(R.id.edit_expense_name);
                String expenseName = editText.getText().toString();
                if (expenseName.trim().length() > 0) {
                    passedChecks += 1;
                } else {
                    resultsToReturn.put("status", "failed");
                    resultsToReturn.put("failed_information", "Invalid Expense name.");
                    break;
                }

                editText = findViewById(R.id.edit_expense_limit);
                String expenseLimitString = editText.getText().toString();
                int decimalPlace = expenseLimitString.indexOf('.');

                if (expenseLimitString.length() - decimalPlace <= 3 || decimalPlace == -1) {
                    passedChecks += 1;
                } else {
                    resultsToReturn.put("status", "failed");
                    resultsToReturn.put("failed_information", "Invalid expense limit");
                    break;
                }

                double expenseLimit;
                try {
                    expenseLimit = Float.parseFloat(editText.getText().toString());
                    passedChecks += 1;
                } catch (NumberFormatException numberFormatException) {
                    Log.d("NumberFormatException", numberFormatException.toString());
                    resultsToReturn.put("status", "failed");
                    resultsToReturn.put("failed_information", "Invalid expense limit entry");
                    break;
                }

                String expenseType = mExpenseTypeSpinner.getSelectedItem().toString();

                if (passedChecks == 3) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
                    if (databaseHelper.createExpense(expenseName, 0, expenseLimit, expenseType) != -1) {
                        resultsToReturn.put("status", "success");
                        resultsToReturn.put("success_information", "New expense created.");
                    } else {
                        resultsToReturn.put("status", "failed");
                        resultsToReturn.put("failed_information", "Error inserting into database");
                    }
                }

                break;
            }
            default: {
                resultsToReturn.put("status", "failed");
                resultsToReturn.put("failed_information", "Could not find correct view");
                break;
            }
        }

        return resultsToReturn;
    }
}
