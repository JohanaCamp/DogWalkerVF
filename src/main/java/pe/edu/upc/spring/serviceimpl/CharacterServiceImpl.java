package pe.edu.upc.spring.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Character;
import pe.edu.upc.spring.repository.ICharacterRepository;
import pe.edu.upc.spring.service.ICharacterService;

@Service
public class CharacterServiceImpl implements ICharacterService {

	@Autowired
	private ICharacterRepository dCharacter;
	
	@Override
	@Transactional(readOnly = true)
	public List<Character> listCharacter() {
		return dCharacter.findAll();
	}

}
