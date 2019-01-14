package com.example.jiinheo.momsee;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class OnChatting extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    EditText editText;
    Button sendButton;
    String userName;
    ArrayAdapter<String> adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public void onClick(View v) {
        ChatData chatData = new ChatData(userName, editText.getText().toString());
        try {
            databaseReference.child("message").push().setValue(chatData);
            editText.setText("");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_chatting);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.Text);
        sendButton = (Button) findViewById(R.id.Send);
        sendButton.setOnClickListener(this);
        userName = "user" + new Random().nextInt(10000);
        ArrayList<String> MyListView = new ArrayList<String>();
        ArrayAdapter<String> MyArrayAdapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, MyListView);
        ListView MyList= (ListView)findViewById(R.id.listView);
        MyList.setAdapter(adapter);
        MyList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        MyList.setDivider(new ColorDrawable(Color.GRAY));
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        MyList.setDividerHeight(10);
       adapter.registerDataSetObserver(new DataSetObserver() {

            @Override

            public void onChanged() {

                super.onChanged();

                listView.setSelection(adapter.getCount()-1);

            }

        });
        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot,String s) {
                    try {
                        Iterator i = dataSnapshot.getChildren().iterator();
                            String my_value1 =
                                    (String) ((DataSnapshot) i.next()).getValue();
                            String my_value2 =
                                    (String) ((DataSnapshot) i.next()).getValue();
                            ChatData chatData = new ChatData(my_value1, my_value2);
                            adapter.add(chatData.getUserName() + ": " + chatData.getMessage());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }


    public static class ChatData {
        private String userName;
        private String message;
        public ChatData(){

        }
        public ChatData(String userName, String message) {
            this.userName = userName;
            this.message = message;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
