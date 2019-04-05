package org.fbertos.persistence.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.fbertos.persistence.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification implements Specification<User> {
	private static final long serialVersionUID = 7984346165274886297L;

	private Filter filter;
 
    @Override
    public Predicate toPredicate
      (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    	Predicate predicate = null;
    	
    	if (this.filter.getQuery() != null) {
    		String[] s = this.filter.getQuery().split("\\+");
    		
    		for (int i=0; i<s.length; i++) {
    			String[] p = s[i].split(" ");
    			
    			if (p[1].trim().equals("like")) {
    				if (predicate == null)
    					predicate = builder.like(root.<String> get(p[0].trim()), p[2]);
    				else
    					predicate = builder.and(predicate, builder.like(root.<String> get(p[0].trim()), p[2]));
    			}
    			else if (p[1].trim().equals("=")) {
    				if (predicate == null)
    					predicate = builder.equal(root.<String> get(p[0].trim()), p[2]);
    				else
    					predicate = builder.and(predicate, builder.equal(root.<String> get(p[0].trim()), p[2]));    				
    			}
    		}
    	}
    	
    	return predicate;
    }
    
	public Pageable toPageable() {
		int page = 0;
		int items = 500;
		
		if (this.filter.getPage() != null)
			page = new Integer(this.filter.getPage()).intValue();
		
		if (this.filter.getItemsperpage() != null)
			items = new Integer(this.filter.getItemsperpage()).intValue();
		
		if (this.filter.getOrder() != null) {
			String[] p = this.filter.getOrder().split(" ");
			return PageRequest.of(page, items, Sort.Direction.fromString(p[1]), p[0]);
		}
		
		return PageRequest.of(page, items);	
    }

	public UserSpecification(Filter filter) {
		super();
		this.filter = filter;
	}
}