package com.proskurnia.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.proskurnia.VOs.Identified;

public interface DAO<T extends Identified<I>, I> {

	Collection<T> getAll();
	
	T getById(I id);
	
	T create(T o) throws SQLException;
	
	void update(T o) throws SQLException;
	
	void delete(I id) throws SQLException;
}

enum QueryType {
	INSERT, UPDATE
}