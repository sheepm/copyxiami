package com.sheepm.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sheepm.Utils.Constants;
import com.sheepm.Utils.MediaUtil;
import com.sheepm.Utils.OtherUtil;
import com.sheepm.bean.Mp3Info;
import com.sheepm.service.MusicService;

import android.app.Application;
import android.content.Intent;
import android.os.Parcelable;

public class Myapp extends Application {
	
	private static List<Mp3Info> infos;
	
	public static boolean isPlay = false;
	
	//state模3的值  0是随机播放   1是列表循环  2是单曲循环
	public static int state =2;
	
	public static int position; 
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		infos= MediaUtil.getMp3Infos(getApplicationContext());
		if (!OtherUtil.isServiceRunning(getApplicationContext(), Constants.MUSIC_SERVICE)) {
			startService();
		}
		
	}
	
	private void startService(){
		Intent service = new Intent();
		service.setClass(getApplicationContext(), MusicService.class);
		service.putParcelableArrayListExtra("mp3Infos",(ArrayList<? extends Parcelable>) infos);
		getApplicationContext().startService(service);
	}
	
	public static void getRandom(){
		Random random = new Random();
		position = random.nextInt(infos.size());
	}

}
