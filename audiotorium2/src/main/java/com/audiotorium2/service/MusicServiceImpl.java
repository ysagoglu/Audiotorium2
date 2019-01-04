package com.audiotorium2.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audiotorium2.dao.IMusicDAO;
import com.audiotorium2.dao.IUserDAO;
import com.audiotorium2.entity.GenreWithMusic;
import com.audiotorium2.entity.SongView;
import com.audiotorium2.utility.SessionUtils;

@Service
public class MusicServiceImpl implements IMusicService {

	@Autowired
	IMusicDAO musicDAO;

	@Autowired
	IUserDAO userDAO;

	public List<SongView> search(String searchString) throws Exception {

		List<SongView> songList = musicDAO.findMusic(searchString);
		if (songList == null || (songList.isEmpty())) {
			throw new Exception("There are no result found for words '" + searchString + "'");
		}
		HttpSession session = SessionUtils.getSession();

		insertLog((Integer) session.getAttribute("id"), "search");

		return songList;
	}

	@Override
	public SongView getSong(int id) throws Exception {

		SongView sv = musicDAO.getSong(id);

		if (sv == null) {
			throw new Exception("Cannot find song path.");
		}

		HttpSession session = SessionUtils.getSession();
		insertLog((Integer) session.getAttribute("id"), "play music");
		return sv;
	}

	public void insertLog(int userId, String operation) throws Exception {
		userDAO.insertLog(operation, userId);
	}

	@Override
	public List<GenreWithMusic> listSongsByGenre() throws Exception {

		return musicDAO.retrieveSongWithGenre();
	}

	@Override
	public void addToFavoriteList(int musicId, int userId) throws Exception {

		List<SongView> songs = musicDAO.retrieveFavoriteListByUserId(userId);

		if (songs != null && !songs.isEmpty()) {
			for (SongView s : songs) {
				if (s.getMusicid() == musicId) {
					throw new Exception("This song already exists in your favorite list.");
				}
			}
		}

		musicDAO.addToFavoriteList(musicId, userId);

	}

	@Override
	public List<SongView> retrieveFavoriteList(int userid) {
		return musicDAO.retrieveFavoriteListByUserId(userid);
	}
}
