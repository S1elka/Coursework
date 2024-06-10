package com.example.kv;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentInfo extends Fragment {
    private ImageView imageView;
    private TextView filmNameView;
    Button btn;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        imageView = view.findViewById(R.id.poster);
        filmNameView = view.findViewById(R.id.filmNameView);
        textView = view.findViewById(R.id.text_info);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String filmName = bundle.getString("CounName");
            String imgPath = bundle.getString("imgPath");
            String urel = bundle.getString("Url");
            int pos = bundle.getInt("Pos");
            String[] text = getResources().getStringArray(R.array.info1);

            int imgSrcId = getResources().getIdentifier(imgPath, "drawable", requireActivity().getPackageName());

            textView.setText(text[pos]);
            imageView.setImageResource(imgSrcId);
            filmNameView.setText(filmName);
            btn = (Button) view.findViewById(R.id.btn_info2);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("CounName", filmName);
                    bundle.putString("imgPath", imgPath);
                    bundle.putString("Url", urel);
                    bundle.putInt("Pos", pos);
                    FragmentInfo2 modalBottomSheet = new FragmentInfo2();
                    modalBottomSheet.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, modalBottomSheet, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        return view;
    }
}
