package com.notificationsample.notificationsample;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

import android.graphics.Bitmap;
import android.os.*;
import android.support.v4.app.NotificationCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CaptureScreen extends Service {

    private NotificationManager nm;
    private static final int ID = 0;
    private static final int UPDATE = 1;
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    public void onStart(Intent it,int id) {
        super.onStart(it, id);
        showNotification();
    }

    public void onDestroy() {
        nm.cancel(ID);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
    // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    private void showNotification() {
        //Notification n = new Notification(R.drawable.ic_launcher, buf, System.currentTimeMillis());
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("text", "Notification Activity");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("notification title");
        builder.setContentText("notification text.");
        builder.setTicker("notification ticker");
        builder.setContentIntent(pendingIntent);

        nm.notify(ID, builder.build());
    }


    public void createImageFromBitmap(Bitmap bmp)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File( Environment.getExternalStorageDirectory() + "/capturedscreen.jpg");
        try
        {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            ostream.write(bytes.toByteArray());
            ostream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}