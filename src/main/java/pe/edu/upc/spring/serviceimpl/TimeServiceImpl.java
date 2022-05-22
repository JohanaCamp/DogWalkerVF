package pe.edu.upc.spring.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Time;
import pe.edu.upc.spring.repository.ITimeRepository;
import pe.edu.upc.spring.service.ITimeService;

@Service
public class TimeServiceImpl implements ITimeService {

	@Autowired
	private ITimeRepository dTime;
	
	@Override
	@Transactional(readOnly = true)
	public List<Time> listTime() {
		return dTime.findAll();
	}

}
