package com.audiotorium2.service;

import java.util.List;

import com.audiotorium2.entity.GenreWithMusic;
import com.audiotorium2.entity.SongView;

public interface IMusicService {

	public List<SongView> search(String searchString) throws Exception;

	public SongView getSong(int id) throws Exception;

	public List<GenreWithMusic> listSongsByGenre() throws Exception;

	public void addToFavoriteList(int musicId, int userId) throws Exception;

	public List<SongView> retrieveFavoriteList(int userid);
}
