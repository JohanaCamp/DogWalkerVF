package pe.edu.upc.spring.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Status;
import pe.edu.upc.spring.repository.IStatusRepository;
import pe.edu.upc.spring.service.IStatusService;

@Service
public class StatusServiceImpl implements IStatusService {

	@Autowired
	private IStatusRepository dStatus;
	
	@Override
	@Transactional(readOnly = true)
	public List<Status> listStatus() {
		return dStatus.findAll();
	}

}
