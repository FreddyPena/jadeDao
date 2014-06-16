/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.js.jd.models.dao;

import com.js.jd.exception.BussinessException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Fred Peña
 * @param <T>
 * @param <ID>
 */
public interface GenericDao<T, ID extends Serializable> {

    void saveOrUpdate(T entity) throws BussinessException;

    T read(ID id) throws BussinessException;//NOPMD

    void remove(ID id) throws BussinessException;//NOPMD

    List<T> findAll() throws BussinessException;

}
