package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.aidltest3.IMyAidlInterface;
import com.example.client.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    IMyAidlInterface myAidlInterface ;
    private static final String TAG = "MainActivity:evan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(activityMainBinding.getRoot());

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                try {
                    myAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
                    String st= myAidlInterface.getStr();
                    Log.i(TAG,"st:" + st);
                    activityMainBinding.clientTv.setText(st);
                } catch (RemoteException e) {
                    Log.i(TAG,"onServiceConnected:" + e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };  //end connection

        Intent intent = new Intent();
        intent.setPackage("com.example.aidltest3");
        intent.setAction("com.example.service.action");
        bindService(intent,connection,BIND_AUTO_CREATE);
    }
}