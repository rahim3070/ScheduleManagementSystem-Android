package com.example.rahi.schedulemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rahi.schedulemanagement.R;
import com.example.rahi.schedulemanagement.model.ScheduleAssignViewModel;

import java.util.ArrayList;

public class ScheduleAssignViewAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ScheduleAssignViewModel> recordList;

    public ScheduleAssignViewAdapter(Context context, int layout, ArrayList<ScheduleAssignViewModel> recordList) {
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
        TextView txtEmpId, txtSchDate, txtShift1, txtShift2, txtShift3, txtShift4;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ScheduleAssignViewAdapter.ViewHolder holder = new ScheduleAssignViewAdapter.ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            //ID From scheduleassign_view.xml
            holder.txtEmpId = row.findViewById(R.id.empId);
            holder.txtSchDate = row.findViewById(R.id.scheduleDate);
            holder.txtShift1 = row.findViewById(R.id.shift1);
            holder.txtShift2 = row.findViewById(R.id.shift2);
            holder.txtShift3 = row.findViewById(R.id.shift3);
            holder.txtShift4 = row.findViewById(R.id.shift4);

            row.setTag(holder);
        } else {
            holder = (ScheduleAssignViewAdapter.ViewHolder) row.getTag();
        }

        ScheduleAssignViewModel model = recordList.get(position);
        holder.txtEmpId.setText(model.getEmpId());
        holder.txtSchDate.setText(model.getSchDate());
        holder.txtShift1.setText(model.getShift1());
        holder.txtShift2.setText(model.getShift2());
        holder.txtShift3.setText(model.getShift3());
        holder.txtShift4.setText(model.getShift4());

        return row;
    }
}
