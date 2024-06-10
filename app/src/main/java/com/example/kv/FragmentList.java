package com.example.kv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private View view;

    String[] userNames = {""};
    String[] paths = {""};

    DatabaseReference ref;


    public FragmentList() {
        // Required empty public constructor
    }
    public static FragmentList newInstance(String param1, String param2) {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView filmsListView = (ListView) view.findViewById(R.id.list);
        String[] a = {""};
        List<String> b = new ArrayList<>(Arrays.asList(a));
        Countries country = new Countries(b, b, b, b);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                country.names = snapshot.child("Countries").child("names").getValue(t);
                country.paths = snapshot.child("Countries").child("paths").getValue(t);

                userNames = country.names.toArray(new String[0]);
                paths = country.paths.toArray(new String[0]);


                ArrayAdapter<String> usersAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.gallery_item,R.id.countrytext,  userNames) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = (TextView) view.findViewById(R.id.countrytext);
                        text1.setText(userNames[position]);
                        ImageView img = (ImageView) view.findViewById(R.id.galleryposter);
                        int imgSrcId = getResources().getIdentifier(paths[position], "drawable", requireActivity().getPackageName());
                        img.setImageResource(imgSrcId);
                        return view;
                    }
                };

                filmsListView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("A", "cancelled");
            }
        });

        String[] pre_info = getResources().getStringArray(R.array.pre_info);
        String[] urls = getResources().getStringArray(R.array.urls);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        filmsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // по позиции получаем выбранный элемент
                String selectedItem = userNames[position];
                String selectedItemImgPath = paths[position];
                String selectedInfo = pre_info[position];
                String selectedUrl = urls[position];
                Bundle bundle = new Bundle();
                bundle.putString("CounName", selectedItem);
                bundle.putString("imgPath", selectedItemImgPath);
                bundle.putString("Inf", selectedInfo);
                bundle.putString("Url", selectedUrl);
                bundle.putInt("Pos", position);
                FragmentBottom modalBottomSheet = new FragmentBottom();
                modalBottomSheet.setArguments(bundle);
                modalBottomSheet.show(getChildFragmentManager(), FragmentBottom.TAG);
            }
        });

    }
}
