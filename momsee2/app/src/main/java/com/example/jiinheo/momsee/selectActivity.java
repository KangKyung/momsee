package com.example.jiinheo.momsee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectActivity extends AppCompatActivity implements View.OnClickListener{

    Button Bt_Parent,Bt_Child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Bt_Parent = (Button)findViewById(R.id.bt_parent);
        Bt_Child = (Button)findViewById(R.id.bt_child);
        Bt_Parent.setOnClickListener(this);
        Bt_Child.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.bt_parent:{
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE
                )!= PackageManager.PERMISSION_GRANTED)
                    startActivity(new Intent(this,P_permissionActivity.class));
                else {
                    Intent intent = getIntent();    //  나중에 수정할 부분 ...
                    String userEmail = intent.getStringExtra("userEmail");

                    Intent intent2 = new Intent(selectActivity.this, Parent_main.class);
                    intent2.putExtra("userEmail", userEmail);

                    selectActivity.this.startActivity(intent2);
                }
                break;
            }
            case R.id.bt_child:{
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE
                )!= PackageManager.PERMISSION_GRANTED)
                    startActivity(new Intent(this,C_permissionActivity.class));
                else
                    startActivity(new Intent(this, Child_name.class));
                break;
            }
        }
    }
}
