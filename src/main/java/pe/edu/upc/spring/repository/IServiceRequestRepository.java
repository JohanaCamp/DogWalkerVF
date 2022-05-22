package pe.edu.upc.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.ServiceRequest;

@Repository
public interface IServiceRequestRepository extends JpaRepository<ServiceRequest, Integer> {
	
	@Query("from ServiceRequest s where CAST(s.owner.idOwner AS string) like %:idOwner%")
	List<ServiceRequest> listServiceRequestByOwner(@Param("idOwner")String idOwner);
	
	@Query("from ServiceRequest s where CAST(s.walker.idWalker AS string) like %:idWalker%")
	List<ServiceRequest> listServiceRequestByWalker(@Param("idWalker")String idWalker);

	@Query("from ServiceRequest s where (s.dateService between :dateBegin and :dateEnd) and s.walker.district.name like %:d% ")
	List<ServiceRequest> findServiceByDate(@Param("dateBegin") Date DateBegin,@Param("dateEnd") Date DateEnd, @Param("d") String d);	

	@Query("from ServiceRequest s where (s.dateService between :dateBegin and :dateEnd) ")
	List<ServiceRequest> findServiceByDates(@Param("dateBegin") Date DateBegin,@Param("dateEnd") Date DateEnd);	

	
}
