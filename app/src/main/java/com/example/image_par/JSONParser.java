package com.example.image_par;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
    private Context context;

    public JSONParser(Context context) {
        this.context = context;
    }

    public ArrayList<Employee> Parsing(String json) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONArray array = root.getJSONArray((String) root.names().get(0));
            for (int i = 0; i < array.length(); i++) {
                Employee employee = new Employee();
                JSONObject object = array.getJSONObject(i);
                String id = object.getString((String) object.names().get(0));
                String name = object.getString((String) object.names().get(1));
                String salary = object.getString((String) object.names().get(2));
                String image = object.getString((String) object.names().get(3));
                employee.setId(id);
                employee.setName(name);
                employee.setSalary(salary);
                employee.setImage(image);
                employees.add(employee);
            }
        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return employees;
    }
}
