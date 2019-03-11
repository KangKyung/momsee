package com.example.android0211;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android0211.Retrofit.INodeJS;
import com.example.android0211.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.button.MaterialButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class activity_child_info extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MaterialButton addchild;
    MaterialEditText edt_child_email,edt_child_age;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_info);
        ListView listView;
        ListViewAdapter adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        adapter.addItem("강경훈");
        adapter.addItem("이세찬");
        adapter.addItem("허지인");
        adapter.addItem("허현성");

        //init API
        Retrofit retrofit1 = RetrofitClient.getInstance();
        myAPI = retrofit1.create(INodeJS.class);


        edt_child_email=(MaterialEditText)findViewById(R.id.edt_child_email);
        edt_child_age=(MaterialEditText)findViewById(R.id.edt_child_age);
        addchild=(MaterialButton)findViewById(R.id.addchild);

        addchild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.addchild)
                    registerUser_child(edt_child_email.getText().toString(),edt_child_age.getText().toString());
            }
        });



    }

    public void registerUser_child(final String email, final String child_age) {

        final View enter_email_view = LayoutInflater.from(this).inflate(R.layout.activity_add_child,null);

        new MaterialStyledDialog.Builder(this)
                .setTitle("자식 추가")
                .setDescription("One more step!")
                .setCustomView(enter_email_view)
                .setNegativeText("Cancel")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveText("Register")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        MaterialEditText edt_child_name = (MaterialEditText)enter_email_view.findViewById(R.id.edt_child_name);

                        compositeDisposable.add(myAPI.registerUser_child(email,edt_child_name.getText().toString(),child_age)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        Toast.makeText(activity_child_info.this,""+s,Toast.LENGTH_SHORT).show();

                                    }
                                }));

                    }
                }).show();



    }

    public class ListViewAdapter extends BaseAdapter {
        private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

        public ListViewAdapter(){}

        @Override
        public int getCount(){
            return listViewItemList.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            final int pos = position;
            final Context context = parent.getContext();
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_child_info_item,parent,false);
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
            listViewItemList.add(item);
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
