package pe.edu.upc.spring.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Feedback;
import pe.edu.upc.spring.repository.IFeedbackRepository;
import pe.edu.upc.spring.service.IFeedbackService;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

	@Autowired
	private IFeedbackRepository dFeedback;
	

	@Override
	@Transactional
	public boolean save(Feedback feedback) {
		Feedback objFeedback= dFeedback.save(feedback);
		if (objFeedback == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Feedback> list() {
		return dFeedback.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Feedback> FeedbackByIdWalker(String idWalker) {
		return dFeedback.FeedbackByIdWalker(idWalker);
	}


}
