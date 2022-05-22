package pe.edu.upc.spring.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.District;
import pe.edu.upc.spring.repository.IDistrictRepository;
import pe.edu.upc.spring.service.IDistrictService;

@Service
public class DistrictServiceImpl implements IDistrictService {

	@Autowired
	private IDistrictRepository dDistrict;
	

	@Override
	@Transactional(readOnly = true)
	public List<District> listDistrict() {
		return dDistrict.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<District> searchByName(String name) {
		return dDistrict.searchByName(name);
	}

}
