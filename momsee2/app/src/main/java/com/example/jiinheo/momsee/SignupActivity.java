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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class SignupActivity extends AppCompatActivity{
    Button button;
    EditText Name,password,passwordCheck,email;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        passwordCheck = (EditText)findViewById(R.id.passwordcheck);
        Name = (EditText)findViewById(R.id.UserName);
        button = (Button)findViewById(R.id.button_OK);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String pwCheck = passwordCheck.getText().toString();    //  이부분 이후에 구현 !!
                String userName = Name.getText().toString();
                //이전 데이터베이스 구현
                /*
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                                SignupActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SignupRequest registerRequest = new SignupRequest(userEmail, userPassword, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                queue.add(registerRequest);
                */
                //이전 데이터베이스 구현
                //신 데이터베이스 구현
                {
                    try {
                        Account account = new Account(email.getText().toString(),password.getText().toString(), getMACAddress("wlan0"),"P","","");
                        ChatData chatData = new ChatData(email.getText().toString(), password.getText().toString());
                        databaseReference.child("message").push().setValue(account);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions

        return "";
    }
    public class Account{
        private String UserEmail;
        private String Password;
        private String MacAddress;
        private String Position;
        private String ParentEmail;
        private String UserName;
        public Account(String UserEmail,String Password,String MacAddress,String Position,String ParentEmail,String UserName){
            this.UserEmail = UserEmail;
            this.Password = Password;
            this.MacAddress = MacAddress;
            this.Position = Position;
            this.ParentEmail = ParentEmail;
            this.UserName = UserName;
        }
        public String getUserEmail(){return UserEmail;}
        public String getPassword(){return Password;}
        public String getMacAddress(){return MacAddress;}
        public String getPosition(){return Position;}
        public String getParentEmail(){return ParentEmail;}
        public String getUserName(){return UserName;}
    }
    public class ChatData {
        private String userName;
        private String message;
        public ChatData(){

        }
        public ChatData(String userName, String message) {
            this.userName = userName;
            this.message = message;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}