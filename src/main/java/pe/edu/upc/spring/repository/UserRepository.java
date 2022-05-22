package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.model.Walker;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	
	@Query("from Users u where u.username = :username")
	public Users findByUsername(@Param("username")String username);
	

}

//fre