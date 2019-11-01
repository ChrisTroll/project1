package project1.services;

import project1.dao.Dao;
import project1.models.Credentials;
import project1.models.User;

public class LoginService {
	Dao dao = new Dao();
	
	public User tryLogin(Credentials cred) {
		User user = dao.login(cred);
		return user;
	}
}
