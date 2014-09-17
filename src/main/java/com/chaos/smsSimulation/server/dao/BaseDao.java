/**
 * Interface for base DAO.
 * 
 * Copyright (c) 2014 BIT-SmsSimulation.
 * 
 * This file is part of BIT-SmsSimulation.
 * 
 * BIT-SmsSimulation is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * BIT-SmsSimulation is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with BIT-SmsSimulation.  If not, see <http://www.gnu.org/licenses/>.
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
