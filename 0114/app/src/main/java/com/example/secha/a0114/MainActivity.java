package com.example.secha.a0114;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button lock, disable, enable;
    public static final int RESULT_ENABLE=11;
    private DevicePolicyManager devicePolicyManager;
    private ActivityManager activityManager;
    private ComponentName componentName;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        componentName= new ComponentName(this, MyAdmin.class);

        lock = (Button)findViewById(R.id.lock);
        enable = (Button)findViewById(R.id.enableBtn);
        disable = (Button)findViewById(R.id.disableBtn);
        lock.setOnClickListener(this);
        enable.setOnClickListener(this);
        disable.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isActive = devicePolicyManager.isAdminActive(componentName);
        disable.setVisibility(isActive ? View.VISIBLE : View.GONE);
        enable.setVisibility(isActive ? View.GONE : View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        if(v==lock){
            boolean active = devicePolicyManager.isAdminActive(componentName);

            if(active){
                devicePolicyManager.lockNow();
            }else{
                Toast.makeText(this,"yout need to enable the Admin Device",Toast.LENGTH_SHORT).show();
            }
        }else if(v==enable){
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Additional text explaining why we need this permission");
            startActivityForResult(intent,RESULT_ENABLE);
        }else if(v==disable){
            devicePolicyManager.removeActiveAdmin(componentName);
            disable.setVisibility(v.GONE);
            enable.setVisibility(v.VISIBLE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case RESULT_ENABLE:
                if(resultCode== Activity.RESULT_OK){
                    Toast.makeText(MainActivity.this,"you have enabled the Admin Device features",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"Problem to enabled the Admin Device features",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
