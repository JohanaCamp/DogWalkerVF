package pe.edu.upc.spring.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Walker;
import pe.edu.upc.spring.repository.IWalkerRepository;
import pe.edu.upc.spring.service.IWalkerService;

@Service
public class WalkerServiceImpl implements IWalkerService {

	@Autowired
	private IWalkerRepository wa;

	@Override
	@Transactional
	public boolean save(Walker walker) {
		Walker objRace = wa.save(walker);
		if (objRace == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Walker> list() {
		return wa.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Walker> findByEmailAndPassword(String email, String password) {
		return wa.findByEmailAndPassword(email, password);
	}

	@Override
	@Transactional(readOnly = true)
	public Walker WalkerById(String idWalker) {
		return wa.WalkerById(idWalker);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Walker> listByDistrict(String nameDistrict) {
		return wa.listByDistrict(nameDistrict);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Walker findByEmail(String email) {
		return wa.findByEmail(email);
	}

}
