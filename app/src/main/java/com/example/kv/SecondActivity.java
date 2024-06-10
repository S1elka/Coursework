package com.example.kv;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mAuth = FirebaseAuth.getInstance();

        checkAuthentication();

        NavigationBarView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment;
                if (item.getItemId() == R.id.home) {
                    fragment = new FragmentList();
                } else if (item.getItemId() == R.id.profile) {
                    checkAuthentication();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        fragment = new ProfileLogged();
                    } else {
                        fragment = new Profile();
                    }
                } else if (item.getItemId() == R.id.instruction){
                    fragment = new FragmentInf();
                }else if (item.getItemId() == R.id.search){
                    fragment = new FragmentMe();
                }
                else {
                    return false;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });

        FragmentList fragmentList = new FragmentList();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentList).commit();
    };
    private void checkAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
