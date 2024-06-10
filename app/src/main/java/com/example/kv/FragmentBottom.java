package com.example.kv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class FragmentBottom extends BottomSheetDialogFragment {
    public static final String TAG = "FragmentBottom";
    private ImageView imageView;
    private TextView textView;

    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom, container, false);
        Button btn = rootView.findViewById(R.id.btn);
        String country = getArguments().getString("CounName");
        String imgPath = getArguments().getString("imgPath");
        String inform = getArguments().getString("Inf");
        String urel = getArguments().getString("Url");
        int pos = getArguments().getInt("Pos", 0);
        int imgSrcId = getResources().getIdentifier(imgPath, "drawable", requireActivity().getPackageName());
        imageView = rootView.findViewById(R.id.poster);
        textView = rootView.findViewById(R.id.info);
        text = rootView.findViewById(R.id.wow);
        imageView.setImageResource(imgSrcId);
        textView.setText(country);
        text.setText(inform);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ThirdActivity.class);
                intent.putExtra("CounName", country);
                intent.putExtra("imgPath", imgPath);
                intent.putExtra("Url", urel);
                intent.putExtra("Pos", pos);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
