package com.example.test_0040_Prj;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.test_0040_Prj.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	private EditText userName;
	private EditText passWord;
	private Button logButton;
	private CheckBox remPassw;
	private CheckBox autoLog;
	
	private SharedPreferences sp;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        logButton = (Button) findViewById(R.id.login);
        remPassw = (CheckBox) findViewById(R.id.rememberpassword);
        autoLog = (CheckBox) findViewById(R.id.autosign);
        
        logButton.setOnClickListener(new loginListener());
        
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        boolean rpwCheck = sp.getBoolean("remPassw", false);
        remPassw.setChecked(rpwCheck);
        boolean atlCheck = sp.getBoolean("autoLog", false);
        autoLog.setChecked(atlCheck);
        
        remPassw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				sp.edit().putBoolean("remPassw", isChecked).commit();
			}
		});
        autoLog.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				sp.edit().putBoolean("autoLog", isChecked).commit();
			}
		});
        
        if(rpwCheck){
        	String userNameStr = sp.getString("username", "");
        	String passWordStr = sp.getString("passWord", "");
        	userName.setText(userNameStr);
        	passWord.setText(passWordStr);
        }
        
        if(atlCheck){
        	Thread thread = new Thread(runnable);
			thread.start();
        }
    }
    
    private Handler handler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch(msg.what){
    			case 0 :
    				String userNameStr = userName.getText().toString();
    	        	String passWordStr = passWord.getText().toString();
    	        	sp.edit().putString("username", userNameStr).putString("passWord", passWordStr).commit();
					Intent intent = new Intent(MainActivity.this, SignActivity.class);
					startActivity(intent);
    				break;
    			case 1:
    				Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_LONG).show();
                    break;
    		}
    	}
    };
    
    Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String userNameStr = userName.getText().toString();
			String passWordStr = passWord.getText().toString();
			String path = "http://10.152.22.83:8282/mobileSale/attendence/androidLoginTest?userName="+userNameStr+"&passWordStr="+passWordStr+"";
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
    
    class loginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Thread thread = new Thread(runnable);
			thread.start();
		}
    }
    
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
}
