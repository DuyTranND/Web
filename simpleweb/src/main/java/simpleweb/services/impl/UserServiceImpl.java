package simpleweb.services.impl;

import simpleweb.dao.IUserDao;
import simpleweb.dao.impl.UserDaoImpl;
import simpleweb.models.UserModel;
import simpleweb.services.IUserService;

public class UserServiceImpl implements IUserService {
	IUserDao userDao = new UserDaoImpl();
	

	@Override
	public UserModel login(String username, String password) {
		
		UserModel user = this.findByUsername(username);
		 
		if (user != null && password.equals(user.getPassword())) {
			 return user;
		 }
		 return null;
	}

	@Override
	public UserModel findByUsername(String username) {
		
		return userDao.findByUsername(username);
	}
	public static void main(String[] args) {
	 	
        IUserService userDao = new UserServiceImpl();
        System.out.println(userDao.findByUsername("endy"));
      
    }
}