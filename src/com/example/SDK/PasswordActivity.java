package com.example.SDK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by bruce-too
 * on 2014/12/12
 * Time 22:36.
 */
public class PasswordActivity extends Activity {

    private EditText custom_dialog_edit_text;
    private CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_password);

        initDialog();
    }

    private void initDialog() {
         dialog = new CustomDialog(this, R.style.CustomDialog);
        //dialog.setTitleText("    ");
        //  dialog.hideTitle();
    /*    dialog.setContentText("您的详细预算合计金额超过总预算，是否确定修改总预算？");
        dialog.setBtnConfirmText("确定");
        dialog.setBtnCancelText("取消");*/
        custom_dialog_edit_text = dialog.custom_dialog_edit_text;
        dialog.setBtnConfirmOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //默认密码123
                if (custom_dialog_edit_text.getText().toString().equals(SharePreUtils.getString(PasswordActivity.this, "password"))) {
                    Intent intent = new Intent(PasswordActivity.this, CenterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PasswordActivity.this.startActivity(intent);
                    startService(new Intent(PasswordActivity.this, SDKService.class));
                    PasswordActivity.this.finish();
                }else{
                    Toast.makeText(PasswordActivity.this,"密码错误,请再次输入",Toast.LENGTH_SHORT).show();
                    custom_dialog_edit_text.setText("");
                }
            }
        });
        dialog.setBtnCancelOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PasswordActivity.this.finish();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
