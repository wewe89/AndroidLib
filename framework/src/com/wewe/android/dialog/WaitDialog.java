package com.wewe.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wewe.android.R;

public class WaitDialog extends Dialog {

	private TextView _messageTv;
	
	public WaitDialog(Context context) {
		super(context);
		init(context);
	}

	public WaitDialog(Context context, int defStyle) {
		super(context, defStyle);
		init(context);
	}

	protected WaitDialog(Context context, boolean cancelable, OnCancelListener listener) {
		super(context, cancelable, listener);
		init(context);
	}

	private void init(Context context) {
		setCancelable(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_wait, null);
		_messageTv = (TextView) view.findViewById(R.id.tv_loading);
		setContentView(view);
	}


	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
//		if (TDevice.isTablet()) {
//			int i = (int) TDevice.dpToPixel(360F);
//			if (i < TDevice.getScreenWidth()) {
//				WindowManager.LayoutParams params = getWindow()
//						.getAttributes();
//				params.width = i;
//				getWindow().setAttributes(params);
//			}
//		}
	}

	public void setMessage(int message) {
		_messageTv.setText(message);
	}

	public void setMessage(String message) {
		_messageTv.setText(message);
	}
}
