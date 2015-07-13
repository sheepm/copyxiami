package com.sheepm.fragment;

import com.sheepm.Utils.SlidingMenu;
import com.sheepm.copyxiami.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 我的 界面
 * 
 * @author sheepm
 * 
 */
public class MyFragment extends Fragment implements OnClickListener {
	private View view;
	private LinearLayout mMymusic;
	private LocalMusicFragment localMusicFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my, container, false);
		initView();

		setOnclickListener();
		return view;
	}

	private void setOnclickListener() {
		mMymusic.setOnClickListener(this);
	}

	private void initView() {
		mMymusic = (LinearLayout) view.findViewById(R.id.mymusic);
	}

	/**
	 * 切换fragment视图
	 * 
	 * @param fragment
	 */
	@SuppressLint("NewApi")
	public void replaceFragment(Fragment fragment) {
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragment_main, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.mymusic:
				localMusicFragment = new LocalMusicFragment();
				replaceFragment(localMusicFragment);
			
			break;

		default:
			break;
		}
	}

}
