package com.JPATest.service.impl;

import com.JPATest.data.Composition;
import com.JPATest.repo.AlbumRepo;
import com.JPATest.repo.CompositionRepo;
import com.JPATest.service.CompositionService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CompositionServiceImpl implements CompositionService {

    @Autowired
    private CompositionRepo compositionRepo;

    @Autowired
    private AlbumRepo albumRepo;

    @Override
    public void save(Composition composition) {
        compositionRepo.save(composition);
    }

    @Override
    public List<Composition> getAll() {
        return Lists.newArrayList(compositionRepo.findAll());
    }

    @Override
    public void delete(Long id) {
        compositionRepo.deleteById(id);
    }

    @Override
    public List<Composition> getAllByAlbum(Long albumId) {
        List<Composition> result = new ArrayList<>();
        if (albumRepo.findById(albumId).isPresent()) {
            result = compositionRepo.findByAlbum(albumRepo.findById(albumId).get());
        }
        return result;
    }

    @Override
    public Set<Composition> getByParam(String param) {
        Set<Composition> result = new HashSet<>();
        result.addAll(compositionRepo.getByName("%" + param + "%"));
        if (param.matches("^\\d+\\.?\\d{2}$")) {
            try {
                double parseDouble = Double.parseDouble(param);
                result.addAll(compositionRepo.getByDuration(parseDouble));
            } catch (NumberFormatException nfe) {
                return result;
            }
        }
        return result;
    }
}
