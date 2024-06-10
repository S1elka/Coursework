package com.example.kv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ThirdActivity extends AppCompatActivity {

    private Button button;

    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        button = findViewById(R.id.button);
        // Получаем данные о фильме
        String counName = getIntent().getStringExtra("CounName");
        String imgPath = getIntent().getStringExtra("imgPath");
        String urel = getIntent().getStringExtra("Url");
        int pos = getIntent().getIntExtra("Pos", 0);

        // Передаем данные фрагменту
        Bundle bundle = new Bundle();
        bundle.putString("CounName", counName);
        bundle.putString("imgPath", imgPath);
        bundle.putString("Url", urel);
        bundle.putInt("Pos", pos);


        // Создаем и отображаем фрагмент
        FragmentInfo fragment = new FragmentInfo();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
