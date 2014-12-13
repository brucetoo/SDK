package com.example.SDK;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialog extends Dialog implements View.OnClickListener {
	int layoutRes;// 布局文件
	Context context;
	private TextView custom_dialog_title_tv;
	private TextView custom_dialog_content_tv;
	/** 确定按钮 **/
	private Button custom_dialog_confirm_btn;
	/** 取消按钮 **/
	private Button custom_dialog_cancel_btn;

	private View custom_dialog_line;
    public EditText custom_dialog_edit_text;

	/**
	 * 自定义主题及布局的构造方法
	 *
	 * @param context
	 * @param theme
	 * @param
	 */
	public CustomDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		this.setContentView(R.layout.layout_dialog);

		// 根据id在布局中找到控件对象
		custom_dialog_title_tv = (TextView) findViewById(R.id.custom_dialog_title_tv);
		custom_dialog_content_tv = (TextView) findViewById(R.id.custom_dialog_content_tv);
		custom_dialog_confirm_btn = (Button) findViewById(R.id.custom_dialog_confirm_btn);
		custom_dialog_cancel_btn = (Button) findViewById(R.id.custom_dialog_cancel_btn);
		custom_dialog_line = (View) findViewById(R.id.custom_dialog_line);
        custom_dialog_edit_text = (EditText) findViewById(R.id.custom_dialog_edit_text);

        this.setCanceledOnTouchOutside(true);
		// 设置按钮的文本颜色
		custom_dialog_confirm_btn.setTextColor(0xff1E90FF);
		custom_dialog_cancel_btn.setTextColor(0xff1E90FF);

		// 为按钮绑定点击事件监听器
		custom_dialog_confirm_btn.setOnClickListener(this);
		custom_dialog_cancel_btn.setOnClickListener(this);
	}

    public String getEditText(){
       return custom_dialog_edit_text.getText().toString();
    }

	public void setTitleText(String titleText) {
		if (custom_dialog_title_tv == null)
			return;
		// custom_dialog_title_tv.getPaint().setFakeBoldText(true);
		custom_dialog_title_tv.setText(titleText);
		custom_dialog_title_tv.setVisibility(View.VISIBLE);
	}

    public void hideTitle(){
        custom_dialog_title_tv.setVisibility(View.GONE);
    }

	public void setContentText(String contentText) {
		if (custom_dialog_content_tv == null)
			return;

		custom_dialog_content_tv.setText(contentText);
		custom_dialog_content_tv.setVisibility(View.VISIBLE);
	}

	public void setBtnConfirmText(String btnText) {
		if (custom_dialog_confirm_btn == null)
			return;
		// custom_dialog_confirm_btn.getPaint().setFakeBoldText(true);
		custom_dialog_confirm_btn.setText(btnText);
		custom_dialog_confirm_btn.setVisibility(View.VISIBLE);
	}

	public void setBtnCancelText(String btnText) {
		if (custom_dialog_cancel_btn == null)
			return;
		// custom_dialog_cancel_btn.getPaint().setFakeBoldText(true);
		custom_dialog_cancel_btn.setText(btnText);
		custom_dialog_cancel_btn.setVisibility(View.VISIBLE);
	}

	public void setBtnConfirmOnClickListener(View.OnClickListener onClickListener) {
		if (custom_dialog_confirm_btn == null)
			return;

		custom_dialog_confirm_btn.setOnClickListener(onClickListener);
	}

	public void setBtnCancelOnClickListener(View.OnClickListener onClickListener) {
		if (custom_dialog_cancel_btn == null)
			return;

		custom_dialog_cancel_btn.setOnClickListener(onClickListener);
	}

	@Override
	public void onClick(View v) {
		this.dismiss();
	}


	public void showDiv(boolean show) {
		if (show) {
			custom_dialog_line.setVisibility(View.VISIBLE);
		} else {
			custom_dialog_line.setVisibility(View.GONE);
		}
	}

	public TextView getCustom_dialog_title_tv() {
		return custom_dialog_title_tv;
	}

	public TextView getCustom_dialog_content_tv() {
		return custom_dialog_content_tv;
	}
}