package com.example.test_0040_Prj.test;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class testJson {
	public static void main (String args[]) {
		String json = getData();
		json = json.replaceAll(" ", "");
		JSONObject object;
		try {
			object = (JSONObject) new JSONTokener(json).nextValue();
//			int listSize = object.getInt("total");
//			System.out.println(listSize);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getData(){
		return "{\"total\":0,\"rows\":[]}";
	}
}
