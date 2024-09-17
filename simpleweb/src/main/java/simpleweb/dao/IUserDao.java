package simpleweb.dao;

import java.util.List;

import simpleweb.models.UserModel;

public interface IUserDao {
	
	List<UserModel> findAll();
	
	UserModel findOne(int id);
	
	UserModel findByUsername(String username);
	
	void insert(UserModel user);
	

}