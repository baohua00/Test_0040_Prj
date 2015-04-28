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

    // ÿ�θ��¶�����һ�θ÷�����ʹ��Ƶ��
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

    // ÿɾ��һ���͵���һ��
    public void onDeleted(Context context, int[] appWidgetIds) 
    {
        Log.d(TAG, "mythou--------->onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    // ����Widget��һ����ӵ������ǵ��ø÷���������Ӷ�ε�ֻ��һ�ε���
    public void onEnabled(Context context) 
    {
        Log.d(TAG, "mythou--------->onEnabled");
        super.onEnabled(context);
        Intent intent = new Intent(context, UpdateTimeService.class);  
        context.startService(intent);
    }

    // �����һ����Widgetɾ���ǵ��ø÷�����ע�������һ��
    public void onDisabled(Context context) 
    {
        Log.d(TAG, "mythou--------->onDisabled");
        super.onDisabled(context);
        Intent intent = new Intent(context, UpdateTimeService.class);  
        context.stopService(intent);
    }
}
