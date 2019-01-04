package com.audiotorium2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Music {

	@Id
	private int id;
	private String name;
	private int artistid;
	private String genre;
	private String musicUrl;
	private String coverUrl;
	private int listenCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getArtistid() {
		return artistid;
	}

	public void setArtistid(int artistid) {
		this.artistid = artistid;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public int getListenCount() {
		return listenCount;
	}

	public void setListenCount(int listenCount) {
		this.listenCount = listenCount;
	}
}
