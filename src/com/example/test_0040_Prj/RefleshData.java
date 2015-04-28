package com.example.test_0040_Prj;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;

public class RefleshData implements Runnable{
	private Handler mHandler;
	
	public RefleshData(){
		
	}
	
	public RefleshData(Handler mHandler){
		this.mHandler = mHandler;
	}

	@Override
	public void run() {
		String path = "http://wq.ipower.ah.cn/mobileSale/attendence/attences?pagesize=10&page=1&userName=yandou&startDate=&endDate=";
//		String path = "http://10.152.22.83:8282/mobileSale/attendence/attences?pagesize=10&page=1&userName=yandou&startDate=&endDate=";
		URL url;
		String result = "";
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				byte[] data = StreamTool.read(inputStream);
				result = new String(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mHandler.obtainMessage(1).sendToTarget();
            return;
		}
		mHandler.obtainMessage(0,result).sendToTarget();
	}

}
