package com.example.SDK;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;

/**
 * Created by Bruce-too-N1007
 * On 2014/12/15
 * time 10:56
 */
public class UDPHelper implements Runnable {
    private boolean flag = true;//指示监听线程是否终止
    private static InetAddress mAddress = null;
    private static int i = 0;
    private static int j = 0;
    private static DatagramSocket mSocket = null;
    private static DatagramPacket datagramPacket = null;
    private static final int MAXNUM = 5;      //设置重发数据的最多次数
    private static Context context;
    private DatagramPacket receivePacket;
    private Handler handler;

    public UDPHelper(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(String message) {

        handler.sendEmptyMessage(1);
        //Toast.makeText(context,"数据正在发送.....", Toast.LENGTH_LONG).show();
        message = (message == null ? "UDP DATA!!!!" : message);
        int server_port = 5060;  //服务端端口号
        byte[] buf = new byte[1024];
        int tries = 0;                         //重发数据的次数
        boolean receivedResponse = false;     //是否接收到数据的标志位
        Log.d("UDP Demo", "UDP发送数据:" + message);
        try {
            if (i == 0) {
                mSocket = new DatagramSocket(7000); //客户端接受消息端口号
                mSocket.setSoTimeout(5000);
                i = 1;
            }
            if (j == 0) {
                mAddress = InetAddress.getByName("192.168.56.1"); //服务器IP地址
                j = 1;
            }
        } catch (SocketException e) {
            System.out.println("=========SocketException=====");
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (datagramPacket == null) {
            datagramPacket = new DatagramPacket(message.getBytes(), message.length(), mAddress, server_port);
        }

        while (!receivedResponse && tries < MAXNUM) {
            try {
                mSocket.send(datagramPacket);
                receivePacket = new DatagramPacket(buf, buf.length);
                try {
                    //接收从服务端发送回来的数据
                    mSocket.receive(receivePacket);
                    //如果接收到数据。则将receivedResponse标志位改为true，从而退出循环
                    receivedResponse = true;
                } catch (InterruptedIOException e) {
                    //如果接收数据时阻塞超时，重发并减少一次重发的次数
                    tries += 1;
                    System.out.println("Time out," + (MAXNUM - tries) + " more tries...");
                }
                //System.out.println("=======发送成功========");
                //mSocket = null;
                //handler.sendEmptyMessage(3);
                //StartListen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (receivedResponse) {
            //如果收到数据，则打印出来
            System.out.println("client received data from server：");
            String str_receive = new String(receivePacket.getData(), 0, receivePacket.getLength()) +
                    " from " + receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort();
            System.out.println(str_receive);
            //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
            //所以这里要将dp_receive的内部消息长度重新置为1024
            receivePacket.setLength(1024);
        } else {
            //如果重发MAXNUM次数据后，仍未获得服务器发送回来的数据，则打印如下信息
            System.out.println("No response -- give up.");
            handler.sendEmptyMessage(4);
        }
        // mSocket.close();
    }

    @Override
    public void run() {

        if (checkConfig()) {
            getMsg();
            send(getMsg()); //发送的数据
        }
    }

    //发送参数组装
    private String getMsg() {

        return "";
    }

    //检查配置
    private boolean checkConfig() {
        if (TextUtils.isEmpty(SharePreUtils.getString(context, "room"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "floor"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "port"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "ip"))
                ) {
            handler.sendEmptyMessage(5);
            return false;
        }
        return true;
    }
}
