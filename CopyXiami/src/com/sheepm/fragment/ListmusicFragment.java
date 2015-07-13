package com.sheepm.fragment;

import java.util.List;

import com.sheepm.Utils.Constants;
import com.sheepm.Utils.MediaUtil;
import com.sheepm.adapter.LocalMusicAdapter;
import com.sheepm.bean.Mp3Info;
import com.sheepm.copyxiami.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListmusicFragment extends Fragment implements OnItemClickListener{
	
	private View view;
	private LocalMusicAdapter adapter;
	private ListView mp3lists;
	private List<Mp3Info> infos;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_localmusic, container, false);
		
		mp3lists = (ListView) view.findViewById(R.id.mp3lists);
		infos= MediaUtil.getMp3Infos(getActivity());
		adapter = new LocalMusicAdapter(getActivity(), infos);
		mp3lists.setAdapter(adapter);
		mp3lists.setOnItemClickListener(this);
		return view;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent broadcast = new Intent();
		broadcast.setAction(Constants.ACTION_LIST_SEARCH);
		broadcast.putExtra("id", infos.get(position).getId());
		broadcast.putExtra("position", position);
		getActivity().sendBroadcast(broadcast);
	}

}
