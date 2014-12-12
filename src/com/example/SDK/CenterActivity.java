package com.example.SDK;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

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
        ip1 = (EditText) findViewById(R.id.ip1);
        ip2 = (EditText) findViewById(R.id.ip2);
        ip3 = (EditText) findViewById(R.id.ip3);
        ip4 = (EditText) findViewById(R.id.ip4);
        set_seek = (ImageView) findViewById(R.id.set_seek);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

        top = (LinearLayout) findViewById(R.id.top);
        top1 = (LinearLayout) findViewById(R.id.top1);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
