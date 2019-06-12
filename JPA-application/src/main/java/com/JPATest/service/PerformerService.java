package com.JPATest.service;

import com.JPATest.data.Performer;

import java.util.List;
import java.util.Set;

public interface PerformerService {

    void save(Performer performer);

    void delete(Long id);

    List<Performer> getAll();

    List<Performer> getAllByGroup(Long groupId);

    Set<Performer> getByParam(String param);
}
