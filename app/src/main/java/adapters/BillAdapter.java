package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cliff.budgetapp.DatabaseHelper;
import com.example.cliff.budgetapp.R;

import java.util.List;

import sqlite.model.Bill;

public class BillAdapter extends ArrayAdapter<Bill> {

    private List<Bill> mBill;
    private Context mContext;

    private static class ViewHolder {
        TextView tvBillName;
        TextView tvBillCost;
        TextView tvBillStatus;
        CheckBox cbPaid;
    }

    public BillAdapter(Context context, List<Bill> bill) {
        super(context, R.layout.listview_bill_item, bill);
        this.mBill = bill;
        this.mContext = context;
    }

    @Override
    @NonNull
    @SuppressWarnings("ConstantConditions")
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final Bill bill = getItem(position);

        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_bill_item, parent, false);

            viewHolder.tvBillName = convertView.findViewById(R.id.tv_bill_name);
            viewHolder.tvBillName.setText(getContext().getString(R.string.text_list_view_bill_name, bill.getName()));

            viewHolder.tvBillCost = convertView.findViewById(R.id.tv_bill_cost);
            viewHolder.tvBillCost.setText(getContext().getString(R.string.text_list_view_bill_cost, bill.getCost() + ""));

            viewHolder.tvBillStatus = convertView.findViewById(R.id.tv_bill_status);
            viewHolder.tvBillStatus.setText(getContext().getString(R.string.text_list_view_bill_due, bill.getDue()));

            viewHolder.cbPaid = convertView.findViewById(R.id.cb_bill_paid);
            if (bill.getPaid() == 0) {
                viewHolder.cbPaid.setChecked(false);
                viewHolder.tvBillStatus.setText(mContext.getString(R.string.text_list_view_bill_due, bill.getDue()));
            } else {
                viewHolder.cbPaid.setChecked(true);
                viewHolder.tvBillStatus.setText(mContext.getString(R.string.text_list_view_bill_paid));
            }
        } else {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_bill_item, parent, false);

            viewHolder.tvBillName = convertView.findViewById(R.id.tv_bill_name);
            viewHolder.tvBillName.setText(getContext().getString(R.string.text_list_view_bill_name, bill.getName()));

            viewHolder.tvBillCost = convertView.findViewById(R.id.tv_bill_cost);
            viewHolder.tvBillCost.setText(getContext().getString(R.string.text_list_view_bill_cost, bill.getCost() + ""));

            viewHolder.tvBillStatus = convertView.findViewById(R.id.tv_bill_status);
            viewHolder.tvBillStatus.setText(getContext().getString(R.string.text_list_view_bill_due, bill.getDue()));

            viewHolder.cbPaid = convertView.findViewById(R.id.cb_bill_paid);
            if (bill.getPaid() == 0) {
                viewHolder.cbPaid.setChecked(false);
                viewHolder.tvBillStatus.setText(mContext.getString(R.string.text_list_view_bill_due, bill.getDue()));
            } else {
                viewHolder.cbPaid.setChecked(true);
                viewHolder.tvBillStatus.setText(mContext.getString(R.string.text_list_view_bill_paid));
            }
        }

        viewHolder.cbPaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                if(compoundButton.isChecked()) {
                    viewHolder.tvBillStatus.setText(mContext.getString(R.string.text_list_view_bill_paid));
                    bill.setPaid(1);
                    databaseHelper.updateBill(bill);
                } else {
                    viewHolder.tvBillStatus.setText(mContext.getString(R.string.text_list_view_bill_due, bill.getDue()));
                    bill.setPaid(0);
                    databaseHelper.updateBill(bill);
                }
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return mBill.size();
    }

    @Override
    public Bill getItem(int position) {
        return mBill.get(position);
    }
}
