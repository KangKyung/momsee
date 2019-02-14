package com.example.gjgustjd.servicetest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.List;

import static java.lang.Thread.sleep;

public class Zombie extends Service {

    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        try {
            Toast.makeText(getApplicationContext(), "onStartCommand 호출", Toast.LENGTH_LONG).show();
            Thread.sleep(4000);
            ActivityManager manager = (ActivityManager) getSystemService( Activity.ACTIVITY_SERVICE );
            List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo info=list.get(0);
            if(info.topActivity.getClassName().equals("com.example.test7_20.Lab4Activity")){
            }else {
                startActivity(new Intent(this,MainActivity.class));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"서비스 소멸",Toast.LENGTH_LONG).show();
    }

}
