package com.wewe.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

import static com.wewe.android.util.LogUtils.LOGD;
import static com.wewe.android.util.LogUtils.makeLogTag;

public class CameraActivity extends Activity {
	private static final String TAG=makeLogTag(CameraActivity.class);
	private String mImageFilePath;
	private static final String IMAGEFILEPATH = "ImageFilePath";
	public final static String IMAGE_PATH = "image_path";
	static Activity mContext;
	public final static int GET_IMAGE_REQ = 5000;
	private static Context applicationContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mImageFilePath = savedInstanceState.getString(IMAGEFILEPATH);
			Log.i("savedInstanceState", mImageFilePath);
			File mFile = new File(IMAGEFILEPATH);
			if (mFile.exists()) {
				Intent rsl = new Intent();
				rsl.putExtra(IMAGE_PATH, mImageFilePath);
				setResult(Activity.RESULT_OK, rsl);
				Log.i("savedInstanceState", "shoot successed");
				finish();
			} else {
				Log.i("savedInstanceState", "shoot failed");
			}
		}

		mContext = this;
		applicationContext = getApplicationContext();
		if (savedInstanceState == null) {
			String output=getIntent().getStringExtra(MediaStore.EXTRA_OUTPUT);
			LOGD(TAG,output+"");
			initialUI(output);
		}
	}

	public void initialUI(String output) {
		mImageFilePath = output;
		File out = new File(mImageFilePath);
		showCamera(out);

	}

	private void showCamera(File out) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); // set
		startActivityForResult(intent, GET_IMAGE_REQ);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (GET_IMAGE_REQ == requestCode && resultCode == Activity.RESULT_OK) {
			Intent rsl = new Intent();
			rsl.putExtra(IMAGE_PATH, mImageFilePath);
			mContext.setResult(Activity.RESULT_OK, rsl);
			mContext.finish();
		} else {
			mContext.finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("ImageFilePath", mImageFilePath + "");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

	}
}
