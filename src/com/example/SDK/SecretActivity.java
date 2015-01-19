package com.example.SDK;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Bruce-too-N1007
 * On 2014/12/13
 * time 10:12
 */
public class SecretActivity extends Activity {

    private EditText current_secret;
    private EditText new_secret;
    private EditText new_secret_again;
    private Button secret_confirm;
    private Button secret_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_secret);

        initView();
        //   judge();
    }

    private void judge() {
        if (!current_secret.getText().toString().equals(SharePreUtils.getString(this, "password"))) {
            Toast.makeText(this, "当前密码输入有误", Toast.LENGTH_SHORT).show();
            current_secret.setText("");
        } else {
            if (TextUtils.isEmpty(new_secret.getText().toString()) || TextUtils.isEmpty(new_secret_again.getText().toString())) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                if (new_secret.getText().toString().equals(new_secret_again.getText().toString())) {
                    SharePreUtils.saveString(this, "password", new_secret.getText().toString());
                    Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    SharePreUtils.saveFLoat(this, "hint", 1);
                    this.finish();
                }else{
                    Toast.makeText(this, "前后密码不匹配,请重新输入", Toast.LENGTH_SHORT).show();
                    current_secret.setText("");
                    new_secret.setText("");
                    new_secret_again.setText("");
                }
            }
        }
    }

    private void initView() {
        current_secret = (EditText) findViewById(R.id.current_secret);
        new_secret = (EditText) findViewById(R.id.new_secret);
        new_secret_again = (EditText) findViewById(R.id.new_secret_again);
        secret_confirm = (Button) findViewById(R.id.secret_confirm);
        secret_cancle = (Button) findViewById(R.id.secret_cancle);
        secret_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judge();
            }
        });
        secret_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecretActivity.this.finish();
            }
        });
    }
}
