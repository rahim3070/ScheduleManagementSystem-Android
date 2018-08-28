package com.example.rahi.schedulemanagement.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rahi.schedulemanagement.R;
import com.example.rahi.schedulemanagement.model.EmployeeSpinnerModel;

import java.util.ArrayList;

public class EmployeeSpinnerAdapter extends BaseAdapter {
    Activity mActivity;
    ArrayList<EmployeeSpinnerModel> mEmployeeSpinnerModel;
    LayoutInflater mLayoutInflater;
    ViewHolder holder;

    public EmployeeSpinnerAdapter(Activity activity, ArrayList<EmployeeSpinnerModel> mEmployeeSpinnerModelList) {
        this.mActivity = activity;
        this.mEmployeeSpinnerModel = mEmployeeSpinnerModelList;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEmployeeSpinnerModel.size();
    }

    @Override
    public Object getItem(int position) {
        return mEmployeeSpinnerModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView empId, empName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.employee_spinner_view, null, false);
            holder = new ViewHolder();

            holder.empId = convertView.findViewById(R.id.textViewEmpId);
            holder.empName = convertView.findViewById(R.id.textViewEmpName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EmployeeSpinnerModel employeeSpinnerModel = mEmployeeSpinnerModel.get(position);

        holder.empId.setText(String.valueOf(employeeSpinnerModel.getId()));
        holder.empName.setText(employeeSpinnerModel.getEmpName());

        return convertView;
    }
}
