package com.audiotorium2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.audiotorium2.controller.MusicController;
import com.audiotorium2.entity.GenreWithMusic;
import com.audiotorium2.entity.SongView;
import com.audiotorium2.utility.SessionUtils;

@ManagedBean
@RequestScoped
@Component
@SessionScoped
public class SearchManagedBean {

	private String searchString;
	private List<SongView> songList;
	private int musicId;
	private String path;
	private String songName;
	private String artistName;
	boolean favorite =true;
	
	@Autowired
	MusicController musicController;
	
	private List<GenreWithMusic> genreList;
	private GenreWithMusic selectedGenre;
	
	public String listGenres() throws Exception {
		favorite =true;
		genreList = new ArrayList<GenreWithMusic>();
		genreList.addAll(musicController.retriveSongsByGenre()); 
		 System.out.println("qsdasd");
		return "Genres.xhtml";
	}
	
	
	public String select() {
		System.out.println(selectedGenre.getGenreId());
		songList = selectedGenre.getMusicList();
		return "search-result.xhtml";

	}
	
	public String search() {
		favorite =true;
		try {
			songList= musicController.search(searchString);
		} catch (Exception e) {
			e.printStackTrace();
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
			return null; 
		}
		return "search-result.xhtml";
	}
	
	public String playMusic() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		try {
			path =musicController.getSongPath(Integer.parseInt(params.get("musicId")));
			songName = params.get("musicName");
			artistName = params.get("artistName");
		} catch (Exception e) {
			e.printStackTrace();
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
			return null; 
		}
		return "Music.xhtml";
	}
	
	public void addToFavoriteList(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		int musicId = Integer.parseInt(params.get("musicId"));
		HttpSession session = SessionUtils.getSession();
		int userId = (Integer)session.getAttribute("id");
		try {
			musicController.addToFavoriteList(musicId, userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
			return;
		}
		RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Song has been added to your favorite list successfully."));
	}
	
	public String showFavoriteList() {
		favorite = false;
		HttpSession session = SessionUtils.getSession();
		int userId = (Integer)session.getAttribute("id");
		
		songList = musicController.retrieveFavoriteList(userId);
		
		return "favorite-result.xhtml";
		
	}
	
	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}


	public List<SongView> getSongList() {
		return songList;
	}


	public void setSongList(List<SongView> songList) {
		this.songList = songList;
	}

	public int getMusicId() {
		return musicId;
	}

	public void setMusicId(int musicId) {
		this.musicId = musicId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}


	public List<GenreWithMusic> getGenreList() {
		return genreList;
	}


	public void setGenreList(List<GenreWithMusic> genreList) {
		this.genreList = genreList;
	}


	public GenreWithMusic getSelectedGenre() {
		return selectedGenre;
	}


	public void setSelectedGenre(GenreWithMusic selectedGenre) {
		this.selectedGenre = selectedGenre;
	}


	public boolean isFavorite() {
		return favorite;
	}


	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	
}
