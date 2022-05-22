package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Dog;


public interface IDogService {
	public boolean save(Dog dog);
	public void delete(int idDog);
	public Optional<Dog> listById(int idDog);
	public List<Dog> ListDogByOwner(String idOwner);
}

