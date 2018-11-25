package com.proskurnia.services.impl;

import java.sql.SQLException;
import java.util.Collection;

import com.proskurnia.VOs.Identified;
import com.proskurnia.dao.Dao;
import com.proskurnia.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericServiceImpl<T extends Identified<I>, I> implements GenericService<T, I> {

    @Autowired
    Dao<T, I> dao;

    @Override
    public Collection<T> getAll() {
        return dao.getAll();
    }

    @Override
    public T getById(I id) {
        return dao.getById(id);
    }

    @Override
    public T create(T o) throws SQLException {
        return dao.create(o);
    }

    @Override
    public void update(T o) throws SQLException {
        dao.update(o);
    }

    @Override
    public void delete(I id) throws SQLException {
        dao.delete(id);
    }
}
