package com.JPATest.service.impl;

import com.JPATest.data.Group;
import com.JPATest.repo.GroupRepo;
import com.JPATest.service.GroupService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepo repo;

    @Override
    public void save(Group group) {
        repo.save(group);
    }

    @Override
    public List<Group> getAll() {
        return Lists.newArrayList(repo.findAll());
    }

    @Override
    public Group getById(Long id) {
        Group result = null;
        if (repo.findById(id).isPresent()) {
            result = repo.findById(id).get();
        }
        return result;
    }

    @Override
    public Set<Group> getByParam(String param) {
        Set<Group> result = new HashSet<>();
        result.addAll(repo.getByName("%" + param + "%"));
        result.addAll(repo.getByCountry("%" + param + "%"));
        result.addAll(repo.getByGenre("%" + param + "%"));
        return result;
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
