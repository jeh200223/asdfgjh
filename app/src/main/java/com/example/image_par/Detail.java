package com.example.image_par;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.image);
        TextView id = findViewById(R.id.id);
        TextView name = findViewById(R.id.name);
        TextView salary = findViewById(R.id.salary);

        Intent intent = getIntent();
        id.setText(intent.getStringExtra("id"));
        name.setText(intent.getStringExtra("name"));
        salary.setText(intent.getStringExtra("salary"));
        ImageThread thread = new ImageThread(Detail.this, intent.getStringExtra("image"));
        thread.start();
        try {
            thread.join();
            imageView.setImageBitmap(thread.getBitmap());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}