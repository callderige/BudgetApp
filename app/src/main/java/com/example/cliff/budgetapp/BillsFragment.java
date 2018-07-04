package com.example.cliff.budgetapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import adapters.BillAdapter;
import sqlite.model.Bill;

public class BillsFragment extends Fragment {
    private DatabaseHelper mDatabaseHelper;
    private List<Bill> mBills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bills, container, false);
        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, "Bills"));
        mDatabaseHelper = new DatabaseHelper(getContext());
        mBills = mDatabaseHelper.getAllBills();
        BillAdapter billAdapter = new BillAdapter(getActivity(), mBills);
        ListView listView = rootView.findViewById(R.id.lv_bills);
        listView.setAdapter(billAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bill bill = mBills.get(position);
            }
        });

        return rootView;
    }
}
