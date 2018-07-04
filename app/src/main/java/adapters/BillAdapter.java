package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

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
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Bill bill = getItem(position);
        ViewHolder viewHolder = new ViewHolder();

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
        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_bill_item, parent, false);

            viewHolder.tvBillName = convertView.findViewById(R.id.tv_bill_name);
            viewHolder.tvBillName.setText(getContext().getString(R.string.text_list_view_bill_name, bill.getName()));

            viewHolder.tvBillCost = convertView.findViewById(R.id.tv_bill_cost);
            viewHolder.tvBillCost.setText(getContext().getString(R.string.text_list_view_bill_cost, bill.getCost() + ""));

            viewHolder.tvBillStatus = convertView.findViewById(R.id.tv_bill_status);
            viewHolder.tvBillStatus.setText(getContext().getString(R.string.text_list_view_bill_due, bill.getDue()));

            viewHolder.cbPaid = convertView.findViewById(R.id.cb_bill_paid);
        }

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
