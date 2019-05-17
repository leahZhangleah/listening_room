package com.example.listeningroom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView date_tv,evaluation_room_label,listening_room_label,intelligent_room_label;
    private RecyclerView evaluation_waiting_lv,listening_waiting_lv,intelligent_waiting_lv;
    private VerticalScrollLayout evaluation_passed_ls,listening_passed_ls,intelligent_passed_ls;
    private ChildAdapter evaluation_waiting_adapter,listening_waiting_adapter,intelligent_waiting_adapter;
    private PassedAdapter evaluation_passed_adapter,listening_passed_adapter,intelligent_passed_adapter;
    private TextToSpeech tts;
    private String IP_ADDRESS= "";
    private int PORT=0;
    List<Child> waitingList;
    ArrayList<Child> passedList;
    private boolean isTTSInitialised=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initPermission();
        initViews();
        initTTS();
        //connectTcpAndGetData();
        testWithFakeData();
    }

    private void testWithFakeData(){

        waitingList.add(new Child(1,"小朋友1","测量"));
        waitingList.add(new Child(2,"小朋友2","等待"));
        waitingList.add(new Child(3,"小朋友3","等待"));
        waitingList.add(new Child(4,"小朋友4","等待"));
        waitingList.add(new Child(5,"小朋友5","等待"));
        waitingList.add(new Child(6,"小朋友6","等待"));

        passedList.add(new Child(1,"小朋友1","过号"));
        passedList.add(new Child(2,"小朋友2","过号"));
        passedList.add(new Child(3,"小朋友3","过号"));
        passedList.add(new Child(4,"小朋友4","过号"));
        passedList.add(new Child(5,"小朋友5","过号"));
        passedList.add(new Child(6,"小朋友6","过号"));

        evaluation_waiting_adapter.setChildList(waitingList);
        listening_waiting_adapter.setChildList(waitingList);
        intelligent_waiting_adapter.setChildList(waitingList);

        evaluation_passed_adapter.setChildList(passedList);
        listening_passed_adapter.setChildList(passedList);
        intelligent_passed_adapter.setChildList(passedList);

        //todo:call below code in none oncreate method
       /* Child child = waitingList.get(0);
        new VoiceThread(child).start();*/

    }

    private class VoiceThread extends Thread{
        private Child child;

        public VoiceThread(Child child) {
            this.child = child;
        }

        @Override
        public void run() {//control how many times to play this string
            if(isTTSInitialised){
                String textToRead = "请"+child.number+"号"+child.name+"测量室测量";
                for(int i=0;i<2;i++) {
                    tts.speak(textToRead+",", TextToSpeech.QUEUE_ADD, null);
                }
            }
        }
    }



    /*private void connectTcpAndGetData() {//链接socket，获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String line = null;
                    try {
                        Socket socket = new Socket(IP_ADDRESS, PORT);
                        InputStream in = socket.getInputStream();
                        BufferedReader bufr = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        while ((line = bufr.readLine()) != null) {
                            ConsultingRecord[] cs = new Gson().fromJson(line, ConsultingRecord[].class);
                            list = Arrays.asList(cs);
                            Message msg = Message.obtain();
                            msg.obj = list;
                            msg.what = 0x123;
                            handler.sendMessage(msg);

                            for (int i = 0; i < list.size(); i++) {
                                CallNum  callNum = new CallNum();
                                String patientName = cs[i].getBrxm();
                                String roomNum = cs[i].getFjmc();
                                String num = cs[i].getPdhm();
                                callNum.setPatientName(patientName);
                                callNum.setRoomNum(roomNum);
                                callNum.setNum(num + "号");

                                BuildPlayStr buildPlayStr = new BuildPlayStr(cs, callNum, i, patientName, roomNum, num).invoke();
                                num = buildPlayStr.getNum();
                                Integer playCnt = buildPlayStr.getPlayCnt();
                                String playStr = buildPlayStr.getPlayStr();



                                if ((cs[i].getYsgy() != null && !cs[i].getYsgy().equals("过号")) &&
                                        (patientName != null && !patientName.equals("")) && (num != null && !num.equals(""))) {
                                    if (playCnt < 2) {
                                        App.patients.put(callNum.getPatientName(), callNum);
                                        Message voice = Message.obtain();

                                        PlayStrModel playStrModel = new PlayStrModel();
                                        playStrModel.setPatientNam(callNum.getPatientName());

                                        String tPlayStr =playStr.substring(0,playStr.indexOf("||"));

                                        playStrModel.setPlayStr(tPlayStr);
                                        voice.obj = playStrModel;
                                        voice.what = 0x124;
                                        voiceHandle.sendMessage(voice);
                                        hasPlayMap.put(playStr, playCnt += 1);
                                    }
                                }
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }*/

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    //todo, set list for different adapters
                    //adapter.setData((List<ConsultingRecord>) msg.obj);
                    break;
            }
        }
    };

    private void initViews() {
        date_tv = findViewById(R.id.date_tv);
        evaluation_room_label = findViewById(R.id.evaluation_room_label);
        listening_room_label = findViewById(R.id.listening_room_label);
        intelligent_room_label = findViewById(R.id.intelligent_room_label);

        evaluation_passed_ls = findViewById(R.id.evaluation_room_passed_list);
        listening_passed_ls = findViewById(R.id.listening_room_passed_list);
        intelligent_passed_ls = findViewById(R.id.intelligent_room_passed_list);

        evaluation_waiting_lv = findViewById(R.id.evaluation_room_waiting_list);
        listening_waiting_lv = findViewById(R.id.listening_room_waiting_list);
        intelligent_waiting_lv = findViewById(R.id.intelligent_room_waiting_list);

        //todo assign value to adapter after
        waitingList = new ArrayList();
        evaluation_waiting_adapter = new ChildAdapter(waitingList,this);
        listening_waiting_adapter=new ChildAdapter(waitingList,this);
        intelligent_waiting_adapter=new ChildAdapter(waitingList,this);


        passedList = new ArrayList<>();
        evaluation_passed_adapter = new PassedAdapter();
        listening_passed_adapter = new PassedAdapter();
        intelligent_passed_adapter = new PassedAdapter();

        //set layout manager
        LinearLayoutManager evaluation_layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager listening_layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager intelligent_layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        evaluation_waiting_lv.setLayoutManager(evaluation_layoutManager);
        listening_waiting_lv.setLayoutManager(listening_layoutManager);
        intelligent_waiting_lv.setLayoutManager(intelligent_layoutManager);


        //todo populate data
        //set adapter to recyclerview
        evaluation_waiting_lv.setAdapter(evaluation_waiting_adapter);
        listening_waiting_lv.setAdapter(listening_waiting_adapter);
        intelligent_waiting_lv.setAdapter(intelligent_waiting_adapter);

        evaluation_passed_ls.setAdapter(evaluation_passed_adapter);
        listening_passed_ls.setAdapter(listening_passed_adapter);
        intelligent_passed_ls.setAdapter(intelligent_passed_adapter);
    }

    private void initTTS() {
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == tts.SUCCESS) {
                    isTTSInitialised = true;
                    int result = tts.setLanguage(Locale.CHINA);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(MainActivity.this, "TTS暂时不支持这种语音的朗读！",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (tts != null) {
            tts.shutdown();//关闭TTS
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        super.onDestroy();
    }

}
