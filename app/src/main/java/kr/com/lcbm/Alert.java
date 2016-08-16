package kr.com.lcbm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import kr.ds.config.Config;
import kr.ds.utils.PushWakeLock;

public class Alert extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//풀스크린
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
		//| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
		//| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
		//| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		PushWakeLock.acquire(this);
		alert();
		Handler handler = new Handler(){
        	public void handleMessage(Message msg){
        		super.handleMessage(msg);
        		PushWakeLock.release();
        	}
        };
		handler.sendEmptyMessageDelayed(0, 3000);
	}
	
	public void alert() {
		Bundle Bun = getIntent().getExtras();
		String Text = Bun.getString("text");
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		alertDialog.setPositiveButton("닫기",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						PushWakeLock.release();
						finish();
					}
				});

		alertDialog.setNegativeButton("업데이트하기",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=kr.com.lcbm")));

						PushWakeLock.release();
						finish();
					}
				});
		alertDialog.setMessage(Text);
		alertDialog.setTitle("알림");
		alertDialog.show();
	}
}
