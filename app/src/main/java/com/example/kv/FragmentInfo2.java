package com.example.kv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentInfo2 extends Fragment {
    private ImageView imageView;
    private TextView filmNameView;
    Button btn;
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info2, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String filmName = bundle.getString("CounName");
            String imgPath = bundle.getString("imgPath");
            String urel = bundle.getString("Url");
            int pos = bundle.getInt("Pos");
            WebView webView = (WebView) view.findViewById(R.id.web);
            webView.loadUrl(urel);


            int imgSrcId = getResources().getIdentifier(imgPath, "drawable", requireActivity().getPackageName());
            imageView = view.findViewById(R.id.poster);
            imageView.setImageResource(imgSrcId);
            btn = (Button) view.findViewById(R.id.button2);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("CounName", filmName);
                    bundle.putString("imgPath", imgPath);
                    bundle.putString("Url", urel);
                    bundle.putInt("Pos", pos);
                    FragmentInfo3 modalBottomSheet = new FragmentInfo3();
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

