package com.example.kv;

import static com.google.firebase.auth.AuthKt.getAuth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
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
import java.util.HashMap;
import java.util.List;

public class ProfileLogged extends Fragment {

    private Button but;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseAuth mAuth;
    DatabaseReference ref;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private void checkAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public ProfileLogged() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile_logged.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileLogged newInstance(String param1, String param2) {
        ProfileLogged fragment = new ProfileLogged();
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


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_logged, container, false);
        return inflater.inflate(R.layout.profile_logged, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        TextView hellotext;
        hellotext = view.findViewById(R.id.username);
        hellotext.setText("Добро пожаловать, "+user.getEmail()+" !");
        Button btn = (Button) view.findViewById(R.id.signout);
        ListView filmsListView = (ListView) view.findViewById(R.id.list);
        ref = database.getReference();
        String[] coun = {"1", "2"};
        List<String> l = new ArrayList<>(Arrays.asList(coun));
        List<Long> s = new ArrayList<Long>(Arrays.asList(1L, 2L));
        ListComp list = new ListComp(l, s);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = "";
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                for(DataSnapshot child: snapshot.child("Completed").child(user.getUid()).getChildren()){
                    list.names.add(child.getKey());
                    list.count.add(child.getValue(Long.class));
                }
                list.names.remove(0);
                list.names.remove(0);
                list.count.remove(0);
                list.count.remove(0);
                ArrayAdapter<String> usersAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_2,android.R.id.text1,  list.names){

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                        text1.setText(list.names.get(position));
                        String str = String.valueOf("Пройдено. Вы набрали "+list.count.get(position)+" балл(а/ов)");
                        text2.setText(str);
                        if(list.count.get(position)==4L){
                            text1.setTextColor(Color.GREEN);
                        }
                        return view;
                    }
                };
                filmsListView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Profile modalBottomSheet = new Profile();
                getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, modalBottomSheet, "findThisFragment")
                .commit();
            }
        });
    }

}
