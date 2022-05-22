package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.District;

@Repository
public interface IDistrictRepository extends JpaRepository<District, Integer>{
	@Query("from District r where r.name like %:name%")
	List<District> searchByName(@Param("name") String name);
}
