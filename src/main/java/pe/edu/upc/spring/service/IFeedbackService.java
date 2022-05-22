package pe.edu.upc.spring.service;

import java.util.List;

import pe.edu.upc.spring.model.Feedback;


public interface IFeedbackService {
	public boolean save(Feedback feedback);
	public List<Feedback> list();
	public List<Feedback> FeedbackByIdWalker(String idWalker);
}

