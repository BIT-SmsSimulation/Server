/**
 * Implementation of BaseDao.
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

package com.chaos.smsSimulation.server.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.chaos.smsSimulation.server.dao.BaseDao;

/**
 * @author chaos
 *
 */
public abstract class BaseDaoImpl<E, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<E, PK> {

	private Class<E> entityClass;
	
	public BaseDaoImpl() {  
		Type genType = getClass().getGenericSuperclass();  
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
		entityClass = (Class<E>) params[0];  
	}
	
	public E getById(PK id) {
		// TODO Auto-generated method stub
		return (E) getHibernateTemplate().get(entityClass, id);
	}

	public void deleteById(PK id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(getHibernateTemplate().get(entityClass, id));
	}

	public void save(E entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(entity);
	}

	public void update(E entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(entity);
	}

	public List<E> findAll() {
		// TODO Auto-generated method stub
		return (List<E>) getHibernateTemplate().find("from " + entityClass.getName());
	}

	public List<E> findByMap(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		String queryString = "from " + entityClass.getName() + " e where";
		ArrayList<Object> values = new ArrayList<Object>();
		
		String orderBy = null;
		Boolean desc = false;
		List<Map<String, Object>> ranges = null;
		if (paraMap.containsKey("KW_ORDER_BY")) {
			orderBy = (String) paraMap.remove("KW_ORDER_BY");
		}
		if (paraMap.containsKey("KW_DESC")) {
			desc = (Boolean) paraMap.remove("KW_DESC");
		}
		if (paraMap.containsKey("KW_RANGE")) {
			ranges = (List<Map<String, Object>>)paraMap.remove("KW_RANGE");
		}
		
		int flag = 0;
		Set<Map.Entry<String, Object>> set = paraMap.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = it.next();
			if (flag == 0) {
				flag = 1;
			} else if (flag == 1) {
				queryString += " and";
			}
			queryString += " e." + entry.getKey() + "=?";
			
			values.add(entry.getValue());
		}
		
		if (ranges != null) {
			for (Map<String, Object> range : ranges) {
				String key = (String) range.get("key");
				Object small = range.get("small");
				Object big = range.get("big");
				
				if (key != null) {
					if (small != null) {
						queryString += " and e." + key + ">=?";
						values.add(small);
					}
					if (big != null) {
						queryString += " and e." + key + "<=?";
						values.add(big);
					}
				}
			}
		}
		
		if (orderBy != null) {
			queryString += " order by " + orderBy;
			
			if (desc) {
				 queryString += " desc";
			}
		}
		
		return (List<E>) getHibernateTemplate().find(queryString, values.toArray());
	}

	public List<E> findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		String queryString = "from " + entityClass.getName() + " as model where model."
				+ propertyName + "= ?";
		return (List<E>) getHibernateTemplate().find(queryString, value);
	}

	public List<E> findByOrder(String key) {
		// TODO Auto-generated method stub
		String queryStr = "from " + entityClass.getName() +" order by ?";
		
		return (List<E>) getHibernateTemplate().find(queryStr, key);
	}

	public Long findCount() {
		// TODO Auto-generated method stub
		return (Long) getHibernateTemplate().find("select count(*) from " + entityClass.getName()).get(0);
	}

	public Long findCountByMap(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		String queryString = "select count(*) from " + entityClass.getName() + " e where";
		ArrayList<Object> values = new ArrayList<Object>();
		
		String orderBy = null;
		Boolean desc = false;
		List<Map<String, Object>> ranges = null;
		if (paraMap.containsKey("KW_ORDER_BY")) {
			orderBy = (String) paraMap.remove("KW_ORDER_BY");
		}
		if (paraMap.containsKey("KW_DESC")) {
			desc = (Boolean) paraMap.remove("KW_DESC");
		}
		if (paraMap.containsKey("KW_RANGE")) {
			ranges = (List<Map<String, Object>>) paraMap.remove("KW_RANGE");
		}
		
		int flag = 0;
		Set<Map.Entry<String, Object>> set = paraMap.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = it.next();
			if (flag == 0) {
				flag = 1;
			} else if (flag == 1) {
				queryString += " and";
			}
			queryString += " e." + entry.getKey() + "=?";
			
			values.add(entry.getValue());
		}
		
		if (ranges != null) {
			for (Map<String, Object> range : ranges) {
				String key = (String) range.get("key");
				Object small = range.get("small");
				Object big = range.get("big");
				
				if (key != null) {
					if (small != null) {
						queryString += " and e." + key + ">=?";
						values.add(small);
					}
					if (big != null) {
						queryString += " and e." + key + "<=?";
						values.add(big);
					}
				}
			}
		}
		
		if (orderBy != null) {
			queryString += " order by " + orderBy;
			
			if (desc) {
				 queryString += " desc";
			}
		}
		
		return (Long) getHibernateTemplate().find(queryString, values.toArray()).get(0);
	}
}
