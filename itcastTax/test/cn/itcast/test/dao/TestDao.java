package cn.itcast.test.dao;

import java.io.Serializable;

import cn.itcast.test.entity.Person;

public interface TestDao {
	//新增用户
	public void save(Person person);
	//根据id查询用户
	public Person findPersonById(Serializable id);
		
}
