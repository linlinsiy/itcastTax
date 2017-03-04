package cn.itcast.test.service;

import java.io.Serializable;

import cn.itcast.test.entity.Person;

public interface TestService {
	
	public void say();

	//新增用户
	public void save(Person person);
	//根据id查询用户
	public Person findPersonById(Serializable id);
	
}
