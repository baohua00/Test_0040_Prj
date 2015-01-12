package com.example.test_0030_netWork;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HttpRequestActivity extends Activity {
	private TextView textView;
	private EditText editText;
	private Button button;
	private TextView textView_result;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_http);
        textView = (TextView) findViewById(R.id.textView_http);
        editText = (EditText) findViewById(R.id.editText_http);
        button = (Button) findViewById(R.id.button_http);
        textView_result  = (TextView) findViewById(R.id.result);
        
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread thread = new Thread(runnable);
				thread.start();
			}
		});
        
    }
    
    private Handler handler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch(msg.what){
    			case 0 :
    				textView_result.setText((CharSequence) msg.obj);
    				break;
    			case 1:
    				Toast.makeText(HttpRequestActivity.this, "get_pic_failure", Toast.LENGTH_LONG).show();
                    break;
    		}
    		
    	};
    };
    
    Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String path = "http://10.152.22.83:8282/mobileSale/attendence/androidHttpTest?userName=18629615889&lac=test";
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
				handler.obtainMessage(1).sendToTarget();//ªÒ»°Õº∆¨ ß∞‹
                return;
			}
			
			handler.obtainMessage(0,result).sendToTarget();
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

}
