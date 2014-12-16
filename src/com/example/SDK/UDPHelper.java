package com.example.SDK;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private static InetAddress mAddress = null;
    private static int i = 0;
    private static int j = 0;
    private static DatagramSocket mSocket = null;
    private static DatagramPacket datagramPacket = null;
    private static final int MAXNUM = 3;      //设置重发数据的最多次数
    private static Context context;
    private DatagramPacket receivePacket;
    private Handler handler;
    private static int serverPort;  //服务器端口号
    private static String ipAddress;  //服务器IP
    private static String sign; //检验码
    private static String type;
    private static String data; //发送的数据

    public UDPHelper(Context context, Handler handler, String type) {
        this.context = context;
        this.handler = handler;
        this.type = type;
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
        //int server_port = 5060;  //服务端端口号
        byte[] buf = new byte[1024];
        int tries = 0;                         //重发数据的次数
        boolean receivedResponse = false;     //是否接收到数据的标志位
        Log.d("UDP Demo", "UDP发送数据:" + message);
        try {
            if (i == 0) {
                mSocket = new DatagramSocket(7000); //客户端接受消息端口号
                mSocket.setSoTimeout(5000); //5s超时
                i = 1;
            }
            if (j == 0) {
                mAddress = InetAddress.getByName(ipAddress); //服务器IP地址
                j = 1;
            }
        } catch (SocketException e) {
            System.out.println("=========SocketException=====");
            handler.sendEmptyMessage(2);
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        datagramPacket = new DatagramPacket(message.getBytes(), message.length(), mAddress, serverPort);

        while (!receivedResponse && tries < MAXNUM) {
            try {
                mSocket.send(datagramPacket);
                receivePacket = new DatagramPacket(buf, buf.length);
                try {
                    //接收从服务端发送回来的数据
                    mSocket.receive(receivePacket);
                    //如果接收到数据。则将receivedResponse标志位改为true，从而退出循环
                    handler.sendEmptyMessage(3);
                    receivedResponse = true;
                } catch (InterruptedIOException e) {
                    //如果接收数据时阻塞超时，重发并减少一次重发的次数
                    tries += 1;

                    Message msg = new Message();
                    msg.what = 6;
                    Bundle da = new Bundle();
                    da.putInt("try",tries);
                    msg.setData(da);
                    handler.sendMessage(msg);
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
            String str_receive = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getOffset() + receivePacket.getLength()) +
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
            getMsg(type);
            send(data); //发送的数据
        }
    }

    //发送参数组装
    private void getMsg(String type) {

        serverPort = Integer.parseInt(SharePreUtils.getString(context, "port"));
        ipAddress = SharePreUtils.getString(context, "ip");
        Log.d("getMsg:", serverPort + ":" + ipAddress);
        if (type.equals("auth")) {  //授权
            //(0x5099)校验=0x000c+0x408A+0x0000+0x0100+0x0f03
            prepareData("auth");

        } else {  //呼梯
            prepareData("call");
        }
    }

    private void prepareData(String type) {
        int floor = Integer.valueOf(SharePreUtils.getString(context, "floor"));
        int room = Integer.valueOf(SharePreUtils.getString(context, "room"));
        Log.d("getMsg-floor:", floor + "");
        Log.d("getMsg-room:", room + "");
        String mf = Integer.toHexString(floor);
        String mr = Integer.toHexString(room);
        if (floor <= 15) {
            mf = "0" + mf;
            //0xFF=-1; 0xFE=-2; 0xFD=-3; 0xFC=-4; 0xFB=-5; 0xFA=-6;
            if (floor < 0) {
                switch (floor) {
                    case -1:
                        mf = 0xFF + "";
                        break;
                    case -2:
                        mf = 0xFE + "";
                        break;
                    case -3:
                        mf = 0xFD + "";
                        break;
                    case -4:
                        mf = 0xFC + "";
                        break;
                    case -5:
                        mf = 0xFB + "";
                        break;
                    case -6:
                        mf = 0xFA + "";
                        break;
                }
            }
        }

        if (room <= 15) {
            mr = "0" + mr;
        }

        Log.d("getMsg-floor-parse:", mf + "");
        Log.d("getMsg-room-parse:", mr + "");
        String str = "";
        if (type.equals("auth")) {
            sign = Integer.toHexString(0x000c + 0x408a + 0x0000 + 0x0100 + Integer.parseInt(mf + mr, 16)); //5099
            str = sign + "000c408a00000100" + mf + mr;
        } else {
            //0x000c+0x408b+0x0000+0x0f03+0x0100
            sign = Integer.toHexString(0x000c + 0x408b + 0x0000 + Integer.parseInt(mf + mr, 16) + 0x0100); //5099
            str = sign + "000c408b0000" + mf + mr + "0100";
        }
        Log.d("getMag-sign:", sign);
        //50 99 00 0c 40 8a 00 00 01 00 0f 03
/*        String str = "5099 000c 408a 0000 0100 0f03";
        str = sign + "000c408a00000100" + mf + mr;*/
        data = parseString(str);
        Log.d("getMsg-str:", str);
        Log.d("getMsg--data:", data);
    }

    public String parseString(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 1) {
                result += str.charAt(i) + " ";
            } else {
                result += str.charAt(i) + "";
            }
        }
        return result;
    }

    //检查配置
    private boolean checkConfig() {
        if (TextUtils.isEmpty(SharePreUtils.getString(context, "room"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "floor"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "port"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "ip1"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "ip2"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "ip3"))
                || TextUtils.isEmpty(SharePreUtils.getString(context, "ip4"))
                ) {
            handler.sendEmptyMessage(5);
            return false;
        }
        return true;
    }
}
