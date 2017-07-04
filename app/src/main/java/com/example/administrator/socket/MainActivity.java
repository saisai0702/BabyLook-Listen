package com.example.administrator.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.mTextView);
    }

    //点击事件
    public void sentMessage(View view) {

        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //连接到服务器 参数1：IP  参数2：端口号
                    Socket socket = new Socket("192.168.1.107",5000);
                    //输出流
                    OutputStream outputStream = socket.getOutputStream();
                    //回复服务器端
                    outputStream.write("客户端向服务端发送消息".getBytes("utf-8"));
                    //刷新
                    outputStream.flush();

                    //接收服务器端发送的消息
                    InputStream inputStream = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    final String result = reader.readLine();
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.append("\r\n"+result);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
