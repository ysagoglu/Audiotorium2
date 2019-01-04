package com.audiotorium2.dao;

import java.util.List;

import com.audiotorium2.entity.GenreWithMusic;
import com.audiotorium2.entity.SongView;

public interface IMusicDAO {
	public List<SongView> findMusic(String name);

	public SongView getSong(int id);

	public List<GenreWithMusic> retrieveSongWithGenre();

	public void addToFavoriteList(int musicId, int userId);

	public List<SongView> retrieveFavoriteListByUserId(int userid);

}
