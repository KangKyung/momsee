package com.example.secha.a0114;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by secha on 2019-01-14.
 */

public class MyAdmin extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context,"Device Admin: enabled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Toast.makeText(context,"Device Admin: disabled",Toast.LENGTH_SHORT).show();
    }
}
