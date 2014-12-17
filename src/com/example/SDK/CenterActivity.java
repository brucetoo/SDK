package com.example.SDK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

/**
 * Created by Bruce-too-N1007
 * On 2014/12/12
 * time 10:19
 */
public class CenterActivity extends Activity implements SeekBar.OnSeekBarChangeListener {


    private ImageView local_ad_ig;
    private EditText floor;
    private EditText room;
    private EditText port;
    private EditText ip1;
    private EditText ip2;
    private EditText ip3;
    private EditText ip4;
    private ImageView set_seek;
    private SeekBar seekBar;
    private LinearLayout top;
    private LinearLayout top1;
    private TextView change_secret;
    private Button save;
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_center);

        initView();

    }

    private void initView() {
        local_ad_ig = (ImageView) findViewById(R.id.local_ad_ig);
        //local_ad_ig.setOnClickListener(this);
        floor = (EditText) findViewById(R.id.floor);
        room = (EditText) findViewById(R.id.room);
        port = (EditText) findViewById(R.id.port);
        ip1 = (EditText) findViewById(R.id.ip1);
        ip2 = (EditText) findViewById(R.id.ip2);
        ip3 = (EditText) findViewById(R.id.ip3);
        ip4 = (EditText) findViewById(R.id.ip4);
        set_seek = (ImageView) findViewById(R.id.set_seek);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setProgress((int) (SharePreUtils.getFloat(this, "prog") * 100));

        floor.setText(SharePreUtils.getString(this, "floor"));
        room.setText(SharePreUtils.getString(this, "room"));
        port.setText(SharePreUtils.getString(this, "port"));
        ip1.setText(SharePreUtils.getString(this, "ip1"));
        ip2.setText(SharePreUtils.getString(this, "ip2"));
        ip3.setText(SharePreUtils.getString(this, "ip3"));
        ip4.setText(SharePreUtils.getString(this, "ip4"));

        top = (LinearLayout) findViewById(R.id.top);
        top1 = (LinearLayout) findViewById(R.id.top1);

        change_secret = (TextView) findViewById(R.id.change_secret);
        change_secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CenterActivity.this, SecretActivity.class);
                CenterActivity.this.startActivity(intent);
            }
        });
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judge();
            }
        });
        checkbox = (CheckBox) findViewById(R.id.checkbox);
       if(SharePreUtils.getFloat(this,"boot") == 0){
           checkbox.setChecked(false);
       }else{
           checkbox.setChecked(true);
       }
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkbox.isChecked()) {
                    SharePreUtils.saveFLoat(CenterActivity.this,"boot",1);
                    Toast.makeText(CenterActivity.this, "已设置开机启动", Toast.LENGTH_SHORT).show();
                } else {
                    SharePreUtils.saveFLoat(CenterActivity.this,"boot",0);
                    Toast.makeText(CenterActivity.this, "已取消开机启动", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void judge() {
        if (TextUtils.isEmpty(room.getText().toString())
                || TextUtils.isEmpty(floor.getText().toString())
                || TextUtils.isEmpty(port.getText().toString())
                || TextUtils.isEmpty(ip1.getText().toString())
                || TextUtils.isEmpty(ip2.getText().toString())
                || TextUtils.isEmpty(ip3.getText().toString())
                || TextUtils.isEmpty(ip4.getText().toString())
                ) {
            Toast.makeText(CenterActivity.this, "每个参数都不能为空,请再次输入", Toast.LENGTH_SHORT).show();
        } else {
            if (Integer.parseInt(room.getText().toString()) > 254) {
                Toast.makeText(CenterActivity.this, "房间号不能大于254", Toast.LENGTH_SHORT).show();
                SharePreUtils.saveString(CenterActivity.this, "room", "254");
            } else {
                SharePreUtils.saveString(CenterActivity.this, "room", room.getText().toString());
            }

            if (Integer.parseInt(floor.getText().toString()) > 254) {
                Toast.makeText(CenterActivity.this, "楼层不能大于254", Toast.LENGTH_SHORT).show();
                SharePreUtils.saveString(CenterActivity.this, "floor", "254");
            } else {
                SharePreUtils.saveString(CenterActivity.this, "floor", floor.getText().toString());
            }

            SharePreUtils.saveString(CenterActivity.this, "port", port.getText().toString());
            SharePreUtils.saveString(CenterActivity.this, "ip1", ip1.getText().toString());
            SharePreUtils.saveString(CenterActivity.this, "ip2", ip2.getText().toString());
            SharePreUtils.saveString(CenterActivity.this, "ip3", ip3.getText().toString());
            SharePreUtils.saveString(CenterActivity.this, "ip4", ip4.getText().toString());
            SharePreUtils.saveString(CenterActivity.this, "ip", ip1.getText().toString() + "."
                            + ip2.getText().toString() + "."
                            + ip3.getText().toString() + "."
                            + ip4.getText().toString()
            );
            Toast.makeText(CenterActivity.this, "参数配置成功", Toast.LENGTH_SHORT).show();
            CenterActivity.this.finish();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        float progress = (float) seekBar.getProgress() / (float) seekBar.getMax();
        Log.d("CenterActivity", progress + "");
        Log.d("CenterActivity-getProgress", seekBar.getProgress() + "");
        Log.d("CenterActivity-getMax", seekBar.getMax() + "");
        /*WindowManagerUtil.removeAllView();
        WindowManagerUtil.createMagnetView(this, progress);*/
        SharePreUtils.saveFLoat(this, "prog", progress); //缓存
        seekBar.setProgress(seekBar.getProgress());
        Intent intent = new Intent(this, SDKService.class);
        intent.putExtra("progress", progress);
        startService(intent);

    }
}
