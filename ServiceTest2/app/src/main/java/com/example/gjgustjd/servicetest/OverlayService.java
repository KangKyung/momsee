package com.example.gjgustjd.servicetest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.content.ContentValues.TAG;

public class OverlayService extends Service {
    private TextView mPopupView;							//�׻� ���̰� �� ��
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;			//������ �Ŵ���
    private Button momsee,katalk,everytime;
    LayoutInflater inflater;
    ConstraintLayout layout;
    View temp;
    private String getForegroundPackageName() {

            String packageName = "";
            try{
            UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            final long endTime = System.currentTimeMillis();
            final long beginTime = endTime - 100000;
            final UsageEvents usageEvents = usageStatsManager.queryEvents(beginTime, endTime);
            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    packageName = event.getPackageName();
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return packageName;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (ConstraintLayout)inflater.inflate(R.layout.activity_main, null);
        momsee = (Button)layout.findViewById(R.id.momsee);
        momsee.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(v.getId() == R.id.momsee) {
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.jiinheo.momsee");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        katalk = (Button)layout.findViewById(R.id.katalk);
        katalk.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(v.getId() == R.id.katalk) {
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.kakao.talk");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        everytime = (Button)layout.findViewById(R.id.everytime);
        everytime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(v.getId() == R.id.everytime ) {
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.everytime.v2");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY ,					//�׻� �� ������ �ְ�. status bar �ؿ� ����. ��ġ �̺�Ʈ ���� �� ����.
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        try {

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        try {
            Toast.makeText(getApplicationContext(), "onStartCommand 호출", Toast.LENGTH_LONG).show();
            final Handler handler1 = new Handler();
                new Thread(new Runnable(){
                    public void run(){
                        while(true) {
                            int i = 0;
                            try {
                                if (getForegroundPackageName().equals("com.example.jiinheo.momsee")||getForegroundPackageName().equals("com.kakao.talk")||getForegroundPackageName().equals("com.everytime.v2")) {
                                    if(temp!= null) {
                                        handler1.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mWindowManager.removeViewImmediate(layout);
                                            }
                                        });
                                    }
                                    temp = null;
                                } else {
                                    if (temp == null)
                                        try {
                                            handler1.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mWindowManager.addView(layout, mParams);
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    temp = layout;
                                }
                                i++;
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"서비스 종료",Toast.LENGTH_SHORT);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}