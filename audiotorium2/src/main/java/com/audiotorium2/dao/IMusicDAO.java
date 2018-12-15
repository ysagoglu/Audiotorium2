package com.audiotorium2.dao;

import java.util.List;

import com.audiotorium2.entity.SongView;

public interface IMusicDAO {
	public  List<SongView> findMusic(String name);

	public String getSongPath(int id);
}
