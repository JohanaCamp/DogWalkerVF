package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pe.edu.upc.spring.model.Dog;
import pe.edu.upc.spring.repository.IDogRepository;
import pe.edu.upc.spring.service.IDogService;

@Service
public class DogServiceImpl implements IDogService {////

	@Autowired
	private IDogRepository dDog;
	
	@Override
	@Transactional
	public boolean save(Dog dog) {
		Dog objDog= dDog.save(dog);
		if (objDog == null)
			return false;
		else
			return true;
	}
	@Override
	@Transactional
	public void delete(int idDog) {
		dDog.deleteById(idDog);
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Optional<Dog> listById(int idDog)  {
		return dDog.findById(idDog);
	}

	
	@Override
	@Transactional(readOnly=true)
	public List<Dog> ListDogByOwner(String idOwner) {
		return dDog.ListDogByOwner(idOwner);
	}

}
