package com.example.test_0040_Prj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

import android.util.Log;

public class JsonToMap {
	private static final String TAG = "HistoryListAdpter";
	
	private String data;
	private int listSize;
	private List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
	
	public JsonToMap(){
		
	}
	
	public JsonToMap(String data){
		this.data = data;
	}
	
	public List<Map<String, Object>> returnListData(){
		JSONObject object;
		JSONArray rows;
		try {
			data = data.replaceAll(" ", "");
			data = data.replaceAll("[\n]","");
			object = (JSONObject) new JSONTokener(data).nextValue();
			listSize = object.getInt("total");
			Log.d(TAG, "listSize: " + listSize);
			rows = object.getJSONArray("rows");
			int rowsLength = rows.length();
			for (int i = 0; i < rowsLength; i++) {
				JSONObject rowData = rows.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userNameAndTime", rowData.get("userName")+" "+rowData.get("date")+" "+rowData.get("time"));
				map.put("attenceTypeName", rowData.get("attenceTypeName")+" "+("0".equals(rowData.get("attence_fashion"))?"正常":"异常"));
				map.put("state", rowData.get("attenceTypeName"));
		        map.put("img", R.drawable.accept);
				listData.add(map);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listData;
	}
	
}
