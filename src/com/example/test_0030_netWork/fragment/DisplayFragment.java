package com.example.test_0030_netWork.fragment;

import com.example.test_0030_netWork.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DisplayFragment extends Fragment {  
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.display_fragment, container, false);  
        return view;  
    }  
}