package com.example.android0211.Chatting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android0211.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ChatBoxActivity extends AppCompatActivity {
    public RecyclerView myRecylerView ;
    public List<Message> MessageList ;
    public ChatBoxAdapter chatBoxAdapter;
    public EditText messagetxt ;
    public Button send ;

    //declare socket object
    private Socket socket;
    //디비생성
    Realm mRealm;

    public String Nickname ;

    String recordName, recordMessage; // 저장할라고.. ㅎㅎ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        messagetxt = findViewById(R.id.message);
        send = findViewById(R.id.send);
        // get the nickame of the user
        Nickname = (String) getIntent().getExtras().getString(Parent_chatting.NICKNAME);

        //디비 생성
        Realm.init(getApplicationContext());

        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .name("chatttRecord.db") // 이거 나중에 변경하자 변경변경!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getDefaultInstance();

        //connect you socket client to the server
        try {
            socket = IO.socket("http://10.20.15.3:3000");
            socket.connect();
            socket.emit("join", Nickname);
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }
        //setting up recyler
        MessageList = new ArrayList<>();
        myRecylerView = findViewById(R.id.messagelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());



        // message send action
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve the nickname and the message content and fire the event messagedetection
                if (!messagetxt.getText().toString().isEmpty()) {
                    socket.emit("messagedetection", Nickname, messagetxt.getText().toString());

                    messagetxt.setText(" ");
                }


            }
        });

        //implementing socket listeners
        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event

                            String nickname = data.getString("senderNickname");
                            String message = data.getString("message");

                            recordName = nickname;
                            recordMessage = message;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        addMessage(); // 위치선정 좋았음 ㅎㅎ
                    }
                });
            }
        });

        // 처음화면 출력
        Realm realm = null;

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) { } // 처음화면에 출력하는 거여서, 함수가 비어있음.
        });

        mRealm = realm; // 하 요놈이 문제였넹 ㅋ

        // 화면에 출력
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<Message> results = realm.where(Message.class).findAll();
                for (Message message : results) {
//                    textView.append(message.getNickname() + " : " + message.getMessage() + "\n");

                    //add the message to the messageList
                    MessageList.add(message);

                    // add the new updated list to the dapter
                    chatBoxAdapter = new ChatBoxAdapter(MessageList);

                    // notify the adapter to update the recycler view

                    chatBoxAdapter.notifyDataSetChanged();

                    //set the adapter for the recycler view

                    myRecylerView.setAdapter(chatBoxAdapter);
                }
            }
        });

    }

    public void addMessage() { // 이놈 메시지 받았을때랑 보낼때 호출해야함 !!! 부터 하장 ㅋ

        Realm realm = null;

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (!Nickname.isEmpty()) {
                    Message message = new Message();
                    message.setNickname(recordName);
                    message.setMessage(recordMessage);

                    realm.copyToRealm(message);
                }
            }
        });

        mRealm = realm; // 하 요놈이 문제였넹 ㅋ

        // 화면에 출력
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<Message> results = realm.where(Message.class).findAll();
                for (Message message : results) {
//                    textView.append(message.getNickname() + " : " + message.getMessage() + "\n");


                    //add the message to the messageList
                    MessageList.add(message);

                    // add the new updated list to the dapter
                    chatBoxAdapter = new ChatBoxAdapter(MessageList);

                    // notify the adapter to update the recycler view

                    chatBoxAdapter.notifyDataSetChanged();

                    //set the adapter for the recycler view

                    myRecylerView.setAdapter(chatBoxAdapter);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        socket.disconnect();
    }
}
