package com.example.android0211;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android0211.Retrofit.INodeJS;
import com.example.android0211.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Parent_chatting extends Fragment {             //프래그먼트 상속

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Button btn;

    public static String NICKNAME ;

    public  Parent_chatting(){}

    /*이전 액티비티 onCreate()에 있던 내용을 그대로 가져다 씀.*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.activity_parent_chatting, container, false);
        btn = (Button)layout.findViewById(R.id.enterchat);
        //  여기에 사용자 이름을 출력 시키기 - 부모, 자녀

        //init API
        Retrofit retrofit1 = RetrofitClient.getInstance();
        myAPI = retrofit1.create(INodeJS.class);

        String email = getArguments().getString("email")   ;    //인텐트 였던 부분 번들처리됨.

        //채팅시작 버튼
        btn.setOnClickListener(v -> {
            //if the nickname is not empty go to chatbox activity and add the nickname to the intent extra
            //extract_parent_name(email);
            Intent i  = new Intent(getContext(),ChatBoxActivity.class);
            startActivity(i);
        });

        return layout;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //call UI component  by id
    }
    /*이전 함수 그대로*/
    private void extract_parent_name(String email) {
        compositeDisposable.add(myAPI.extract_parent_name(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Toast.makeText(getContext(),""+s,Toast.LENGTH_SHORT).show();
                    NICKNAME=s;
                })
        );
    }
}
