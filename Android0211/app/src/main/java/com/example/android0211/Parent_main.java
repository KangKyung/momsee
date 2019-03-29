package com.example.android0211;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

public class Parent_main extends AppCompatActivity {
    ViewPager vp;        //뷰페이저 변수.
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        /*레이아웃 뷰 변수 할당*/
        vp = (ViewPager)findViewById(R.id.vp);
        Button btn_first = (Button)findViewById(R.id.btn_first);
        Button btn_second = (Button)findViewById(R.id.btn_second);
        Button btn_third = (Button)findViewById(R.id.btn_third);

        /*뷰페이저 어댑터할당*/
        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        /*각 버튼에 태그 설정(구분 용도)*/
        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener);
        btn_second.setTag(1);
        btn_third.setOnClickListener(movePageListener);
        btn_third.setTag(2);
        String email = getIntent().getStringExtra("email");

       /*이전 탭 호스트 방식에서는 각 탭에 보여지는 화면들이 액티비티로 취급되어 인텐트로 필요한 메시지를 송수신 가능하였으나
         뷰페이저에서는 각 화면이 Fragment로 들어가므로 액티비티만 받을 수 있는 인텐트를 수신못함,Bundle을 이용하여 메시지를 송수신하여야함 */
      /*  //받았어 이메일을
        String email=getIntent().getStringExtra("email");
        String message = "환영합니다,  " + email + "님!";
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

        //보냈어 이메일을
        Intent intent1 = new Intent(getApplicationContext(),activity_child_info.class);
        intent1.putExtra("email",email);
        startActivity(intent1);


        Intent intent2 = new Intent(getApplicationContext(),Parent_chatting.class);
        intent2.putExtra("email",email);


        Intent intent3 = new Intent(getApplicationContext(),Child_chatting.class);
        intent3.putExtra("email",email);*/




    }
    View.OnClickListener movePageListener = new View.OnClickListener()             //각 버튼에 대한 클릭 리스너
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();                 //버튼을 태그로 구분하여 현 뷰페이저의 화면을 전환한다.
            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter               //뷰페이저에 들어갈 아이템을 제공하는 어댑터.
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            Bundle bundle = new Bundle();
            bundle.putString("email",email);
            switch(position)
            {
                case 0:
                    Fragment info = new activity_child_info();
                    info.setArguments(bundle);
                    return info;          //자식정보 프래그먼트
                case 1:
                    Fragment chatting = new Parent_chatting();
                    chatting.setArguments(bundle);
                    return chatting;              //채팅 프래그먼트.
                case 2:
                    Fragment maps = new Maps();
                    maps.setArguments(bundle);
                    return maps;                       //지도 프래그먼트.
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }
}
