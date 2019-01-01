package com.example.jiinheo.momsee;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_child_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.children_info);
        ListView listView;
        ListViewAdapter adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);
        adapter.addItem("강경훈");
        adapter.addItem("이세찬");
        adapter.addItem("허지인");
        adapter.addItem("허현성");




    }
    public class ListViewAdapter extends BaseAdapter{
        private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
        public ListViewAdapter(){}
        @Override
        public int getCount(){
            return listViewItemList.size();
        }
        @Override
        public  View getView(int position,View convertView,ViewGroup parent){
            final int pos = position;
            final Context context = parent.getContext();
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.child_info_item,parent,false);
            }
            TextView childName = (TextView)convertView.findViewById(R.id.childName);

            ListViewItem listViewItem = listViewItemList.get(position);
            childName.setText(listViewItem.getS());
            return convertView;
        }

        @Override
        public long getItemId(int position){
            return position;
        }
        @Override
        public Object getItem(int position){
            return listViewItemList.get(position);
        }
        public void addItem(String Name){
            ListViewItem item = new ListViewItem();
            item.setS(Name);
        }

    }
    public class ListViewItem {
        String s;
        public String getS(){
            return s;
        }
        public void setS(String s){
            this.s = s;
        }
    }
}
