package com.example.android0211;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Child_name extends AppCompatActivity {
    Button Bt_Child_name;
    EditText Edit_Child_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_name);
        Bt_Child_name = (Button) findViewById(R.id.bt_child_name);
        Edit_Child_name = (EditText) findViewById(R.id.edit_child_name);

        Bt_Child_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Child_main.class);
                startActivity(intent);
            }
        });
    }
}
