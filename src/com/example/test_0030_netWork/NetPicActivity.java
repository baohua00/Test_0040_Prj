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

public class NetPicActivity extends Activity {
	private TextView textView;
	private EditText editText;
	private Button button;
	private ImageView imageView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_pict);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        
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
    				imageView.setImageBitmap((Bitmap) msg.obj);
    				break;
    				
    			case 1:
    				Toast.makeText(NetPicActivity.this, "get_pic_failure", Toast.LENGTH_LONG).show();
                    break;
    		}
    		
    	};
    };
    
    Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			String str = "http://www.chiphell.com/data/attachment/portal/201412/19/213729re3i1wpohr1hp181.jpg";
			String path = editText.getText().toString();
			URL url;
			final Bitmap bitmapFormNet;
			try {
				url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				InputStream inputStream = conn.getInputStream();
				bitmapFormNet = BitmapFactory.decodeStream(inputStream);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				handler.obtainMessage(1).sendToTarget();//ªÒ»°Õº∆¨ ß∞‹
                return;
			}
			handler.obtainMessage(0, bitmapFormNet).sendToTarget();
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
