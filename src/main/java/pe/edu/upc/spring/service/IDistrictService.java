package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.District;

public interface IDistrictService {
	public List<District> listDistrict();
	public List<District> searchByName(String name);	
}
