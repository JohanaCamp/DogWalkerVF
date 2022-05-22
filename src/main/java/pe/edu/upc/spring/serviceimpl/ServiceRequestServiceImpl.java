package pe.edu.upc.spring.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.ServiceRequest;
import pe.edu.upc.spring.repository.IServiceRequestRepository;
import pe.edu.upc.spring.service.IServiceRequestService;

@Service
public class ServiceRequestServiceImpl implements IServiceRequestService {

	@Autowired
	private IServiceRequestRepository dServiceRequest;

	@Override
	@Transactional
	public boolean save(ServiceRequest serviceRequest) {
		ServiceRequest objServiceRequest = dServiceRequest.save(serviceRequest);
		if (objServiceRequest == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idServiceRequest) {
		dServiceRequest.deleteById(idServiceRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ServiceRequest> listId(int idServiceRequest) {
		return dServiceRequest.findById(idServiceRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ServiceRequest> listServiceRequest() {
		return dServiceRequest.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<ServiceRequest> listServiceRequestByOwner(String idOwner) {
		return dServiceRequest.listServiceRequestByOwner(idOwner);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ServiceRequest> listServiceRequestByWalker(String idWalker) {
		return dServiceRequest.listServiceRequestByWalker(idWalker);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ServiceRequest> findServiceByDate(Date DateBegin,Date DateEnd, String d) {
		return dServiceRequest.findServiceByDate(DateBegin, DateEnd,d);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ServiceRequest> findServiceByDates(Date DateBegin,Date DateEnd) {
		return dServiceRequest.findServiceByDates(DateBegin, DateEnd);
	}
}
