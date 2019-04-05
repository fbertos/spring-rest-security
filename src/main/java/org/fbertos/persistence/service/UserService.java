package org.fbertos.persistence.service;

import java.util.List;

import org.fbertos.persistence.model.User;
import org.fbertos.persistence.search.Filter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {
	public void save(User user);
	public User get(long id);
	public List<User> find(Filter filter);
	public void update(User user);
	public void delete(long id);
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
