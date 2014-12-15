package com.example.SDK;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Class Function:
 * Created By Bruce Too
 * On 2014-11-24 下午 5:32
 */
public class DetailView extends LinearLayout implements View.OnClickListener {
    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;
    private Context context;

    public DetailView(final Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_flow, this);
        View view = findViewById(R.id.flow_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        findViewById(R.id.image).setOnClickListener(this);
        findViewById(R.id.setting).setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        findViewById(R.id.auth).setOnClickListener(this);

     /*   close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.removeSmallWindow(context);
                Intent intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.createSmallWindow(context);
            }
        });*/
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image:
                WindowManagerUtil.removeDetailWindow(context);
                WindowManagerUtil.createMagnetView(context,SharePreUtils.getFloat(context,"prog"));
                break;
            case R.id.setting:

                Intent intent = new Intent(context,PasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

               /* Intent intent = new Intent(context,CenterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/

                break;

            case R.id.auth:
                UDPHelper helper = new UDPHelper(context,handler,"auth");
                Thread udp = new Thread(helper);
                udp.start();
                break;

            case R.id.call:
                UDPHelper helper1 = new UDPHelper(context,handler,"call");
                Thread udp1 = new Thread(helper1);
                udp1.start();
                break;
            default:
                break;
        }
        WindowManagerUtil.removeDetailWindow(context);
        WindowManagerUtil.createMagnetView(context,SharePreUtils.getFloat(context,"prog"));
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(context, "消息发送中....", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(context, "消息发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(context, "消息发送成功", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(context, "服务器无响应.....", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(context, "请在设置中填写每一个参数", Toast.LENGTH_LONG).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
