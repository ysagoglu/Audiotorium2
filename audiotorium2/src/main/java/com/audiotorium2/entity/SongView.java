package com.audiotorium2.entity;


public class SongView {

	private int musicid;
	private String musicName;
	private String artistName;
	private String genre;
	
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getMusicid() {
		return musicid;
	}
	public void setMusicid(int musicid) {
		this.musicid = musicid;
	}
}
