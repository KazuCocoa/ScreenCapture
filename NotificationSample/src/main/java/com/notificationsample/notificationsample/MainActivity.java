package com.notificationsample.notificationsample;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.notificationsample.notificationsample.R.id;

public class MainActivity extends ActionBarActivity {

    Intent it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(id.button1);
        Button stopButton = (Button) findViewById(id.button2);

        startButton.setOnClickListener(startListener);
        stopButton.setOnClickListener(stopListener);
    }

    private View.OnClickListener startListener = new View.OnClickListener() {
        public void onClick(View v) {
            it = new Intent(MainActivity.this, CaptureScreen.class);
            startService(it);
        }
    };

    private View.OnClickListener stopListener = new View.OnClickListener() {
        public void onClick(View v) {
            if ( it != null)
            {
                stopService(it);
            }
            it = null;
        }
    };
}
