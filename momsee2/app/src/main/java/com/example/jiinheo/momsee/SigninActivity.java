package com.example.jiinheo.momsee;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SigninActivity extends AppCompatActivity {
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

        Bt_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(SigninActivity.this, SignupActivity.class);
                SigninActivity.this.startActivity(registerIntent);
            }
        });

        Bt_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String userEmail = Et_Email.getText().toString();
                final String userPassword = Et_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String userEmail = jsonResponse.getString("userEmail");
                                String userPassword = jsonResponse.getString("userPassword");
                                Intent intent = new Intent(SigninActivity.this, selectActivity.class);    //  이부분하면서 안건데 처음 로그인 할때만 select나오도록 수정해야함!!
                                intent.putExtra("userEmail", userEmail);    //  intent 이것도 데이터 전달하는 대상 클래스를 바꾸는 방향으로 수정하자 ..
                                intent.putExtra("userPassword", userPassword);  //  여기서 주는 인텐트는 나중에 비밀번호 변경시 현재 비밀번호 입력하도록 할때 쓴다!
                                SigninActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SigninActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                SigninRequest signinRequest = new SigninRequest(userEmail, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SigninActivity.this);
                queue.add(signinRequest);
            }
        });
    }
/*
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
    }*/
}
