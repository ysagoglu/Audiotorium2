package com.audiotorium2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import com.audiotorium2.entity.GenreWithMusic;
import com.audiotorium2.entity.SongView;

public class MusicDAO implements IMusicDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<SongView> findMusic(String name) {

		String sql = "select m.id id, m.name musicname ,a.name artistname, g.genre_name from sys.music m, sys.artist a, sys.music_genre mg, sys.genre g where m.artistid = a.id "
				+ " and mg.genre_id = g.id and mg.music_id = m.id and (m.name like ?  or a.name like  ? or m.tag like ? or g.genre_name like ?) ";

		Connection conn = null;
		List<SongView> songList = new ArrayList<SongView>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			ps.setString(3, "%" + name + "%");
			ps.setString(4, "%" + name + "%");

			SongView song = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				song = new SongView();
				song.setMusicid(rs.getInt("id"));
				song.setMusicName(rs.getString("musicname"));
				song.setArtistName(rs.getString("artistname"));
				song.setGenre(rs.getString("genre_name"));
				songList.add(song);
			}
			rs.close();
			ps.close();
			return songList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	@Override
	public SongView getSong(int id) {
		String sql = "select m.musicUrl, m.id musicid,m.name, a.name artistname,a.information, a.profileUrl from sys.music m , sys.artist a where m.artistid= a.id and m.id = ?";
		Connection conn = null;
		SongView sv = new SongView();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				sv.setMusicid(rs.getInt("musicid"));
				sv.setMusicName(rs.getString("name"));
				sv.setArtistName(rs.getString("artistname"));
				sv.setSongUrl(rs.getString("musicUrl"));
				sv.setArtistInfo(rs.getString("information"));
				sv.setArtistProfileUrl(rs.getString("profileUrl"));

			}
			rs.close();
			ps.close();
			return sv;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}
	}

	@Override
	public List<GenreWithMusic> retrieveSongWithGenre() {
		String sql = "select a.name artistname,g.genre_name,mg.genre_id, m.* from sys.music m, sys.genre g,  sys.music_genre mg, sys.artist a where a.id = m.artistid and m.id= mg.music_id and g.id = mg.genre_id";

		Connection conn = null;
		String url = null;
		HashMap<Integer, GenreWithMusic> genreMusicMap = new HashMap<Integer, GenreWithMusic>();
		List<GenreWithMusic> gmList = new ArrayList<GenreWithMusic>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				int genreId = rs.getInt("genre_id");
				String genreName = rs.getString("genre_name");

				if (!genreMusicMap.containsKey(genreId)) {
					GenreWithMusic gm = new GenreWithMusic();
					gm.setGenreId(genreId);
					gm.setGenreName(genreName);
					List<SongView> musicLst = new ArrayList<SongView>();
					SongView m = new SongView();

					m.setMusicid(rs.getInt("id"));
					m.setMusicName(rs.getString("name"));
					m.setArtistName(rs.getString("musicUrl"));
					m.setArtistName(rs.getString("artistname"));

					musicLst.add(m);

					gm.setMusicList(musicLst);
					genreMusicMap.put(genreId, gm);

				} else {
					GenreWithMusic gm = new GenreWithMusic();
					gm.setGenreId(genreId);
					gm.setGenreName(genreName);

					SongView m = new SongView();

					m.setMusicid(rs.getInt("id"));
					m.setMusicName(rs.getString("name"));
					m.setArtistName(rs.getString("musicUrl"));
					m.setArtistName(rs.getString("artistname"));

					GenreWithMusic genreWithMusic = genreMusicMap.get(genreId);
					genreWithMusic.getMusicList().add(m);
					genreMusicMap.put(genreId, genreWithMusic);

				}

			}
			rs.close();
			ps.close();

			for (GenreWithMusic gm : genreMusicMap.values()) {
				gmList.add(gm);
			}
			return gmList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}
	}

	@Override
	public void addToFavoriteList(int musicId, int userId) {
		String sql = "insert into sys.user_music (userid,musicid) values (?,?)";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, musicId);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}

	}

	@Override
	public List<SongView> retrieveFavoriteListByUserId(int userId) {
		String sql = "select g.genre_name, a.name artistname,m.* from sys.music m, sys.user_music um, sys.artist a, sys.genre g, sys.music_genre mg where a.id = m.artistid and um.musicid=m.id and um.userid = ? and g.id = mg.genre_id and mg.music_id = m.id";

		Connection conn = null;
		List<SongView> swList = new ArrayList<SongView>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SongView sw = new SongView();
				sw.setArtistName(rs.getString("artistname"));
				sw.setMusicid(rs.getInt("id"));
				sw.setMusicName(rs.getString("name"));
				sw.setGenre(rs.getString("genre_name"));
				swList.add(sw);
			}
			rs.close();
			ps.close();
			return swList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

				}
			}
		}

	}

}
