package pe.edu.upc.spring.serviceimpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pe.edu.upc.spring.model.Owner;
import pe.edu.upc.spring.repository.IOwnerRepository;
import pe.edu.upc.spring.service.IOwnerService;

@Service
public class OwnerServiceImpl implements IOwnerService {

	@Autowired
	private IOwnerRepository dOwner;
	
	@Override
	@Transactional
	public boolean save(Owner owner) {
		Owner objOwner= dOwner.save(owner);
		if (objOwner == null)
			return false;
		else
			return true;
	}


	@Override
	@Transactional(readOnly=true)
	public List<Owner> findByEmailAndPassword(String email, String password) {
		return dOwner.findByEmailAndPassword(email, password);
	}
	

	@Override
	@Transactional(readOnly=true)
	public Owner findByEmail(String email) {
		return dOwner.findByEmail(email);
	}

}
