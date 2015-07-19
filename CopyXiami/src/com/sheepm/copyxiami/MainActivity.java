package com.sheepm.copyxiami;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.sheepm.Utils.Constants;
import com.sheepm.Utils.MediaUtil;
import com.sheepm.Utils.SlidingMenu;
import com.sheepm.activity.MusicActivity;
import com.sheepm.application.Myapp;
import com.sheepm.bean.Mp3Info;
import com.sheepm.fragment.MainFragment;

public class MainActivity extends Activity implements OnClickListener {
	
	private String TAG = "MainActivity";

	private SlidingMenu menu;
	private ListView mMenulist;
	private ArrayAdapter<String> adapter;
	private MainFragment mainFragment = new MainFragment();

	private LinearLayout mLayout;
	private LinearLayout mBtmLinear;

	private LocalMusicBroadcastReceiver receiver;

	private NotificationManager manager;
	private RemoteViews remoteViews;

	private ImageView btm_album;
	private TextView btm_title;
	private TextView btm_artist;
	private ImageView btm_state;
	private ImageView btm_next;
	private List<Mp3Info> mp3Infos;


	private boolean isFirst = true;
	private int position ;

//	private boolean isPlay = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		regFilter();
		setDefaultFragment();
		mMenulist.setAdapter(adapter);
		setOnclickListener();
	}

	private void initView() {
		menu = (SlidingMenu) findViewById(R.id.id_menu);
		mLayout = (LinearLayout) findViewById(R.id.main);
		mMenulist = (ListView) findViewById(R.id.listView1);
		// 设置listview的头
		mMenulist.addHeaderView(LayoutInflater.from(this).inflate(
				R.layout.menu_lv_header, null));
		String[] menulist = getResources().getStringArray(R.array.menu);
		adapter = new ArrayAdapter<String>(MainActivity.this,
				R.layout.listview_style, menulist);

		// 实例化bottommusic
		mBtmLinear = (LinearLayout) findViewById(R.id.btm_linear);
		btm_album = (ImageView) findViewById(R.id.bottom_img_album);
		btm_artist = (TextView) findViewById(R.id.bottom_tv_artist);
		btm_title = (TextView) findViewById(R.id.bottom_tv_title);
		btm_state = (ImageView) findViewById(R.id.player_state);
		btm_next = (ImageView) findViewById(R.id.play_next);
		mp3Infos = MediaUtil.getMp3Infos(this);

		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

	}

	private void setOnclickListener() {
		// 设置点击事件监听
		mLayout.setOnClickListener(this);
		btm_state.setOnClickListener(this);
		btm_next.setOnClickListener(this);
		mBtmLinear.setOnClickListener(this);

	}

	/**
	 * 设置默认
	 */
	public void setDefaultFragment() {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.fragment_main, mainFragment);
		transaction.commit();
	}

	/**
	 * 切换fragment
	 * 
	 * @param fragment
	 */
	public void replaceFragment(Fragment fragment) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.fragment_main, fragment);
		transaction.commit();
	}

	/**
	 * 广播过滤
	 */
	private void regFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_LIST_SEARCH);
		filter.addAction(Constants.ACTION_PAUSE);
		filter.addAction(Constants.ACTION_PLAY);
		filter.addAction(Constants.ACTION_NEXT);
		filter.addAction(Constants.ACTION_PRV);
		filter.setPriority(700);
		receiver = new LocalMusicBroadcastReceiver();
		registerReceiver(receiver, filter); // 注册接收
	}

	/**
	 * 设置通知
	 */
	@SuppressLint("NewApi")
	private void setNotification() {

		NotificationCompat.Builder builder = new Builder(this);

		Intent intent = new Intent(this, MainActivity.class);
		// 点击跳转到主界面
		PendingIntent intent_go = PendingIntent.getActivity(this, 5, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.notice, intent_go);

		// 4个参数context, requestCode, intent, flags
		PendingIntent intent_close = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.widget_close, intent_close);

		// 设置上一曲
		Intent prv = new Intent();
		prv.setAction(Constants.ACTION_PRV);
		PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, prv,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.widget_prev, intent_prev);

		// 设置播放
		if (Myapp.isPlay) {
			Intent playorpause = new Intent();
			playorpause.setAction(Constants.ACTION_PAUSE);
			PendingIntent intent_play = PendingIntent.getBroadcast(this, 2,
					playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.widget_play, intent_play);
		}
		if (!Myapp.isPlay) {
			Intent playorpause = new Intent();
			playorpause.setAction(Constants.ACTION_PLAY);
			PendingIntent intent_play = PendingIntent.getBroadcast(this, 6,
					playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.widget_play, intent_play);
		}

		// 下一曲
		Intent next = new Intent();
		next.setAction(Constants.ACTION_NEXT);
		PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, next,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.widget_next, intent_next);

		// 设置收藏
		PendingIntent intent_fav = PendingIntent.getBroadcast(this, 4, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.widget_fav, intent_fav);

		builder.setSmallIcon(R.drawable.notification_bar_icon); // 设置顶部图标

		Notification notify = builder.build();
		notify.contentView = remoteViews; // 设置下拉图标
		notify.bigContentView = remoteViews; // 防止显示不完全,需要添加apisupport
		notify.flags = Notification.FLAG_ONGOING_EVENT;
		notify.icon = R.drawable.notification_bar_icon;

		manager.notify(100, notify);
	}

	/**
	 * 自定义广播接收器
	 * 
	 * @author sheepm
	 * 
	 */
	public class LocalMusicBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.ACTION_LIST_SEARCH)) {
				position = intent.getIntExtra("position", 0);
				Myapp.isPlay= true;
				long id = intent.getLongExtra("id", 0);
				for (int i = 0; i < mp3Infos.size(); i++) {
					if (id == mp3Infos.get(i).getId()) {
						isFirst = false;
						Message message = Message.obtain();
						message.obj = mp3Infos.get(i);
						handler.sendMessage(message);
					}
				}
			} else if (intent.getAction().equals(Constants.ACTION_PLAY)) {
				if (isFirst) {
					isFirst = false;
					Myapp.isPlay = true;
					Message message = Message.obtain();
					message.obj = mp3Infos.get(intent
							.getIntExtra("position", 0));
					handler.sendMessage(message);
					
				}else{
					Myapp.isPlay = true;
					Message message = Message.obtain();
					message.obj = Myapp.isPlay;
					handler2.sendMessage(message);
					setNotification();
				}
			
			} else if (intent.getAction().equals(Constants.ACTION_NEXT)) {
				Myapp.isPlay = true;
				isFirst = false;
				if ((Myapp.state % 3) ==1 ||(Myapp.state % 3) == 2 ) {
					if (position < mp3Infos.size() - 1) {
						++position;
					} else {
						position = 0;
					}
				}else if ((Myapp.state % 3) == 0) {
					position = Myapp.position;
				}
				Message message = Message.obtain();
				message.obj = mp3Infos.get(position);
				handler.sendMessage(message);
				
				Message message2 = Message.obtain();
				message2.obj = Myapp.isPlay;
				handler2.sendMessage(message2);
			
			} else if (intent.getAction().equals(Constants.ACTION_PAUSE)) {
				Myapp.isPlay = false;
				isFirst = false;
				
				
				Message message = Message.obtain();
				message.obj = mp3Infos.get(position);
				handler.sendMessage(message);
				
				Message message2 = Message.obtain();
				message2.obj = Myapp.isPlay;
				handler2.sendMessage(message2);
				
				setNotification();
			}else if (intent.getAction().equals(Constants.ACTION_PRV)) {
				Myapp.isPlay = true;
				if ((Myapp.state % 3) ==1 || (Myapp.state % 3)== 2) {
					if (position == 0) {
						position = mp3Infos.size()-1;
					} else {
						--position ;
					}
				}else if ((Myapp.state % 3) == 0) {
					position = Myapp.position;
				}
				
				Message message = Message.obtain();
				message.obj = mp3Infos.get(position);
				handler.sendMessage(message);
				
				Message message2 = Message.obtain();
				message2.obj = Myapp.isPlay;
				handler2.sendMessage(message2);
			}
		}

	}

	public Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			Mp3Info info = (Mp3Info) msg.obj;
			Bitmap bitmap = MediaUtil.getArtwork(getApplicationContext(),
					info.getId(), info.getAlbumId(), true, false);
			btm_album.setImageBitmap(bitmap);
			btm_artist.setText(info.getArtist());
			btm_title.setText(info.getTitle());

			// 播放歌曲
			btm_state
					.setImageResource(R.drawable.player_btn_radio_pause_normal);

			// 设置通知栏的图片文字
			remoteViews = new RemoteViews(getPackageName(),
					R.layout.customnotice);
			remoteViews.setImageViewBitmap(R.id.widget_album, bitmap);
			remoteViews.setTextViewText(R.id.widget_title, info.getTitle());
			remoteViews.setTextViewText(R.id.widget_artist, info.getArtist());
			if (Myapp.isPlay) {
				remoteViews.setImageViewResource(R.id.widget_play, R.drawable.widget_btn_pause_normal);
			}else {
				remoteViews.setImageViewResource(R.id.widget_play, R.drawable.widget_btn_play_normal);
			}
		
			setNotification();
		};
	};
	
	/**
	 * 设置notifycation的播放，暂停按钮
	 */
	private Handler handler2 = new Handler(){
		
		public void handleMessage(Message msg) {
			boolean is = (Boolean) msg.obj;
			if (is) {
				remoteViews.setImageViewResource(R.id.widget_play, R.drawable.widget_btn_play_normal);
				btm_state
				.setImageResource(R.drawable.player_btn_radio_pause_normal);
			}else {
				remoteViews.setImageViewResource(R.id.widget_play, R.drawable.widget_btn_pause_normal);
				btm_state
				.setImageResource(R.drawable.player_btn_radio_play_normal);
			}
		};
		
	};

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.nav_change:
			if (menu.isOpen()) {
				menu.closeMenu();
			} else {
				menu.openMenu();
			}
			break;

		case R.id.main:
			menu.closeMenu();
			break;

		case R.id.btm_linear:
			Intent intent = new Intent();
			intent.putExtra("position", position);
			Log.i("---"+TAG, ""+position);
			intent.setClass(MainActivity.this, MusicActivity.class);
			startActivity(intent);

			break;

		case R.id.player_state:
			if (btm_state
					.getDrawable()
					.getConstantState()
					.equals(getResources().getDrawable(
							R.drawable.player_btn_radio_pause_normal)
							.getConstantState())) {
				Intent broadcast = new Intent();
				broadcast.setAction(Constants.ACTION_PAUSE);
				sendBroadcast(broadcast);
				btm_state
						.setImageResource(R.drawable.player_btn_radio_play_normal);
			} else if (btm_state
					.getDrawable()
					.getConstantState()
					.equals(getResources().getDrawable(
							R.drawable.player_btn_radio_play_normal)
							.getConstantState())) {
				Intent broadcast = new Intent();
				broadcast.setAction(Constants.ACTION_PLAY);
				sendBroadcast(broadcast);
				btm_state
						.setImageResource(R.drawable.player_btn_radio_pause_normal);
			}
			break;
		case R.id.play_next:
			Intent broadcast = new Intent();
			broadcast.setAction(Constants.ACTION_NEXT);
			sendBroadcast(broadcast);
			break;
		default:
			break;
		}
	}


	@Override
	protected void onDestroy() {
		Log.i("---"+TAG, "ondestory");
		super.onDestroy();
		if (remoteViews != null) {
			manager.cancel(100);
		}
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
	}

}
