package com.example.SDK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

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
        findViewById(R.id.gift).setOnClickListener(this);
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
                WindowManagerUtil.createMagnetView(context);
                break;
            case R.id.setting:
                Intent intent = new Intent(context,CenterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                WindowManagerUtil.removeDetailWindow(context);
                WindowManagerUtil.createMagnetView(context);
                break;

            default:
                break;

        }
    }

}
