/**
 * 
 */
package com.chaos.smsSimulation.server.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author chaos
 *
 */
public interface BaseDao<E, PK extends Serializable> {
	
	public E getById(PK id);
    
	public void deleteById(PK id);
	
	public void save(E entity);
	
	public void update(E entity);
	
	public List<E> findAll();
	
	public List<E> findByMap(Map<String, Object> paraMap);
	
	public List<E> findByProperty(final String propertyName, final Object value);
	
	public List<E> findByOrder(final String key);
	
	public Long findCount();
	
	public Long findCountByMap(Map<String, Object> map);
}
