package com.example.rahi.schedulemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rahi.schedulemanagement.R;
import com.example.rahi.schedulemanagement.model.ScheduleInfoViewModel;

import java.util.ArrayList;

public class ScheduleInfoViewAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ScheduleInfoViewModel> recordList;

    public ScheduleInfoViewAdapter(Context context, int layout, ArrayList<ScheduleInfoViewModel> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtSchDate, txtShift1, txtShift2, txtShift3;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ScheduleInfoViewAdapter.ViewHolder holder = new ScheduleInfoViewAdapter.ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            //ID From scheduleinfo_view.xml
            holder.txtSchDate = row.findViewById(R.id.scheduleDate);
            holder.txtShift1 = row.findViewById(R.id.shift1);
            holder.txtShift2 = row.findViewById(R.id.shift2);
            holder.txtShift3 = row.findViewById(R.id.shift3);

            row.setTag(holder);
        } else {
            holder = (ScheduleInfoViewAdapter.ViewHolder) row.getTag();
        }

        ScheduleInfoViewModel model = recordList.get(position);
        holder.txtSchDate.setText(model.getSchDate());
        holder.txtShift1.setText(model.getShift1());
        holder.txtShift2.setText(model.getShift2());
        holder.txtShift3.setText(model.getShift3());

        return row;
    }
}
