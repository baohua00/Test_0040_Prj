package com.example.test_0040_Prj;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Timer;  
import java.util.TimerTask;  

import com.example.test_0040_Prj.R;

  
import android.app.PendingIntent;  
import android.app.Service;  
import android.appwidget.AppWidgetManager;  
import android.content.ComponentName;  
import android.content.Intent;  
import android.os.IBinder;  
import android.widget.RemoteViews;

public class UpdateTimeService  extends Service {  
  
    private Timer timer;  
    private TimerTask task = new TimerTask() {  
  
        @Override  
        public void run() {  
            // 得到widget管理器  
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());  
            ComponentName componentName = new ComponentName(getApplicationContext(), ExampleAppWidgetProvider.class);  
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.example_appwidget);  
              
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
            String time = formate.format(date);  
            views.setTextViewText(R.id.tv_time, time);  
  
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);  
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 100, intent, 0);  
            // 设置widget的点击事件  
            views.setOnClickPendingIntent(R.id.tv_time, pendingIntent);  
            // 更新widget  
            appWidgetManager.updateAppWidget(componentName, views);  
        }  
    };  
  
    @Override  
    public void onCreate() {  
        super.onCreate();  
  
        timer = new Timer();  
        timer.schedule(task, 0, 1000);// 开始任务  
    }  
  
    @Override  
    public IBinder onBind(Intent intent) {  
        return null;  
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        timer.cancel();// 结束任务  
    }  
}
