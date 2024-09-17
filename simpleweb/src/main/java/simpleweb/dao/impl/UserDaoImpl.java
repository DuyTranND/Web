package simpleweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import simpleweb.configs.DBConnectMySQL;

import simpleweb.dao.IUserDao;

import simpleweb.models.UserModel;

public class UserDaoImpl implements IUserDao {
	
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public List<UserModel> findAll() {
		String sql ="select * from login";
		List<UserModel> list = new ArrayList<>(); 
		try {
			conn = new DBConnectMySQL().getDatabaseConnection(); 
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
                list.add(new UserModel(
                		rs.getInt("id"), 
                		rs.getString("username"), 
                		rs.getString("password"), 
                		rs.getString("email"), 
                		rs.getString("phone"),
                		rs.getString("images"),
                		rs.getString("fullname"),
                		rs.getInt("roleid"),
                		rs.getDate("createDate")        		                		
                		));
            }
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public UserModel findOne(int id) {
		String sql = "SELECT * FROM login WHERE id = ? ";
		
		try {
			conn = new DBConnectMySQL().getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setImages(rs.getString("images"));
				user.setEmail(rs.getString("email"));
				user.setRoleid(rs.getInt("roleid"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(UserModel user) {

		String sql = "INSERT INTO login(id, username, password, images, fullname) VALUES (?, ?, ?, ?, ?)";
			
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, user.getId());
			ps.setString(2, user.getUsername());
			ps.setString(3,user.getPassword());
			ps.setString(4,user.getImages());
			ps.setString(5, user.getFullname());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	@Override
	public UserModel findByUsername(String username) {
		String sql = "SELECT * FROM login WHERE username = ? ";
		
		try {
			conn = new DBConnectMySQL().getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setImages(rs.getString("images"));
				user.setEmail(rs.getString("email"));
				user.setRoleid(rs.getInt("roleid"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 public static void main(String[] args) {
		 	
	        IUserDao userDao = new UserDaoImpl();
	        System.out.println(userDao.findByUsername("Endy"));
	        
	    }

	
}