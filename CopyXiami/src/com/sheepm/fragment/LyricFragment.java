package com.sheepm.fragment;

import com.sheepm.copyxiami.R;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class LyricFragment extends Fragment{
	
	private static final String TAG = "LyricFragment"; 

	private View view;
	private SeekBar mSeekBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_lyric, container, false);
//		initView();
		return view;
	}

//	private void initView() {
//		mSeekBar = (SeekBar) view.findViewById(R.id.seekBar1);
//		mSeekBar.setOnSeekBarChangeListener(this);
//	}


//	@Override
//	public void onProgressChanged(SeekBar seekBar, int progress,
//			boolean fromUser) {
//		Log.i("---"+TAG, "onProgressChanged" + progress);
//	}
//
//	@Override
//	public void onStartTrackingTouch(SeekBar seekBar) {
//		Log.i("---"+TAG, "onStartTrackingTouch");
//	}
//
//	@Override
//	public void onStopTrackingTouch(SeekBar seekBar) {
//		Log.i("---"+TAG, "onStopTrackingTouch");
//	}

}
