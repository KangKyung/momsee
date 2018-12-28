package com.example.jiinheo.momsee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    EditText Name,password,passwordCheck,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        button = (Button)findViewById(R.id.button_OK);
        button.setOnClickListener(this);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        passwordCheck = (EditText)findViewById(R.id.passwordcheck);
        Name = (EditText)findViewById(R.id.UserName);
    }
    public void onClick(View v){
        if(v.getId() == R.id.button){
            /*데이터 DB로 넘길 부분*/
        }
    }

}
