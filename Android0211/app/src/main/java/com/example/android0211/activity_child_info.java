package com.example.android0211;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

public class activity_child_info extends Fragment {              //프래그먼트를 상속.
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MaterialButton addchild;
    MaterialEditText edt_child_name,edt_child_age;
    Button lock_unlock;
    public  activity_child_info(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)           //프래그먼트가 레이아웃을 적용하는 시점
    {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_children_info, container, false);   //레이아웃 xml파일을 변수화한다.
        try {
            ListView listView;
            ListViewAdapter adapter = new ListViewAdapter();
            listView = (ListView)layout.findViewById(R.id.listview1);  //findViewById는 프래그먼트에서 바로 사용불가능하므로 layout에 대입한 레이아웃 파일에 접근하여 대입한다.
            listView.setAdapter(adapter);

            adapter.addItem("강경훈");
            adapter.addItem("이세찬");
            adapter.addItem("허지인");
            adapter.addItem("허현성");

            //init API
            Retrofit retrofit1 = RetrofitClient.getInstance();
            myAPI = retrofit1.create(INodeJS.class);

            lock_unlock = (Button) layout.findViewById(R.id.lock_unlock);
            edt_child_name = (MaterialEditText) layout.findViewById(R.id.edt_child_name);
            edt_child_age = (MaterialEditText) layout.findViewById(R.id.edt_child_age);
            addchild = (MaterialButton) layout.findViewById(R.id.addchild);

            //String email = getIntent().getStringExtra("email");//이메일받은것시발              //이전 액티비티에서 넘어온 인텐트 코드,번들로 대체해야 함.

            addchild.setOnClickListener(v -> {
                if (v.getId() == R.id.addchild)
                    registerUser_child(edt_child_name.getText().toString(), edt_child_age.getText().toString());
            });

            lock_unlock.setOnClickListener(v -> {
                //lock_unlock(email);   //번들처리할 것.
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        return layout;
    }
    /*나머지 함수는 그대로 유지함.*/
    private void lock_unlock(String email) {
        compositeDisposable.add(myAPI.lock_unlock(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if(s.contains("1")){
                        Toast.makeText(getContext(),"UNLOCK",Toast.LENGTH_SHORT).show();


                    }
                    else
                        Toast.makeText(getContext(),"LOCK",Toast.LENGTH_SHORT).show();

                })
        );
    }

    public void registerUser_child(final String name, final String child_age) {

        final View enter_email_view = LayoutInflater.from(getContext()).inflate(R.layout.activity_add_child,null);

        new MaterialStyledDialog.Builder(getContext())
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
                            .subscribe(s -> Toast.makeText(getContext(),""+s,Toast.LENGTH_SHORT).show()));

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
