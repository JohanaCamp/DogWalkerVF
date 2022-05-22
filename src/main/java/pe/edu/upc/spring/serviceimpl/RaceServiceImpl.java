package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Race;
import pe.edu.upc.spring.repository.IRaceRepository;
import pe.edu.upc.spring.service.IRaceService;

@Service
public class RaceServiceImpl implements IRaceService {

	@Autowired
	private IRaceRepository dRace;
	
	@Override
	@Transactional
	public boolean grabar(Race race) {
		Race objRace = dRace.save(race);
		if (objRace == null)
			return false;
		else
			return true;
	}



	@Override
	@Transactional
	public void eliminar(int idRace) {
		dRace.deleteById(idRace);		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Race> listarId(int idRace) {
		return dRace.findById(idRace);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Race> listRace() {
		return dRace.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Race> buscarNombre(String name) {
		return dRace.buscarNombre(name);
	}

}
