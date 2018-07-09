package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cliff.budgetapp.R;

import java.util.List;
import java.util.Locale;

import sqlite.model.Expense;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    private List<Expense> mExpense;
    private Context mContext;

    private static class ViewHolder {
        TextView tvExpenseName;
        TextView tvExpenseStatus;
        Button btnExpenseSpend;
        EditText etSpendInput;
        Button btnSubmitSpend;
        ProgressBar progressBarExpense;
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

            viewHolder.tvExpenseName = convertView.findViewById(R.id.tv_expense_name);
            viewHolder.tvExpenseName.setText(expense.getName());

            viewHolder.tvExpenseStatus = convertView.findViewById(R.id.tv_expense_status);
            String spent = String.format(Locale.US, "%.2f", expense.getSpent());
            String limit = String.format(Locale.US, "%.2f", expense.getLimit());
            viewHolder.tvExpenseStatus.setText(mContext.getString(R.string.text_list_view_expense_status, spent, limit));

            double progressBarNumber = Math.floor( (expense.getSpent() / expense.getLimit()) * 100);
            viewHolder.progressBarExpense = convertView.findViewById(R.id.progress_bar_expense);
            viewHolder.progressBarExpense.setProgress((int) progressBarNumber);

        } else {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_expense_item, parent, false);

            viewHolder.tvExpenseName = convertView.findViewById(R.id.tv_expense_name);
            viewHolder.tvExpenseName.setText(expense.getName());

            viewHolder.tvExpenseStatus = convertView.findViewById(R.id.tv_expense_status);
            String spent = String.format(Locale.US, "%.2f", expense.getSpent());
            String limit = String.format(Locale.US, "%.2f", expense.getLimit());
            viewHolder.tvExpenseStatus.setText(mContext.getString(R.string.text_list_view_expense_status, spent, limit));

            double progressBarNumber = Math.floor( (expense.getSpent() / expense.getLimit()) * 100);
            viewHolder.progressBarExpense = convertView.findViewById(R.id.progress_bar_expense);
            viewHolder.progressBarExpense.setProgress((int) progressBarNumber);
        }

        viewHolder.btnExpenseSpend = convertView.findViewById(R.id.btn_expense_spend);
        viewHolder.btnExpenseSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}

