package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Walker;

public interface IWalkerService {
	public boolean save(Walker walker);
	public List<Walker> list();
	public List<Walker> findByEmailAndPassword(String email, String password);
	public Walker WalkerById(String idWalker);
	public List<Walker> listByDistrict(String nameDistrict);
	public Walker findByEmail(String email);

}
