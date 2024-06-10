package com.example.kv;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestClass {

    public List<String> questions;
    public List<String> answers;

    public TestClass(List<String> _questions, List<String> _answers){
        questions = _questions;
        answers = _answers;
    }
}
