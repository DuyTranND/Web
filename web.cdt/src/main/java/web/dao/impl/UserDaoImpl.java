package web.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import web.configs.DBConnectMySQL;
import web.dao.IUserDao;
import web.models.UserModel;

public class UserDaoImpl extends DBConnectMySQL implements IUserDao {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	public PreparedStatement findMissingIdStmt = null;
	
	@Override
	public List<UserModel> findAll() {
	    String sql = "select * from login";  // Change 'web' to the correct table name
	    List<UserModel> list = new ArrayList<>();

	    try {
	        conn = super.getDatabaseConnection();
	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            list.add(new UserModel(rs.getInt("id"), rs.getString("user"), rs.getString("email"),
	                    rs.getString("fullname"), rs.getString("image"), rs.getString("password")));
	        }
	        return list;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();  // Return an empty list if an exception occurs
	    }
	}


	@Override
	public UserModel findById(int id) {
		// TODO Auto-generated method stub
		String sql = "select * from login WHERE id = ?";
		try {
			conn = super.getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				return new UserModel(rs.getInt("id"),rs.getString("user"), rs.getString("email"),
						rs.getString("fullname"), rs.getString("image"), rs.getString("password"));	
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void insert(UserModel user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO login (id,fullname,image,user,password,email) VALUES (?,?,?,?,?,?)";
		try {
		conn = super.getDatabaseConnection();
	    String findMissingIdSQL = 
	            "SELECT COALESCE(MIN(t1.id + 1), 1) AS missing_id " +
	            "FROM login t1 " +
	            "LEFT JOIN login t2 ON t1.id + 1 = t2.id " +
	            "WHERE t2.id IS NULL";
        findMissingIdStmt = conn.prepareStatement(findMissingIdSQL);
		
			
			ps = conn.prepareStatement(sql);
			rs = findMissingIdStmt.executeQuery();

			while (rs.next()) {
				int missingId = rs.getInt("missing_id");

                // Chèn thông tin vào ID trống
				ps.setInt(1,missingId);
				ps.setString(2,user.getFullname());
				ps.setString(3,user.getImages());
				ps.setString(4,user.getUsername());
				ps.setString(5,user.getPassword());
				ps.setString(6,user.getEmail());
				
				ps.executeUpdate();
			
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public UserModel findByUsername(String username) {
        String sql = "SELECT * FROM login WHERE user = ?";
        try {
            conn = super.getDatabaseConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new UserModel(rs.getInt("id"), rs.getString("user"), rs.getString("email"),
                        rs.getString("fullname"), rs.getString("image"), rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
	public static void main(String[] args) {
	    UserDaoImpl userDao = new UserDaoImpl();
	    
	    // Test database connection
	    try (Connection conn = userDao.getDatabaseConnection()) {
	        System.out.println("Database connection successful");
	    } catch (SQLException e) {
	        System.out.println("Database connection failed: " + e.getMessage());
	        return;
	    }

	    // Insert a new user
	    UserModel newUser = new UserModel("a", "1@gmail", "ND", "null", "123");
	    userDao.insert(newUser);
	    
	    // Try to fetch the newly inserted user
	    UserModel fetchedUser = userDao.findById(newUser.getId());
	    if (fetchedUser != null) {
	        System.out.println("User successfully inserted and fetched: " + fetchedUser);
	    } else {
	        System.out.println("Failed to fetch newly inserted user");
	    }
	    
	    // List all users
	    List<UserModel> list = userDao.findAll();
	    System.out.println("Total users: " + list.size());
	    for (UserModel user : list) {
	        System.out.println(user);
	    }
	}

}
