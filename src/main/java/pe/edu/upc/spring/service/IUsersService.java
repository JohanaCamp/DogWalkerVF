package pe.edu.upc.spring.service;

import pe.edu.upc.spring.model.Users;

public interface IUsersService {
	public boolean save(Users users);
	public Users findByUsername(String username);

}

