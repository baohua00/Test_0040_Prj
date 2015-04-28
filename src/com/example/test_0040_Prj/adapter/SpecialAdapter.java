package com.example.test_0040_Prj.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class SpecialAdapter extends SimpleAdapter{
	
    private int[] colors = new int[]{0x30FF0000, 0x300000FF};
    private List<? extends Map<String, ?>> myData;
    
    public SpecialAdapter(Context context, List<? extends Map<String, ?>> data,  
            int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        myData = data;
    }
    
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        View view = super.getView(position, convertView, parent);
        
        String state = myData.get(position).get("state").toString();
        
        if ("上班".equals(state)) view.setBackgroundColor(colors[0]);
        if ("下班".equals(state)) view.setBackgroundColor(colors[1]);
        return view;  
    }  
}