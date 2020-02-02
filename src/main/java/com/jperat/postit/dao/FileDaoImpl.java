package com.jperat.postit.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jperat.postit.model.File;

@Repository("fileDao")
@Transactional
public class FileDaoImpl implements FileDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public File findByName(String name) {
		return getSession().createQuery("FROM File f WHERE f.name=:name", File.class).setParameter("name", name).uniqueResult();
	}
	
	public void save(File file) {
		getSession().save(file);
	}

	public void remove(File file) {
		getSession().remove(file);
	}

}
