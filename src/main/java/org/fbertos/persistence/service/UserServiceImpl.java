package org.fbertos.persistence.service;

import java.util.List;

import org.fbertos.persistence.dao.UserRepository;
import org.fbertos.persistence.model.User;
import org.fbertos.persistence.search.Filter;
import org.fbertos.persistence.search.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
    private UserRepository userRepository;

	@PreAuthorize("hasAuthority('USER_CREATE')")
	public void save(User user) {
		userRepository.save(user);
	}

	@PreAuthorize("hasAuthority('USER_READ')")
	public User get(long id) {
		return userRepository.findById(id).get();
	}

    @PreAuthorize("hasAuthority('USER_READ')")
	public List<User> find(Filter filter) {
		UserSpecification spec = new UserSpecification(filter);
		Pageable page = spec.toPageable();
		return userRepository.findAll(spec, page).getContent();
	}
	
    @PreAuthorize("hasAuthority('USER_UPDATE')")
	public void update(User user) {
		userRepository.save(user);
	}

    @PreAuthorize("hasAuthority('USER_DELETE')")
	public void delete(long id) {
		userRepository.deleteById(id);
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Filter filter = new Filter("username like " + username, null, null, null);
		UserSpecification spec = new UserSpecification(filter);
		Pageable page = spec.toPageable();
		List<User> list = userRepository.findAll(spec, page).getContent();
		
		if (list != null && list.size() > 0)
			return list.get(0);
    	
        throw new UsernameNotFoundException(username);
    }	
}
