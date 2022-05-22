package pe.edu.upc.spring.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Dog;


@Repository
public interface IDogRepository extends JpaRepository<Dog, Integer>{
	

	@Query("from Dog d where CAST(d.owner.idOwner AS string) like %:idOwner%")
	List<Dog> ListDogByOwner(@Param("idOwner")String idOwner);
	
}
