package com.audiotorium2.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audiotorium2.dao.IMusicDAO;
import com.audiotorium2.dao.IUserDAO;
import com.audiotorium2.entity.SongView;
import com.audiotorium2.utility.SessionUtils;

@Service
public class MusicServiceImpl implements IMusicService{

	@Autowired
	IMusicDAO musicDAO;
	
	@Autowired
	IUserDAO userDAO;
	
	
	public List<SongView> search(String searchString) throws Exception {
		
		List<SongView> songList = musicDAO.findMusic(searchString);
		if(songList == null || (songList.isEmpty())) {
			throw new Exception("There are no result found for words '" + searchString + "'" );
		}
		HttpSession session = SessionUtils.getSession();
		
		insertLog((Integer)session.getAttribute("id"), "search");
		
		return songList;
	}


	@Override
	public String getSongPath(int id) throws Exception {
		
		String songPath = musicDAO.getSongPath(id);
		
		if(songPath == null) {
			throw new Exception("Cannot find song path.");
		}
		
		HttpSession session = SessionUtils.getSession();
		insertLog((Integer)session.getAttribute("id"), "play music");
		return musicDAO.getSongPath(id);
	}
	
	public void insertLog(int userId, String operation) throws Exception {
		userDAO.insertLog(operation, userId);
	}
}
