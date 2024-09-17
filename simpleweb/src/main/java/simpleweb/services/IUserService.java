package simpleweb.services;

import simpleweb.models.UserModel;

public interface IUserService {
	 UserModel login(String username, String password);
	 UserModel findByUsername(String username);
}
