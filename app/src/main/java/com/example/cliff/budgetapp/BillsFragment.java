package com.example.cliff.budgetapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bills, container, false);
        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, "Bills"));

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        List<Bill> bills = databaseHelper.getAllBills();
        BillAdapter billAdapter = new BillAdapter(getActivity(), bills);

        ListView listView = rootView.findViewById(R.id.lv_bills);
        listView.setAdapter(billAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), i +"", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
