package com.sheepm.activity;

import com.sheepm.copyxiami.R;
import com.sheepm.fragment.LyricFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

/**
 * ÏÔÊ¾¸è´ÊµÄactivity
 * @author sheepm
 *
 */
public class MusicActivity extends Activity {
	
	private LyricFragment lyricFragment = new LyricFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_music);
		setDefaultFragment();
	}
	
	private void setDefaultFragment(){
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.display, lyricFragment);
		transaction.commit();
	}
	
	
	
	

}
