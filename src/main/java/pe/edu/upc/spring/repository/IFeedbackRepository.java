package pe.edu.upc.spring.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Feedback;


@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, Integer>{
	
	@Query("from Feedback f where CAST(f.walker.idWalker AS string) like %:idWalker%")
	List<Feedback> FeedbackByIdWalker(@Param("idWalker")String idWalker);

}
