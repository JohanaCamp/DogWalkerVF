package pe.edu.upc.spring.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Role;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.repository.UserRepository;
import pe.edu.upc.spring.service.IUsersService;

@Service
public class JpaUserDetailsService implements UserDetailsService,IUsersService{

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = userRepository.findByUsername(username);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(user!= null) {
			for (Role role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getRol()));
			}
			return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);

		}else {
			return new User(username, username, false, false, false, false, authorities);
			
		}
	}
	
	@Override
	@Transactional
	public boolean save(Users users) {
		Users objUsers= userRepository.save(users);
		if (objUsers == null)
			return false;
		else
			return true;
	}


	@Override
	@Transactional(readOnly = true)
	public Users findByUsername(String username) {
		
		Users user = userRepository.findByUsername(username);
		return user;
	}

}
