package com.sheepm.broadcast;

import com.sheepm.Utils.Constants;
import com.sheepm.application.Myapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneBroadcastReceiver extends BroadcastReceiver {

	private String TAG = "PhoneBroadcastReceiver";

	/**
	 * 之所以判断音乐是否播放，是因为如果只是打开界面而没播放，会报一个错误 sending message to a Handler on a dead
	 * thread
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			Log.i("---拨出电话", "拨出电话");
			if (Myapp.isPlay) {
				Message msg = Message.obtain();
				msg.obj = context;
				handler.sendMessage(msg);
			}
		} else {
			if (Myapp.isPlay) {
				Message msg = Message.obtain();
				msg.obj = context;
				handler.sendMessage(msg);
			}
			// 来电监听
			TelephonyManager tManager = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);
			tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		}

	}

	/**
	 * 电话状态监听
	 */
	PhoneStateListener listener = new PhoneStateListener() {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			// 挂断
			case TelephonyManager.CALL_STATE_IDLE:
				break;

			//接听
			case TelephonyManager.CALL_STATE_OFFHOOK:

				break;
			//来电
			case TelephonyManager.CALL_STATE_RINGING:

				break;

			default:
				break;
			}
		}

	};

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Context context = (Context) msg.obj;
			Intent phonepause = new Intent();
			phonepause.setAction(Constants.ACTION_PAUSE);
			context.sendBroadcast(phonepause);
		}

	};

}
