package com.audiotorium2.entity;

import java.util.List;

public class GenreWithMusic {

	private int genreId;
	private String genreName;
	private List<SongView> musicList;

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public List<SongView> getMusicList() {
		return musicList;
	}

	public void setMusicList(List<SongView> musicList) {
		this.musicList = musicList;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

}
