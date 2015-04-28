package com.example.test_0040_Prj;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.test_0040_Prj.R;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
	
	private static final String TAG="ExampleAppWidgetProvider";

	public void onReceive(Context context, Intent intent) 
    {
        Log.d(TAG, "mythou--------->onReceive");
        super.onReceive(context, intent);
    }

    // 每次更新都调用一次该方法，使用频繁
    @Override  
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {  
        super.onUpdate(context, appWidgetManager, appWidgetIds);  
  
        Log.d(TAG, "mythou--------->onUpdate");  
  
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);  
        Date date = new Date(System.currentTimeMillis());  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        String time = format.format(date);  
        views.setTextViewText(R.id.tv_time, time);  
        appWidgetManager.updateAppWidget(appWidgetIds[0], views);  
    }

    // 每删除一个就调用一次
    public void onDeleted(Context context, int[] appWidgetIds) 
    {
        Log.d(TAG, "mythou--------->onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    // 当该Widget第一次添加到桌面是调用该方法，可添加多次但只第一次调用
    public void onEnabled(Context context) 
    {
        Log.d(TAG, "mythou--------->onEnabled");
        super.onEnabled(context);
        Intent intent = new Intent(context, UpdateTimeService.class);  
        context.startService(intent);
    }

    // 当最后一个该Widget删除是调用该方法，注意是最后一个
    public void onDisabled(Context context) 
    {
        Log.d(TAG, "mythou--------->onDisabled");
        super.onDisabled(context);
        Intent intent = new Intent(context, UpdateTimeService.class);  
        context.stopService(intent);
    }
}
