package com.notificationsample.notificationsample;


import java.util.*;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

import android.os.*;

public class CaptureScreen extends Service {

    MyHandler mHandler=new MyHandler();

    Service my = this;
    private boolean mRunning = false;
    private NotificationManager nm;
    private static final int ID = 0;
    private static final int UPDATE = 1;
    public void onCreate()
    {
        super.onCreate();
        mRunning = true;
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    public void onStart(Intent it,int id){
        super.onStart(it, id);
        if ( mHandler.hasMessages(UPDATE)==false)
        {
            mHandler.sendEmptyMessage(UPDATE);
        }
    }
    public void onDestroy()
    {
        mRunning = false;
        mHandler.removeMessages(UPDATE);
        nm.cancel(ID);
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent arg0) {
    // TODO 自動生成されたメソッド・スタブ
        return null;
    }
    private void showNotification(String buf)
    {
        Notification n = new Notification(R.drawable.ic_launcher, buf, System.currentTimeMillis());
        Intent it = new Intent(this,MainActivity.class);
        PendingIntent pit = PendingIntent.getActivity(this, 0, it, Intent.FLAG_ACTIVITY_NEW_TASK);
        n.setLatestEventInfo(this, "時刻", buf, pit);
        nm.notify(ID, n);
    }
    class MyHandler extends Handler
    {
        public void handleMessage(Message msg)
        {
            if(mRunning == false)
            {
                return;
            }
            GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
            String buf = gc.get(Calendar.HOUR_OF_DAY) + ":" + gc.get(Calendar.MINUTE)+ ":" + gc.get(Calendar.SECOND);
            showNotification(buf);
            sendEmptyMessageDelayed(1, 1000);
        }
    }
}