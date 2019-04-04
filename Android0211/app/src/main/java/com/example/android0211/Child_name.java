package com.example.android0211;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android0211.FCMUse.OverlayService;
import com.example.android0211.FCMUse.PolicyManager;
import com.example.android0211.Retrofit.INodeJS;
import com.example.android0211.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class Child_name extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Button btn_child_login;
    EditText edt_child_email, edt_child_name;

    private PolicyManager policyManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_name);

        btn_child_login = findViewById(R.id.btn_child_login);

        edt_child_email = findViewById(R.id.edt_child_email);
        edt_child_name = findViewById(R.id.edt_child_name);

        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        startActivity(intent);
        policyManager = new PolicyManager(this);

        if (!policyManager.isAdminActive()) {
            Intent activateDeviceAdmin = new Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            activateDeviceAdmin.putExtra(
                    DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    policyManager.getAdminComponent());
            activateDeviceAdmin
                    .putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "After activating admin, you will be able to block application uninstallation.");
            startActivityForResult(activateDeviceAdmin,
                    PolicyManager.DPM_ACTIVATION_REQUEST_CODE);
        }



        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        btn_child_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser_child(edt_child_email.getText().toString(), edt_child_name.getText().toString());
            //    startService(new Intent(getApplicationContext(), OverlayService.class));

            }
        });



    }

    private void loginUser_child(String email, String child_name) {
        compositeDisposable.add(myAPI.loginUser_child(email, child_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s.contains("child_name")) {
                        Toast.makeText(Child_name.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Child_main.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(Child_name.this, "" + s, Toast.LENGTH_SHORT).show();
                }));

    }
}
