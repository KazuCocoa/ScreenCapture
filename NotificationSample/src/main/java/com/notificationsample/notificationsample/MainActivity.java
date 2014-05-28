package com.notificationsample.notificationsample;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.notificationsample.notificationsample.R.id;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends ActionBarActivity {

    private Intent it = null;
    ImageView imageViewCapture, imageViewPreview;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(id.button1);
        Button stopButton = (Button) findViewById(id.button2);
        imageViewPreview = (ImageView) findViewById(R.id.ImageViewPreview);
        imageViewCapture = (ImageView) findViewById(id.ImageViewCapture);

        startButton.setOnClickListener(startListener);
        stopButton.setOnClickListener(stopListener);
        imageViewCapture.setOnClickListener(takePictureListenr);
    }

    private View.OnClickListener startListener = new View.OnClickListener() {
        public void onClick(View v) {
            it = new Intent(MainActivity.this, CaptureScreen.class);
            startService(it);
        }
    };

    private View.OnClickListener stopListener = new View.OnClickListener() {
        public void onClick(View v) {
            if ( it!= null)
            {
                stopService(it);
            }
            it = null;
        }
    };

    private View.OnClickListener takePictureListenr = new View.OnClickListener() {
        public void onClick(View v) {
            bitmap = getBitmapOfView(imageViewCapture);
            imageViewPreview.setImageBitmap(bitmap);
            createImageFromBitmap(bitmap);
        }
    };

    private Intent setServiceIntent(){
        return it;
    }

    private Intent getServiceIntent(){
        return it;
    }

    public Bitmap getBitmapOfView(View v)
    {
        Toast.makeText(this, "take a bitmap", Toast.LENGTH_LONG).show();
        View rootview = v.getRootView();
        rootview.setDrawingCacheEnabled(true);
        Bitmap bmp = rootview.getDrawingCache();
        return bmp;
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


