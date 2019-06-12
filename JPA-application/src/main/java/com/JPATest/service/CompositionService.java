package com.JPATest.service;

import com.JPATest.data.Composition;

import java.util.List;
import java.util.Set;

public interface CompositionService {

    void save(Composition composition);

    void delete(Long id);

    List<Composition> getAll();

    List<Composition> getAllByAlbum(Long albumId);

    Set<Composition> getByParam(String param);
}
