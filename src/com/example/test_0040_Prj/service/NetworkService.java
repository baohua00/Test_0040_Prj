package com.example.test_0040_Prj.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkService {
	public static String getJson(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			InputStream inputStream = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int resl = inputStream.read(buffer);
			String json = buffer.toString();
			return json;
		}
		
		return null;
	}
}
