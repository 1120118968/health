package com.example.mobilehomework;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientThread implements Runnable {
    private Socket s;

    public String content = null;

    String id;

    // 定义向UI线程发送消息的Handler对象

    private Handler handler;
    // 登录 注册 签到 开始答题 反馈题目


    // 定义接收UI线程的消息的Handler对象

    public Handler revHandler;


    // 该线程所处理的Socket所对应的输入流


    BufferedReader br = null;


    OutputStream os = null;




    public ClientThread(Handler handler) {
        this.handler = handler;

    }

    @SuppressLint("HandlerLeak")
    public void run() {

        try {
            //192.168.43.217为本机的ip地址，30000为与MultiThreadServer服务器通信的端口
           // s = new Socket("10.21.101.253", 8888);
            //云服务器IP地址
           // s = new Socket("192.168.3.10", 8888);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();

            // 启动一条子线程来读取服务器响应的数据
            new Thread() {
                @Override
                public void run() {
                    // 不断读取Socket输入流中的内容。
                    try {

                        while ((content = br.readLine()) != null) {
                            //span>// 每当读到来自服务器的数据之后，发送消息通知程序界面显示该数据
                            Message msg = new Message();
                            msg.obj = content;
                            String[] strarray = content.split(" ");
                            for (int i = 0; i < strarray.length; i++)
                                System.out.println(strarray[i] + " ");

                            System.out.println(content);

                            String command = strarray[2];
                            if (command.equals("Y")) {//登录成功
                                //if psd=s psd
                                msg.what = 0x123;
                                msg.obj = content;
                             //
                                    handler.sendMessage(msg);
                              //  }
                            } else if (command.equals("YR")){//注册成功
                                msg.what = 0x1234;
                                msg.obj = content;
                                if(handler != null) {
                                    handler.sendMessage(msg);
                                }
                            }
                            else{
                                msg.what = 0x000;
                                msg.obj = content;
                                if(handler != null) {
                                    handler.sendMessage(msg);
                                }
                            }

                        }

                    } catch (IOException
                            e) {

                        e.printStackTrace();

                    }

                }

            }.start();


            // 为当前线程初始化Looper

            Looper.prepare();

            // 创建revHandler对象

            revHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {

                    // 接收到UI线程中用户输入的数据

                    if (msg.what == 0x345) {

                        // 将用户在文本框内输入的内容写入网络

                        String ip = s.getInetAddress().getHostAddress();
                        try {

                            os.write((msg.obj.toString()+" "+ip+ "\r\n").getBytes("utf-8"));

                        } catch (Exception
                                e) {

                            e.printStackTrace();

                        }

                    }

                }

            };

            // 启动Looper

            Looper.loop();


        } catch (SocketTimeoutException e1) {
            System.out.println("网络连接超时！！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

