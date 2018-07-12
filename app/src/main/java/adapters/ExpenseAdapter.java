package adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cliff.budgetapp.DatabaseHelper;
import com.example.cliff.budgetapp.R;

import java.util.List;
import java.util.Locale;

import sqlite.model.Expense;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    private List<Expense> mExpense;
    private Context mContext;

    private static class ViewHolder {
        TextView expenseNameTextView;
        TextView expenseStatusTextView;
        Button expenseSpendButton;
        ProgressBar expenseProgressBar;
    }

    public ExpenseAdapter(Context context, List<Expense> expense) {
        super(context, R.layout.listview_bill_item, expense);
        this.mExpense = expense;
        this.mContext = context;
    }

    @Override
    @NonNull
    @SuppressWarnings("ConstantConditions")
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final Expense expense = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_expense_item, parent, false);

            viewHolder.expenseNameTextView = convertView.findViewById(R.id.tv_expense_name);
            viewHolder.expenseNameTextView.setText(expense.getName());

            viewHolder.expenseStatusTextView = convertView.findViewById(R.id.tv_expense_status);
            String spent = String.format(Locale.US, "%.2f", expense.getSpent());
            String limit = String.format(Locale.US, "%.2f", expense.getLimit());
            viewHolder.expenseStatusTextView.setText(mContext.getString(R.string.text_list_view_expense_status, spent, limit));

            double progressBarNumber = Math.floor( (expense.getSpent() / expense.getLimit()) * 100);
            viewHolder.expenseProgressBar = convertView.findViewById(R.id.progress_bar_expense);
            viewHolder.expenseProgressBar.setProgress((int) progressBarNumber);
        } else {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_expense_item, parent, false);

            viewHolder.expenseNameTextView = convertView.findViewById(R.id.tv_expense_name);
            viewHolder.expenseNameTextView.setText(expense.getName());

            String spent = String.format(Locale.US, "%.2f", expense.getSpent());
            String limit = String.format(Locale.US, "%.2f", expense.getLimit());
            viewHolder.expenseStatusTextView = convertView.findViewById(R.id.tv_expense_status);
            viewHolder.expenseStatusTextView.setText(mContext.getString(R.string.text_list_view_expense_status, spent, limit));

            double progressBarNumber = Math.floor( (expense.getSpent() / expense.getLimit()) * 100);
            viewHolder.expenseProgressBar = convertView.findViewById(R.id.progress_bar_expense);
            viewHolder.expenseProgressBar.setProgress((int) progressBarNumber);
        }

        if (viewHolder.expenseProgressBar.getProgress() >= 100) {
            viewHolder.expenseProgressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }

        viewHolder.expenseSpendButton = convertView.findViewById(R.id.btn_expense_spend);
        viewHolder.expenseSpendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlertDialog(expense);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return mExpense.size();
    }

    @Override
    public Expense getItem(int position) {
        return mExpense.get(position);
    }

    private void openAlertDialog(final Expense expense) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View dialogLayout = layoutInflater.inflate(R.layout.alert_dialog_expense_spend, null);

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setView(dialogLayout);
        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

        final EditText editExpenseSpendEditText = dialogLayout.findViewById(R.id.et_alert_dialog_expense_spend);
        editExpenseSpendEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean success = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (editExpenseSpendEditText.getText().toString().trim().length() > 0) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
                        expense.setSpent(expense.getSpent() + Double.parseDouble(editExpenseSpendEditText.getText().toString()));
                        if (expense.getSpent() < 0) {
                            expense.setSpent(0);
                        }
                        if (databaseHelper.updateExpense(expense) > 0) {
                            success = true;
                            alertDialog.dismiss();
                        } else {
                            success = false;
                        }
                    } else {
                        success = false;
                    }
                }
                return success;
            }
        });

        Button saveButton = dialogLayout.findViewById(R.id.btn_alert_dialog_expense_spend_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editExpenseSpendEditText.getText().toString().trim().length() > 0) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
                    expense.setSpent(expense.getSpent() + Double.parseDouble(editExpenseSpendEditText.getText().toString()));
                    if (expense.getSpent() < 0) {
                        expense.setSpent(0);
                    }
                    if (databaseHelper.updateExpense(expense) > 0) {
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(mContext, "An error occurred while updating.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please enter a valid number.", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button cancelButton = dialogLayout.findViewById(R.id.btn_alert_dialog_expense_spend_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}

