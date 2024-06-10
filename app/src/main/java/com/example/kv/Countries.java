package com.example.kv;

import java.util.List;

public class Countries {
    List<String> names;
    List<String> paths;
    List<String> preInf;
    List<String> urls;

    public Countries(List<String> _names, List<String> _paths, List<String> _preInf, List<String> _urls){
        names = _names;
        paths = _paths;
        preInf = _preInf;
        urls = _urls;
    }
}
