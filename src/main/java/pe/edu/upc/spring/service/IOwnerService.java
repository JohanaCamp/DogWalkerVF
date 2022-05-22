package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Owner;


public interface IOwnerService {
	public boolean save(Owner owner);
	public List<Owner> findByEmailAndPassword(String email, String password);
	public Owner findByEmail(String email);

}

