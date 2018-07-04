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

import java.util.List;

import adapters.BillAdapter;
import sqlite.model.Bill;

public class BillsFragment extends Fragment {
    private DatabaseHelper mDatabaseHelper;
    private List<Bill> mBills;
    private BillAdapter mBillAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bills, container, false);
        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, "Bills"));
        mDatabaseHelper = new DatabaseHelper(getContext());
        mBills = mDatabaseHelper.getAllBills();
        mBillAdapter = new BillAdapter(getActivity(), mBills);
        ListView listView = rootView.findViewById(R.id.lv_bills);
        listView.setAdapter(mBillAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bill bill = mBills.get(position);
                openAlertDialog(bill);
            }
        });

        return rootView;
    }

    public void openAlertDialog(final Bill bill) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Select an option for " + bill.getName());

        alertDialogBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getContext(), EntryActivity.class);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
            }
        });

        alertDialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabaseHelper.deleteBill(bill);
                mBillAdapter.clear();
                mBillAdapter.addAll(mDatabaseHelper.getAllBills());
                mBillAdapter.notifyDataSetChanged();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
