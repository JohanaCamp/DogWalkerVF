package pe.edu.upc.spring.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Personality;
import pe.edu.upc.spring.repository.IPersonalityRepository;
import pe.edu.upc.spring.service.IPersonalityService;

@Service
public class PersonalityServiceImpl implements IPersonalityService {

	@Autowired
	private IPersonalityRepository dPersonality;
	

	@Override
	@Transactional(readOnly = true)
	public List<Personality> listPersonality() {
		return dPersonality.findAll();
	}

}
