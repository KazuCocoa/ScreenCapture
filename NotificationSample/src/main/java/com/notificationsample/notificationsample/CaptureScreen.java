package com.notificationsample.notificationsample;


import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.*;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CaptureScreen extends Activity {

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_screen);

        Button startButton = (Button) findViewById(R.id.screen_capture_text_view);
        startButton.setOnClickListener(captureImageListener);
    }

    private View.OnClickListener captureImageListener = new View.OnClickListener() {
        public void onClick(View v) {
            bitmap = getBitmapOfView(v);
            createImageFromBitmap(bitmap);

            finish();
        }
    };

    public Bitmap getBitmapOfView(View v)
    {
        //ViewGroup rootview = (ViewGroup)getWindow().getDecorView();//.findViewById(android.R.id.content);

        //View rootview = this.getWindow().getDecorView();//.getRootView();

        View rootview = v.getRootView();
        rootview.setDrawingCacheEnabled(true);
        rootview.destroyDrawingCache();

        Bitmap bmp_cache = rootview.getDrawingCache();

        if(bmp_cache == null){
            return null;
        }

        Bitmap bmp = Bitmap.createBitmap(bmp_cache);
        rootview.setDrawingCacheEnabled(false);

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