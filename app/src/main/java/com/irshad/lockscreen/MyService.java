package com.irshad.lockscreen;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    Handler handler = new Handler();
    int delay = 3000; //milliseconds
    public MyService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
       // Toast.makeText(this, "Invoke background service onCreate method.", Toast.LENGTH_LONG).show();
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        handler.postDelayed(new Runnable(){
//            public void run(){
//                //do something
//                System.out.println("in onStartCommand 11");
//                handler.postDelayed(this, delay);
//            }
//        }, delay);


        int returnValues= START_STICKY;
        System.out.println("in onStartCommand 11");
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
         super.onStartCommand(intent, flags, startId);

        Boolean value = intent.getBooleanExtra("flag",false);
        System.out.println("value===="+value);
        System.out.println("getClass()"+getClass());

        if (value==true) {
            System.out.println("value==if=="+value);
            getApplicationContext().stopService(i);
            super.getApplicationContext().stopService(i);
            onDestroy();
            return START_NOT_STICKY;
            // do something with the value here
        } else {
            System.out.println("value==else=="+value);
            return START_NOT_STICKY;

        }

    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //Toast.makeText(getApplicationContext(), "onTaskRemoved called", Toast.LENGTH_LONG).show();
        System.out.println("onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy====>");
        super.onDestroy();
        //Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean stopService(Intent name) {
        // TODO Auto-generated method stub
        System.out.println("stopServices called,name===="+name);
        return super.stopService(name);

    }
}
