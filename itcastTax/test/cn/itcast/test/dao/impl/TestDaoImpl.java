package cn.itcast.test.dao.impl;

import java.io.Serializable;

import org.junit.Test;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.test.dao.TestDao;
import cn.itcast.test.entity.Person;

public class TestDaoImpl extends HibernateDaoSupport implements TestDao {

	@Override
	public void save(Person person) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(person);
		
	}

	@Override
	public Person findPersonById(Serializable id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(Person.class,id);
	}

	
}
