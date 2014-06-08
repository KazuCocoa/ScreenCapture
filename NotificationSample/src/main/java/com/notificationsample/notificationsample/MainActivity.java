package com.notificationsample.notificationsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.notificationsample.notificationsample.R.id;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends ActionBarActivity {

    private Intent it = null;
    ImageView imageViewPreview;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(id.button1);
        Button stopButton = (Button) findViewById(id.button2);
        Button takePicture = (Button) findViewById(id.take_picture);
        Button showPicture = (Button) findViewById(id.show_picture);
        Button deletePicture = (Button) findViewById(id.delete_picture);


        imageViewPreview = (ImageView) findViewById(R.id.ImageViewPreview);

        startButton.setOnClickListener(startListener);
        stopButton.setOnClickListener(stopListener);
        takePicture.setOnClickListener(takePictureListener);
        showPicture.setOnClickListener(showPictureListener);
        deletePicture.setOnClickListener(deletePictureListener);

    }

    private View.OnClickListener startListener = new View.OnClickListener() {
        public void onClick(View v) {
            it = new Intent(MainActivity.this, Notification.class);
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

    private View.OnClickListener takePictureListener = new View.OnClickListener() {
        public void onClick(View v) {
            bitmap = getBitmapOfView(v);
            createImageFromBitmap(bitmap);
        }
    };

    private View.OnClickListener showPictureListener = new View.OnClickListener() {
        public void onClick(View v) {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            setImage(imageViewPreview, path + "/capturedscreen.jpg");
        }
    };

    private View.OnClickListener deletePictureListener = new View.OnClickListener() {
        public void onClick(View v) {
            deleteImage();
        }
    };

    public Bitmap getBitmapOfView(View v)
    {
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

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(path, "capturedscreen.jpg");

        try
        {
            //Make sure the Picture Directory exists.
            path.mkdirs();

            file.createNewFile();

            FileOutputStream os = new FileOutputStream(file);
            os.write(bytes.toByteArray());
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setImage(ImageView view, String filepath){
        view.setImageBitmap(BitmapFactory.decodeFile(filepath));
    }

    private void deleteImage(){
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(path, "capturedscreen.jpg");
        file.delete();
    }


}


