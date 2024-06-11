package com.example.kv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.example.kv.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SharedPreferences sPref;

    FirebaseAuth mAuth;

    Fragment fragment;
    int cout=0;

    int c = 0;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        MotionLayout m = (MotionLayout)findViewById(R.id.circle);
        mAuth = FirebaseAuth.getInstance();
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imageView = findViewById(R.id.imageView3);
        imageView.startAnimation(rotate);
        Intent intent = new Intent(this, SecondActivity.class);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            binding.textView.setText("С возвращением!");
            binding.reg.setText("Продолжить");
        } else {
            fragment = new Profile();

        }
        binding.reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        if (binding.imageView3.getVisibility() == View.VISIBLE) {
            ImageView imageView = binding.imageView3;
            Animation shakeAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
            imageView.startAnimation(shakeAnimation);
        }
    }
}