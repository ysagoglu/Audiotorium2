package com.audiotorium2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audiotorium2.entity.GenreWithMusic;
import com.audiotorium2.entity.SongView;
import com.audiotorium2.service.IMusicService;

@RestController
@RequestMapping("/music")
public class MusicController {

	@Autowired
	IMusicService musicService;

	public List<SongView> search(String searchString) throws Exception {
		return musicService.search(searchString);
	}

	public SongView getSong(int id) throws Exception {
		return musicService.getSong(id);
	}

	public List<GenreWithMusic> retriveSongsByGenre() throws Exception {
		return musicService.listSongsByGenre();
	}

	public void addToFavoriteList(int musicId, int userId) throws Exception {
		musicService.addToFavoriteList(musicId, userId);
	}

	public List<SongView> retrieveFavoriteList(int userid) {
		return musicService.retrieveFavoriteList(userid);
	}

}
