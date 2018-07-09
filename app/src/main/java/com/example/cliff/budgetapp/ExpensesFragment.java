package com.example.cliff.budgetapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

        return rootView;
    }
}
