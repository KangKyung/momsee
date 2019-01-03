package com.example.jiinheo.momsee;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class child_chatting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_info);

        ArrayList<String> MyListView = new ArrayList<String>();
        MyListView.add("강경훈과의 채팅");
        MyListView.add("이세찬과의 채팅");
        MyListView.add("허지인과의 채팅");
        MyListView.add("허현성과의 채팅");

        ArrayAdapter<String> MyArrayAdapter;
        MyArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, MyListView);
        //기본적인 어댑터 형태

        ListView MyList= (ListView)findViewById(R.id.listview1);
        MyList.setAdapter(MyArrayAdapter);
        //리스트 변수와 실제 위젯 주소 연결

        //리스트뷰 속성 없어도 구현가능하니 빼도됨

        MyList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //항목선택

        MyList.setDivider(new ColorDrawable(Color.GRAY));
        //항목 사이 구분선

        MyList.setDividerHeight(10);
        //구분선 높이 지정


        MyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                int pposition = position +1;
                switch(pposition){
                    case 1:
                        break;
                }

            }
        });
    }
}
