package com.example.cliff.budgetapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import adapters.ExpenseAdapter;
import sqlite.model.Expense;

public class ExpensesFragment extends Fragment {
    private DatabaseHelper mDatabaseHelper;
    private List<Expense> mExpense;
    private ExpenseAdapter mExpenseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);
        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, "Expenses"));

        mDatabaseHelper = new DatabaseHelper(getContext());
        mExpense = mDatabaseHelper.getAllExpenses();

        mExpenseAdapter = new ExpenseAdapter(getActivity(), mExpense);
        ListView listView = rootView.findViewById(R.id.lv_expenses);
        listView.setAdapter(mExpenseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Expense expense = mExpense.get(position);
                openAlertDialog(expense);
            }
        });

        return rootView;
    }

    public void openAlertDialog(final Expense expense) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Select an option for " + expense.getName());

        alertDialogBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getContext(), EntryActivity.class);
                intent.putExtra("expense", expense);
                intent.putExtra("viewFlipper", 1);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
            }
        });

        alertDialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mDatabaseHelper.deleteExpense(expense) != 0) {
                    mExpenseAdapter.clear();
                    mExpenseAdapter.addAll(mDatabaseHelper.getAllExpenses());
                    mExpenseAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Error deleting expense.", Toast.LENGTH_LONG).show();
                }

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
