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
    MaterialEditText edt_child_name,edt_child_age;
    Button lock_unlock;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_info);
        ListView listView;
        ListViewAdapter adapter = new ListViewAdapter();
        listView = findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        adapter.addItem("강경훈");
        adapter.addItem("이세찬");
        adapter.addItem("허지인");
        adapter.addItem("허현성");

        //init API
        Retrofit retrofit1 = RetrofitClient.getInstance();
        myAPI = retrofit1.create(INodeJS.class);

        lock_unlock=findViewById(R.id.lock_unlock);
        edt_child_name= findViewById(R.id.edt_child_name);
        edt_child_age= findViewById(R.id.edt_child_age);
        addchild= findViewById(R.id.addchild);

        String email = getIntent().getStringExtra("email");//이메일받은것시발

        addchild.setOnClickListener(v -> {
            if(v.getId() == R.id.addchild)
                registerUser_child(edt_child_name.getText().toString(),edt_child_age.getText().toString());
        });

        lock_unlock.setOnClickListener(v -> {


            lock_unlock(email);

        });




    }
    private void lock_unlock(String email) {
        compositeDisposable.add(myAPI.lock_unlock(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if(s.contains("1")){
                        Toast.makeText(activity_child_info.this,"UNLOCK",Toast.LENGTH_SHORT).show();


                    }
                    else
                        Toast.makeText(activity_child_info.this,"LOCK",Toast.LENGTH_SHORT).show();

                })
        );
    }

    public void registerUser_child(final String name, final String child_age) {

        final View enter_email_view = LayoutInflater.from(this).inflate(R.layout.activity_add_child,null);

        new MaterialStyledDialog.Builder(this)
                .setTitle("자식 추가")
                .setDescription("자녀 분 등록")
                .setCustomView(enter_email_view)
                .setIcon(R.drawable.ic_user)
                .setNegativeText("Cancel")
                .onNegative((dialog, which) -> dialog.dismiss())
                .setPositiveText("Register")
                .onPositive((dialog, which) -> {

                    MaterialEditText edt_child_email = enter_email_view.findViewById(R.id.edt_child_email);

                    compositeDisposable.add(myAPI.registerUser_child(name,edt_child_email.getText().toString(),child_age)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> Toast.makeText(activity_child_info.this,""+s,Toast.LENGTH_SHORT).show()));

                }).show();



    }

    public class ListViewAdapter extends BaseAdapter {
        private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

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
