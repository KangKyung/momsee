package com.example.jiinheo.momsee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class mainloginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText Et_Email,Et_password;
    Button Bt_login,Bt_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bt_login = (Button)findViewById(R.id.bt_login);
        Bt_signup = (Button)findViewById(R.id.bt_signup);
        Et_Email = (EditText)findViewById(R.id.et_email);
        Et_password = (EditText)findViewById(R.id.et_password);
        Et_Email.setOnClickListener(this);
        Et_password.setOnClickListener(this);
        Bt_login.setOnClickListener(this);
        Bt_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bt_login:{
                startActivity(new Intent(this,selectActivity.class));
                break;
            }
            case R.id.bt_signup:{
                startActivity(new Intent(this,SignupActivity.class));
                break;
            }
        }
    }
}
