package com.sheepm.adapter;

import java.util.List;

import com.sheepm.Utils.MediaUtil;
import com.sheepm.bean.Mp3Info;
import com.sheepm.copyxiami.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocalMusicAdapter extends BaseAdapter {

	private Context mContext;

	private List<Mp3Info> mp3Infos;
	private LayoutInflater mInflater;

	public LocalMusicAdapter(Context context, List<Mp3Info> mp3Infos) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mp3Infos = mp3Infos;
	}

	@Override
	public int getCount() {
		return mp3Infos.size();
	}

	@Override
	public Object getItem(int position) {
		return mp3Infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.music_listitem, null);
			viewHolder.mImgAlbum = (ImageView) convertView
					.findViewById(R.id.img_album);
			viewHolder.mTvTitle = (TextView) convertView
					.findViewById(R.id.tv_title);
			viewHolder.mTvArtist = (TextView) convertView
					.findViewById(R.id.tv_artist);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		 viewHolder.mImgAlbum.setImageBitmap(MediaUtil.getArtwork(mContext,
		 mp3Infos.get(position).getId(), mp3Infos.get(position).getAlbumId(),
		 true, true));
		viewHolder.mTvTitle.setText(mp3Infos.get(position).getTitle());
		viewHolder.mTvArtist.setText(mp3Infos.get(position).getArtist());
		return convertView;
	}

	public class ViewHolder {
		ImageView mImgAlbum;
		TextView mTvTitle;
		TextView mTvArtist;
	}

}
