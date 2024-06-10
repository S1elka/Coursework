package com.example.kv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentInfo3 extends Fragment {

    Button btn;
    ImageView imageView;

    FirebaseAuth mAuth;
    TextView filmNameView;
    TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info3, container, false);

        imageView = view.findViewById(R.id.poster);
        filmNameView = view.findViewById(R.id.filmNameView);
        textView = view.findViewById(R.id.text_info3);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String filmName = bundle.getString("CounName");
            String imgPath = bundle.getString("imgPath");
            String urel = bundle.getString("Url");
            int pos = bundle.getInt("Pos");

            String[] text = getResources().getStringArray(R.array.info3);
            textView.setText(text[pos]);

            int imgSrcId = getResources().getIdentifier(imgPath, "drawable", requireActivity().getPackageName());

            imageView.setImageResource(imgSrcId);
            filmNameView.setText(filmName);
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            btn = (Button) view.findViewById(R.id.btn_info2);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser!= null){
                        Intent intent = new Intent(getActivity(), TestActivity.class);
                        intent.putExtra("CounName", filmName);
                        intent.putExtra("imgPath", imgPath);
                        intent.putExtra("Url", urel);
                        intent.putExtra("Pos", pos);
                        startActivity(intent);
                    }
                    else {
                        Snackbar.make(v, "Чтобы пройти тест для самопроверки, необходимо авторизоваться", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }

        return view;
    }
}

