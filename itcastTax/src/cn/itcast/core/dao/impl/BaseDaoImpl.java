package cn.itcast.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.util.PageResult;
import cn.itcast.core.util.QueryHelper;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	Class<T> clazz;
	public BaseDaoImpl(){
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		clazz = (Class<T>)pt.getActualTypeArguments()[0];
	}
	
	public Session getCurrentSession(){
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
	
	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findObjectById(id));
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<T> findObjects() {
		Query query = getSession().createQuery("FROM "+clazz.getSimpleName());
		return query.list();
	}

	@Override
	public T findObjectById(Serializable id) {
		
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findObjects(String hql, List<Object> parameters) {
		Query query = getCurrentSession().createQuery(hql);
		if(parameters != null){
			for(int i=0;i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}

	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		Query query = getCurrentSession().createQuery(queryHelper.getListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i=0;i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}

	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		Query query = getCurrentSession().createQuery(queryHelper.getListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i=0;i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		//设置记录起始索引号 
		if(pageNo < 1) pageNo = 1;
		query.setFirstResult((pageNo-1)*pageSize);
		//本次查询记录大小
		query.setMaxResults(pageSize);
		List items = query.list();
		
		//总记录数
		Query countQuery = getCurrentSession().createQuery(queryHelper.getCountHql());
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (Long) countQuery.uniqueResult();
		
		return new PageResult(totalCount,pageNo,pageSize,items);
	}

	
}
