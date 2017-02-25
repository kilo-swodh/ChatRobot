package com.example.a45556.chatrobot.net;

import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a45556.chatrobot.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpGetDataListener{

    private List lists;
    private ListView chatListView;
    private EditText inputEditText;
    private Button sendButton;
    private String contentString;
    private ChatAdapter adapter;
    private String[] welcomeArray;

    private double currentTime,oldTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        if (savedInstanceState != null){
            lists = savedInstanceState.getParcelableArrayList("MSG");
            for (Object bean:lists){
                //to do:read and show ChatBean
            }
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getRandomWelcomeTips();
    }

    @Override
    public void getDataUrl(String data) {
        Gson gson = new Gson();
        ChatBean chatBean = gson.fromJson(data,ChatBean.class);
        if (chatBean == null){
            Toast.makeText(MainActivity.this,"抱歉,网络出现异常",Toast.LENGTH_SHORT).show();
            return;
        }
        chatBean.setFlag(ChatBean.TYPE_RECEIVER);
        chatBean.setTime(getTime());
        lists.add(chatBean);
        adapter.notifyDataSetChanged();
    }

    private void initView(){
        if (lists == null)
            lists = new ArrayList<ChatBean>();
        chatListView = (ListView)findViewById(R.id.lv_chat);
        inputEditText = (EditText)findViewById(R.id.ev_input);
        sendButton = (Button)findViewById(R.id.btn_send);

        adapter = new ChatAdapter(MainActivity.this,R.layout.msg_item,lists);
        chatListView.setAdapter(adapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentString = inputEditText.getText().toString();
                if (contentString.equals("")){
                    return;
                }
                ChatBean chatBean = new ChatBean();
                chatBean.setFlag(ChatBean.TYPE_SEND);
                chatBean.setText(contentString);
                chatBean.setCode(666);
                chatBean.setTime(getTime());
                inputEditText.setText("");
                String noSpaceString = contentString.replace(" ","");
                String noEnterString = noSpaceString.replace("\n","");
                new HttpData("http://www.tuling123.com/openapi/api?key=f151d40b156d427ca9bf9a4dba1eadb3&info="+
                        noEnterString,MainActivity.this).execute();
                lists.add(chatBean);
                if (lists.size() >50){
                    lists.remove(0);
                    lists.remove(0);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getRandomWelcomeTips(){
        String welcomeString;
        welcomeArray = this.getResources().getStringArray(R.array.welcome_tips);
        welcomeString = welcomeArray[(int)(Math.random()*welcomeArray.length)];
        ChatBean chatBean = new ChatBean();
        chatBean.setText(welcomeString);
        chatBean.setFlag(ChatBean.TYPE_RECEIVER);
        chatBean.setTime(getTime());
        lists.add(chatBean);
        adapter.notifyDataSetChanged();
    }

    private String getTime(){
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate =  new Date();
        String timeString = format.format(curDate);
        if (currentTime - oldTime > 5*60*1000){
            oldTime = currentTime;
            return timeString;
        }else{
            return "";
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelableArrayList("MSG",(ArrayList<ChatBean>)lists);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
