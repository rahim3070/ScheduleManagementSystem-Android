package com.example.rahi.schedulemanagement.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahi.schedulemanagement.R;
import com.example.rahi.schedulemanagement.model.EmployeeDetailsModel;

import java.util.ArrayList;

public class EmployeeDetailsAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<EmployeeDetailsModel> recordList;

    public EmployeeDetailsAdapter(Context context, int layout, ArrayList<EmployeeDetailsModel> recordList) {
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
        ImageView imageView;
        TextView txtName, txtEmail, txtUserName, txtPassword;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            //ID From employee_view.xml
            holder.txtName = row.findViewById(R.id.empName);
            holder.txtEmail = row.findViewById(R.id.empEmail);
            holder.txtUserName = row.findViewById(R.id.empUserName);
            holder.txtPassword = row.findViewById(R.id.empPassword);
            holder.imageView = row.findViewById(R.id.imgIcon);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        EmployeeDetailsModel model = recordList.get(position);
        holder.txtName.setText(model.getName());
        holder.txtEmail.setText(model.getEmail());
        holder.txtUserName.setText(model.getUsername());
        holder.txtPassword.setText(model.getPassword());

        byte[] recodrImage = model.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recodrImage, 0, recodrImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
