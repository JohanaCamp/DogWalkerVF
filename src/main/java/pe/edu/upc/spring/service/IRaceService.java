package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Race;

public interface IRaceService {
	public boolean grabar(Race race); // cambiar a ingles save, delete listDog
	public void eliminar(int idRace);
	public Optional<Race> listarId(int idRace);
	public List<Race> listRace();
	public List<Race> buscarNombre(String name);	
}
