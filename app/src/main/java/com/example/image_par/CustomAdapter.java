package com.example.image_par;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Employee> {
    private Context context;
    private ArrayList<Employee> employees;

    public CustomAdapter(@NonNull Context context, ArrayList<Employee> employees) {
        super(context, R.layout.item, employees);
        this.context = context;
        this.employees = employees;
    }

    @Nullable
    @Override
    public Employee getItem(int position) {
        return employees.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        Employee employee = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
            holder.imageView = convertView.findViewById(R.id.image);
            holder.id = convertView.findViewById(R.id.id);
            holder.name = convertView.findViewById(R.id.name);
            holder.salary = convertView.findViewById(R.id.salary);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.id.setText("ID : " + employee.getId());
        holder.name.setText("이름 : " + employee.getName());
        holder.salary.setText("급여 : " + employee.getSalary());

        ImageThread thread = new ImageThread(context, employee.getImage());
        thread.start();
        try {
            thread.join();
            holder.imageView.setImageBitmap(thread.getBitmap());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView id;
        TextView name;
        TextView salary;
    }
}
