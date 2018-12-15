package com.audiotorium2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.audiotorium2.entity.SongView;

public class MusicDAO implements IMusicDAO{
	
private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<SongView> findMusic(String name) {
		
		String sql = "select m.id id, m.name musicname ,a.name artistname, m.genre genre from sys.music m, sys.artist a where m.artistid = a.id "
				+ "and (m.name like ? or m.genre like ? or a.name like  ? or m.tag like ? )";
		
		Connection conn = null;
		List<SongView> songList = new ArrayList<SongView>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +name+ "%");
			ps.setString(2, "%" +name+ "%");
			ps.setString(3, "%" +name+ "%");
			ps.setString(4, "%" +name+ "%");
			
			SongView song = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				song = new SongView();
				song.setMusicid(rs.getInt("id"));
				song.setMusicName(rs.getString("musicname"));
				song.setArtistName(rs.getString("artistname"));
				song.setGenre(rs.getString("genre"));
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
				} catch (SQLException e) {}
			}
		}
		
	}

	@Override
	public String getSongPath(int id) {
		String sql = "select musicUrl from sys.music where id = ?";
		Connection conn = null;
		String url = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				url = rs.getString("musicUrl");
				
			}
			rs.close();
			ps.close();
			return url;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

}
