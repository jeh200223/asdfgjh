package com.example.image_par;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ParsingActivity extends AppCompatActivity {
    int type = 1;
    ArrayList<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing);
        setTitle("Parsing 하기");
        Intent intent = getIntent();
        String page = intent.getStringExtra("page");
        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Employee employee = employees.get(position);
                Intent intent1 = new Intent(ParsingActivity.this, Detail.class);
                intent1.putExtra("id", employee.getId());
                intent1.putExtra("name", employee.getName());
                intent1.putExtra("salary", employee.getSalary());
                intent1.putExtra("image", employee.getImage());
                startActivity(intent1);
            }
        });
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    JSONThread thread = new JSONThread(ParsingActivity.this, page);
                    thread.start();
                    try {
                        thread.join();
                        JSONParser parser = new JSONParser(ParsingActivity.this);
                        employees = parser.Parsing(thread.getResuit());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    CustomAdapter adapter = new CustomAdapter(ParsingActivity.this, employees);
                    listView.setAdapter(adapter);
                } else {

                }
            }
        });

        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                type = 1;
                break;
            case R.id.item2:
                type = 2;
                break;
        }
        item.setChecked(true);
        return true;
    }
}