package com.sheepm.fragment;

import com.sheepm.copyxiami.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragment extends Fragment implements OnClickListener {
	private View view;
	private ImageView mChange;
	private TextView mMytv;
	private TextView mRecommendtv;
	private TextView mMusictv;
	private ImageView mSearch;
	private MyFragment mMyFragment = new MyFragment();
	private RecommendFragment mRecomFragment = new RecommendFragment();
	private MusicFragment mMusicFragment = new MusicFragment();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, container, false);
		initView();
		setDefaultFragment();

		// 设置事件监听
		mSearch.setOnClickListener(this);
		mChange.setOnClickListener(this);
		mMytv.setOnClickListener(this);
		mRecommendtv.setOnClickListener(this);
		mMusictv.setOnClickListener(this);
		return view;
	}

	private void initView() {
		// 初始化nav上的元素
		mSearch = (ImageView) view.findViewById(R.id.nav_search);
		mChange = (ImageView) view.findViewById(R.id.nav_change);
		mMytv = (TextView) view.findViewById(R.id.tv_fragment_my);
		mRecommendtv = (TextView) view.findViewById(R.id.tv_fragment_recommend);
		mMusictv = (TextView) view.findViewById(R.id.tv_fragment_music);
	}

	@SuppressLint("NewApi")
	public void setDefaultFragment() {
		FragmentTransaction transaction = getChildFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content, mMyFragment);
		transaction.commit();
	}

	/**
	 * 重新设置nav上的字体颜色
	 */
	public void resetTextColor() {
		mMytv.setTextColor(Color.BLACK);
		mRecommendtv.setTextColor(Color.BLACK);
		mMusictv.setTextColor(Color.BLACK);
	}

	/**
	 * 切换fragment视图
	 * 
	 * @param fragment
	 */
	@SuppressLint("NewApi")
	public void replaceFragment(Fragment fragment) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = getChildFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.commit();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_fragment_my:
			resetTextColor();
			mMytv.setTextColor(getResources().getColor(R.color.nav_text_press));
			replaceFragment(mMyFragment);
			break;

		case R.id.tv_fragment_recommend:
			resetTextColor();
			mRecommendtv.setTextColor(getResources().getColor(
					R.color.nav_text_press));
			replaceFragment(mRecomFragment);
			break;

		case R.id.tv_fragment_music:
			resetTextColor();
			mMusictv.setTextColor(getResources().getColor(
					R.color.nav_text_press));
			replaceFragment(mMusicFragment);
			break;

		case R.id.nav_search:

			break;

		default:
			break;
		}
	}
}
