package com.example.jiinheo.momsee;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText Chkpassword;
    EditText ChkpasswordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Chkpassword = (EditText) findViewById(R.id.password);
        ChkpasswordConfirm = (EditText)findViewById(R.id.passwordConfirm);

/*
        Chkpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = Chkpassword.getText().toString();
                String confirm = ChkpasswordConfirm.getText().toString();

                if(!password.equals(confirm)){
                    Toast.makeText(mainloginActivity.this,"비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                }
                }
                   /* Chkpassword.setBackgroundColor(Color.GREEN);
                    ChkpasswordConfirm.setBackgroundColor(Color.GREEN);
                }
                else {
                    Chkpassword.setBackgroundColor(Color.RED);
                    ChkpasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/
    }
}
