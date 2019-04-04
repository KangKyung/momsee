package com.example.android0211.FCMUse;

import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android0211.R;

public class OverlayService extends Service {
    private TextView mPopupView;                     //�׻� ���̰� �� ��
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;         //������ �Ŵ���
    private Button momsee, katalk, everytime, unlock, stop;
    private BlockThread block;
    final Handler handler1 = new Handler();
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    LayoutInflater inflater;
    ConstraintLayout layout;
    View temp;

    private String getForegroundPackageName() {

        String packageName = "";
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setting = getSharedPreferences("Set", 0);
        editor = setting.edit();
        block = new BlockThread();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (ConstraintLayout) inflater.inflate(R.layout.activity_overlay, null);
        momsee = layout.findViewById(R.id.momsee);
        momsee.setOnClickListener(v -> {
            if (v.getId() == R.id.momsee) {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.jiinheo.momsee");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        katalk = layout.findViewById(R.id.katalk);
        katalk.setOnClickListener(v -> {
            if (v.getId() == R.id.katalk) {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.kakao.talk");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        everytime = layout.findViewById(R.id.everytime);
        everytime.setOnClickListener(v -> {
            if (v.getId() == R.id.everytime) {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.everytime.v2");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        unlock = layout.findViewById(R.id.unlock);
        unlock.setOnClickListener(v -> {
            if (v.getId() == R.id.unlock) {
                long Point = System.currentTimeMillis();
                int Duration = 180000;
                editor.putLong("Point", Point);
                editor.putInt("Duration", Duration);
                editor.commit();
                block.SleepForSecond(Duration);
            }
        });
        stop = layout.findViewById(R.id.stop);
        stop.setOnClickListener(v -> {
            if (v.getId() == R.id.stop) {
                stopService(new Intent(getApplicationContext(), OverlayService.class));
            }
        });
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,               //�׻� �� ������ �ְ�. status bar �ؿ� ����. ��ġ �̺�Ʈ ���� �� ����.
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Toast.makeText(getApplicationContext(), "onStartCommand 호출", Toast.LENGTH_LONG).show();
            block.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        block.Stop();//이거 추가해서 언락이됨
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "서비스 종료", Toast.LENGTH_SHORT);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class BlockThread extends Thread {
        boolean flag = true;
        int Second = 0;

        public void run() {
            while (flag) {
                int i = 0;
                /*long Point = setting.getLong("Point", 0);
                int Duration = setting.getInt("Duration", 0);
                Second = ((int) Point + Duration) - (int) System.currentTimeMillis();*/
                String Name = getForegroundPackageName();
                try {
                    /*if (Second > 0) {*//*
                        if (temp != null) {
                            handler1.post(() -> mWindowManager.removeViewImmediate(layout));
                        }
                       *//* temp = null;
                        Thread.sleep(Second);
                        Second = 0;
                        editor.putLong("Point", 0);
                        editor.putInt("Duration", 0);
                    }*/
                    if (Name.equals("com.example.jiinheo.momsee") || Name.equals("com.kakao.talk") || Name.equals("com.everytime.v2")) {
                        if (temp != null) {
                            handler1.post(() -> mWindowManager.removeViewImmediate(layout));
                        }
                        temp = null;
                    } else {
                        if (temp == null)
                            try {
                                handler1.post(() -> {
                                    try {
                                        mWindowManager.addView(layout, mParams);
                                        Log.i("씨발거","addview");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        temp = layout;
                    }
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            handler1.post(() -> mWindowManager.removeViewImmediate(layout));
        }

        public void Stop() {
            this.flag = false;
        }

        public void SleepForSecond(int Second) {
            this.Second = Second;
        }
    }

}
