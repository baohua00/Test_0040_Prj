package com.example.test_0040_Prj;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.test_0040_Prj.R;
import com.example.test_0040_Prj.adapter.SpecialAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignActivity extends Activity implements com.example.test_0040_Prj.view.XListView.IXListViewListener {
	
//	private ListView mListView;
    private Button mButton; 
    private TextView mTextView;
    private com.example.test_0040_Prj.view.XListView mListView;
    private Handler mHandler;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        
        mListView = (com.example.test_0040_Prj.view.XListView) findViewById(R.id.xListView);
        mListView.setPullLoadEnable(true);
//		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(false);
//		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
//		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		
		mTextView = (TextView) findViewById(R.id.textView);
        
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread thread = new Thread(runnable);
				thread.start();
			}
		});
        
        Thread thread = new Thread(runnable_HistoryList);
		thread.start();
        
    }
    
    private Handler handler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch(msg.what){
    			case 0 :
    				JsonToMap historyListAdpter = new JsonToMap((String) msg.obj);
    				List<Map<String, Object>> listData = historyListAdpter.returnListData();
    				String state = (String) listData.get(0).get("state");
    				mTextView.setText(state);
    				break;
    			case 1:
    				Toast.makeText(SignActivity.this, "get_json_failure", Toast.LENGTH_LONG).show();
                    break;
    		}
    		
    	};
    };
    
    Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String path = "http://10.152.22.83:8282/mobileSale/attendence/androidHttpTest?userName=18629615889&lac=test";
//			String path = "http://wq.ipower.ah.cn/mobileSale/attendence/attences?pagesize=10&page=1&userName=18629615889&startDate=&endDate=";
			URL url;
			final String result;
			byte[] array = new byte[1024];
			try {
				url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				InputStream inputStream = conn.getInputStream();
				inputStream.read(array);
				result = new String(array,"UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				handler.obtainMessage(1).sendToTarget();
                return;
			}
			
			handler.obtainMessage(0,result).sendToTarget();
		}
	};
	
	private Handler historyListHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch(msg.what){
    			case 0 :
    				JsonToMap historyListAdpter = new JsonToMap((String) msg.obj);
    				List<Map<String, Object>> listData = historyListAdpter.returnListData();
    				SpecialAdapter adapter = new SpecialAdapter(SignActivity.this ,listData,R.layout.listview,
    		                new String[]{"attenceTypeName","userNameAndTime","img"},
    		                new int[]{R.id.title,R.id.info,R.id.img});
    		        mListView.setAdapter(adapter);
    		        Toast.makeText(SignActivity.this, "load_data_success", Toast.LENGTH_LONG).show();
    		        onLoad();
    				break;
    			case 1:
    				Toast.makeText(SignActivity.this, "load_data_faild", Toast.LENGTH_LONG).show();
    				onLoad();
                    break;
    		}
    		
    	};
    };
	
	Runnable runnable_HistoryList = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String path = "http://wq.ipower.ah.cn/mobileSale/attendence/attences?pagesize=10&page=1&userName=18629615889&startDate=&endDate=";
//			String path = "http://10.152.22.83:8282/mobileSale/attendence/attences?pagesize=10&page=1&userName=yandou&startDate=&endDate=";
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
				// TODO Auto-generated catch block
				e.printStackTrace();
				historyListHandler.obtainMessage(1).sendToTarget();
                return;
			}
			historyListHandler.obtainMessage(0,result).sendToTarget();
		}
	};
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Thread thread = new Thread(runnable_HistoryList);
				thread.start();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 1000);
	}

}
