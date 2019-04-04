package com.example.android0211.Chatting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android0211.Parent_chatting;
import com.example.android0211.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChatBoxActivity extends AppCompatActivity {
    public RecyclerView myRecylerView ;
    public List<Message> MessageList ;
    public ChatBoxAdapter chatBoxAdapter;
    public EditText messagetxt ;
    public Button send ;
    //declare socket object
    private Socket socket;

    public String Nickname ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        messagetxt = findViewById(R.id.message);
        send = findViewById(R.id.send);
        // get the nickame of the user
        Nickname= getIntent().getExtras().getString(Parent_chatting.NICKNAME);
        //connect you socket client to the server
        try {
            socket = IO.socket("http://10.20.12.148:3001");
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
        send.setOnClickListener(v -> {
            //retrieve the nickname and the message content and fire the event messagedetection
            if(!messagetxt.getText().toString().isEmpty()){
                socket.emit("messagedetection",Nickname,messagetxt.getText().toString());

                messagetxt.setText(" ");
            }
        });

        //implementing socket listeners
        socket.on("userjoinedthechat", args -> runOnUiThread(() -> {
            String data = (String) args[0];

            Toast.makeText(ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();

        }));
        socket.on("userdisconnect", args -> runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String data = (String) args[0];

                Toast.makeText(ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();

            }
        }));
        socket.on("message", args -> runOnUiThread(() -> {
            JSONObject data = (JSONObject) args[0];
            try {
                //extract data from fired event

                String nickname = data.getString("senderNickname"); //  이름 가져오기
                String message = data.getString("message");     //  메시지 가져오기

                // make instance of message

                Message m = new Message(nickname,message);


                //add the message to the messageList

                MessageList.add(m);

                // add the new updated list to the dapter
                chatBoxAdapter = new ChatBoxAdapter(MessageList);

                // notify the adapter to update the recycler view

                chatBoxAdapter.notifyDataSetChanged();

                //set the adapter for the recycler view

                myRecylerView.setAdapter(chatBoxAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        socket.disconnect();
    }
}
