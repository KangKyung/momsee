package com.example.android0211.Chatting;

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

import com.example.android0211.Chatting.ChatBoxActivity;
import com.example.android0211.R;
import com.example.android0211.Retrofit.INodeJS;
import com.example.android0211.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Parent_chatting extends Fragment {             //프래그먼트 상속

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
     Button btn;

    public static String NICKNAME = "usernickname";

    public  Parent_chatting(){}

    /*이전 액티비티 onCreate()에 있던 내용을 그대로 가져다 씀.*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_parent_chatting, container, false);
        try{
            btn = (Button)rootView.findViewById(R.id.enterchat);
            //  여기에 사용자 이름을 출력 시키기 - 부모, 자녀

            //init API
            Retrofit retrofit1 = RetrofitClient.getInstance();
            myAPI = retrofit1.create(INodeJS.class);

            String email = getArguments().getString("email")   ;    //인텐트 였던 부분 번들처리됨.

            String nickname = "parent"; //  db가져오면 지워라

            //채팅시작 버튼
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i  = new Intent(getActivity(),ChatBoxActivity.class);
                    i.putExtra(NICKNAME,nickname);//  db가져오면 수정해라

                    startActivity(i);

                }
                    //if the nickname is not empty go to chatbox activity and add the nickname to the intent extra
                    //extract_parent_name(email);

            });


        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return rootView;
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
