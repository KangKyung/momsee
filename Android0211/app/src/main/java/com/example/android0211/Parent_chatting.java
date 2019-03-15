package com.example.android0211;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android0211.Retrofit.INodeJS;
import com.example.android0211.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Parent_chatting extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Button btn;

    public static String NICKNAME ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_chatting);
        //call UI component  by id
        btn = findViewById(R.id.enterchat);
         //  여기에 사용자 이름을 출력 시키기 - 부모, 자녀

        //init API
        Retrofit retrofit1 = RetrofitClient.getInstance();
        myAPI = retrofit1.create(INodeJS.class);

        String email = getIntent().getStringExtra("email");//이메일받은것

        //채팅시작 버튼
        btn.setOnClickListener(v -> {
            //if the nickname is not empty go to chatbox activity and add the nickname to the intent extra
            extract_parent_name(email);
            Intent i  = new Intent(Parent_chatting.this,ChatBoxActivity.class);
            startActivity(i);
        });

    }
    private void extract_parent_name(String email) {
        compositeDisposable.add(myAPI.extract_parent_name(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Toast.makeText(Parent_chatting.this,""+s,Toast.LENGTH_SHORT).show();
                    NICKNAME=s;
                })
        );
    }
}
