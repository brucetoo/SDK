package com.example.SDK;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by bruce-too
 * on 2014/12/12
 * Time 22:36.
 */
public class PasswordActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_password);

        initDialog();
    }

    private void initDialog() {
        final CustomDialog dialog = new CustomDialog(this, R.style.CustomDialog);
        //dialog.setTitleText("    ");
      //  dialog.hideTitle();
    /*    dialog.setContentText("您的详细预算合计金额超过总预算，是否确定修改总预算？");
        dialog.setBtnConfirmText("确定");
        dialog.setBtnCancelText("取消");*/
        dialog.setBtnConfirmOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setBtnCancelOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
