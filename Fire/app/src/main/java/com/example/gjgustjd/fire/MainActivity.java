package com.example.gjgustjd.fire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listView;
    EditText editText;
    Button sendButton;
    String userName;
    ArrayAdapter<String> adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    public void onClick(View v) {
        ChatData chatData = new ChatData(userName, editText.getText().toString());
        databaseReference.child("message").push().setValue(chatData);
        editText.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.Text);
        sendButton = (Button) findViewById(R.id.Send);
        sendButton.setOnClickListener(this);
        userName = "user" + new Random().nextInt(10000);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);
        databaseReference.child("message").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    Iterator i = dataSnapshot.getChildren().iterator();
                        String my_value1 =
                                (String) ((DataSnapshot) i.next()).getValue();
                       String my_value2 =
                                (String) ((DataSnapshot) i.next()).getValue();
                    ChatData chatData = new ChatData(my_value1,my_value2);
                    adapter.add(chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.
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


    public class ChatData {
        private String userName;
        private String message;

        public ChatData() { }

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
