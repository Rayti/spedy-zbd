package com.example.spedy.dao;

import com.example.spedy.model.User;

import java.util.List;
import java.util.UUID;

public interface SimpleDao<T> {
    boolean insert(T model);

    boolean update(T model);

    boolean delete(T model);

    List<T> selectAll();

    T select(String name);

    T select(UUID id);
}
