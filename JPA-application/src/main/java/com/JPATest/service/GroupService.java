package com.JPATest.service;

import com.JPATest.data.Group;

import java.util.List;
import java.util.Set;

public interface GroupService {

    void save(Group group);

    void delete(Long id);

    List<Group> getAll();

    Group getById(Long id);

    Set<Group> getByParam(String param);
}
