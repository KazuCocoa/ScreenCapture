package com.notificationsample.notificationsample;


import android.app.Activity;

import android.graphics.Bitmap;
import android.os.*;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class CaptureScreen extends Activity {

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //View root = getWindow().getDecorView();
        //bitmap = getBitmapOfView(currentView);
        Toast.makeText(this, "We should success to store screen shots.", Toast.LENGTH_LONG).show();
        //createImageFromBitmap(bitmap);
        this.finish();
    }

    public Bitmap getBitmapOfView(View v){
        View rootview = v.getRootView();
        rootview.setDrawingCacheEnabled(false);
        rootview.setDrawingCacheEnabled(true);
        rootview.destroyDrawingCache();

        Bitmap bmp = rootview.getDrawingCache();
        return bmp;
    }

    public void createImageFromBitmap(Bitmap bmp){
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