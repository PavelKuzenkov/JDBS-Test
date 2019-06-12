package com.JPATest.service.impl;

import com.JPATest.Gender;
import com.JPATest.data.Performer;
import com.JPATest.repo.GroupRepo;
import com.JPATest.repo.PerformerRepo;
import com.JPATest.service.PerformerService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PerformerServiceImpl implements PerformerService {

    @Autowired
    private PerformerRepo performerRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Override
    public void save(Performer performer) {
        performerRepo.save(performer);
    }

    @Override
    public List<Performer> getAll() {
        return Lists.newArrayList(performerRepo.findAll());
    }

    @Override
    public void delete(Long id) {
        performerRepo.deleteById(id);
    }

    @Override
    public List<Performer> getAllByGroup(Long groupId) {
        List<Performer> result = new ArrayList<>();
        if (groupRepo.findById(groupId).isPresent()) {
            result = performerRepo.findByGroup(groupRepo.findById(groupId).get());
        }
        return result;
    }

    @Override
    public Set<Performer> getByParam(String param) {
        Set<Performer> result = new HashSet<>();
        result.addAll(performerRepo.getByFirstName("%" + param + "%"));
        result.addAll(performerRepo.getByLastName("%" + param + "%"));
        result.addAll(performerRepo.getByMiddleName("%" + param + "%"));
        if (param.equals("MALE")) {
            result.addAll(performerRepo.getByGender(Gender.MALE));
        }
        if (param.equals("FEMALE")) {
            result.addAll(performerRepo.getByGender(Gender.FEMALE));
        }
        result.addAll(performerRepo.getByRole("%" + param + "%"));
        try{
            result.addAll(performerRepo.getByAge(Integer.parseInt(param)));
        } catch (NumberFormatException nfe) {
            return result;
        }
        return result;
    }
}
