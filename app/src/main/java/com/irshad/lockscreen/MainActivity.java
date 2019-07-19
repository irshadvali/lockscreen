package com.irshad.lockscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button b_enable, b_lock;
    EditText answerET;
    TextView questionTV;
    LinearLayout ltvOne;
    static final int RESULT_ENABLE = 11;
    DevicePolicyManager devicePolicyManager;
    ComponentName componentName;
    boolean result = false;
//    private BroadcastReceiver myReceiver;
    Handler handler = new Handler();
    int delay = 1000; //milliseconds
    boolean serviceFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
      //  b_enable = (Button) findViewById(R.id.b_enable);
        b_lock = (Button) findViewById(R.id.b_lock);
        answerET=(EditText) findViewById(R.id.answerET);
        questionTV=(TextView) findViewById(R.id.questionTV);
        ltvOne=(LinearLayout) findViewById(R.id.ltvOne);

        Random rand = new Random();

        // Obtain a number between [0 -4 ].
        final int n = rand.nextInt(7);


       final String[] questionData = new String[] {
                "2+2",
                "3+4",
                "4/2",
                "3+6",
                "4+7",
                "4*9",
                "7-2",
               "4+7+5"
        };
        final String[] answerData = new String[] {
                "4",
                "7",
                "2",
                "9",
                "11",
                "36",
                "5",
                "16"
        };

        final String[] backgroudColor = new String[] {
                "#0D47A1",
                "#00695C",
                "#37474F",
                "#283593",
                "#FFA000",
                "#4527A0",
                "#546E7A",
                "#558B2F"
        };
        ltvOne.setBackgroundColor(Color.parseColor(backgroudColor[n]));
        questionTV.setText(questionData[n]);

/*
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(MainActivity.this, Controller.class);
        final boolean active = devicePolicyManager.isAdminActive(componentName);

        if (active) {
            b_enable.setText("Disable");
            b_lock.setVisibility(View.VISIBLE);
        } else {
            b_enable.setText("Enable");
            b_lock.setVisibility(View.GONE);
        }

        b_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (active) {
                    devicePolicyManager.removeActiveAdmin(componentName);
                    b_enable.setText("Enable");
                    b_lock.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Additional text explaining why we need this permission");
                    startActivityForResult(intent, RESULT_ENABLE);
                }

            }
        });

*/
        b_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String values=answerET.getText().toString();
                if(values.length()>0){
                    values=values.trim();
                }

               if(values.equals(answerData[n])){
                    //devicePolicyManager.lockNow();
                    serviceFlag=true;
                    Intent intent = new Intent(MainActivity.this, MyService.class);
                    intent.putExtra("flag", true);
                    intent.putExtra("activity", MainActivity.class);
                    startService(intent);
                    getApplicationContext().stopService(intent);

                    finish();System.exit(1);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Your answer is wrong please try again",Toast.LENGTH_SHORT).show();
                }



            }
        });

//        myReceiver= new BroadcastReceiver(){
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                // Waking up mobile if it is sleeping
//                WakeLocker.acquire(getApplicationContext());
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("working now");
//                        //Do something after 100ms
//                    }
//                }, 1000);
//                WakeLocker.release();
//            }
//        };


    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    b_enable.setText("Disable");
                    b_lock.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "You have enabled the Admin Device features", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Problem to enable the Admin Device features", Toast.LENGTH_SHORT).show();
                }
                return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    */

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (event.getKeyCode()) {

            case KeyEvent.KEYCODE_MENU:
                result = true;
                break;

            case KeyEvent.KEYCODE_VOLUME_UP:
                result = true;
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                result = true;
                break;
            case KeyEvent.KEYCODE_BACK:
                result = true;
                break;
            case KeyEvent.ACTION_DOWN:
                result = true;
                break;

            default:
               // result = super.dispatchKeyEvent(event);
                break;

        }

        return result;
    }

    protected void onResume() {
        System.out.println("in onResume");
        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("flag", true);
        (MainActivity.this).stopService(intent);


        super.onResume();
    }

    @Override
    protected void onPause() {
        System.out.println("in onPause");
        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("flag", true);
        (MainActivity.this).stopService(intent);
// TODO Auto-generated method stub
        super.onPause();
       // this.unregisterReceiver(this.myReceiver);
    }


    @Override
    protected void onStart() {
        System.out.println("in onStart");

        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("flag", true);
        (MainActivity.this).stopService(intent);
//        IntentFilter intentFilter = new IntentFilter();
//        //intentFilter.addDataType(String);
//        registerReceiver(myReceiver, intentFilter);
        super.onStart();

    }

    @Override
    protected void onStop() {
        System.out.println("in onStop"+serviceFlag);
        if(!serviceFlag) {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            intent.putExtra("flag", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startService(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            intent.putExtra("flag", true);
            (MainActivity.this).stopService(intent);
        }
        super.onStop();

    }
    @Override
    protected void onRestart() {

        if(!serviceFlag){
            Intent intent = new Intent(MainActivity.this, MyService.class);
            intent.putExtra("flag", false);
            startService(intent);


        }else {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            intent.putExtra("flag", true);
            (MainActivity.this).stopService(intent);
        }
        super.onRestart();

    }
    @Override
    protected void onDestroy() {
        System.out.println("in onDestroy"+serviceFlag);
        if(!serviceFlag){
            Intent intent = new Intent(MainActivity.this, MyService.class);
            intent.putExtra("flag", false);
            startService(intent);


        }else {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            intent.putExtra("flag", true);
            (MainActivity.this).stopService(intent);
        }


        super.onDestroy();

    }


}