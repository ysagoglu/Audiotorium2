package com.audiotorium2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.audiotorium2.entity.User;

public class UserDAO implements IUserDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String saveUser(User user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO sys.user "
				+ "(id, name, username, surname, user_type, email, password, birthdate, gender, phonenumber) VALUES (?, ?, ?,?,?,?,?,?,?,?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getUsername());
			ps.setString(4, user.getSurname());
			ps.setInt(5, user.getUserType());
			ps.setString(6, user.getEmail());
			ps.setString(7, user.getPassword());
			ps.setDate(8, new java.sql.Date(user.getBirthDate().getTime()));
			ps.setString(9, user.getGender());
			ps.setString(10, user.getPhoneNumber());
			ps.executeUpdate();
			ps.close();

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
		return "OK";
	}

	public User findByUsername(String username, String password) {

		String sql = "SELECT * FROM sys.user WHERE username = ?";
		if (password != null) {
			sql = sql + " AND password = ?";
		}
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			if (password != null)
				ps.setString(2, password);
			User user = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setEmail(rs.getString("email"));
				user.setUserType(rs.getInt("user_type"));
				user.setBirthDate(rs.getDate("birthdate"));
			}
			rs.close();
			ps.close();
			return user;
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
	public User findByEmail(String email, String password) {
		String sql = "SELECT * FROM sys.user WHERE email = ?";

		if (password != null) {
			sql = sql + " AND password = ?";
		}

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			if (password != null)
				ps.setString(2, password);
			User user = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setEmail(rs.getString("email"));
				user.setUserType(rs.getInt("user_type"));
				user.setBirthDate(rs.getDate("birthdate"));
			}
			rs.close();
			ps.close();
			return user;
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
	public void insertLog(String operation, int userId) {
		String sql = "INSERT INTO sys.log (userid, operation) values(?,?)";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setString(2, operation);
			ps.executeUpdate();
			ps.close();

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

}
