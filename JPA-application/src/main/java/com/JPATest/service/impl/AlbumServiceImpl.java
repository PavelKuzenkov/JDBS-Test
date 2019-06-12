package com.JPATest.service.impl;

import com.JPATest.data.Album;
import com.JPATest.repo.AlbumRepo;
import com.JPATest.repo.GroupRepo;
import com.JPATest.service.AlbumService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepo albumRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Override
    public void save(Album album) {
        albumRepo.save(album);
    }

    @Override
    public List<Album> getAll() {
        return Lists.newArrayList(albumRepo.findAll());
    }

    @Override
    public void delete(Long id) {
        albumRepo.deleteById(id);
    }

    @Override
    public List<Album> getAllByGroup(Long groupId) {
        List<Album> result = new ArrayList<>();
        if (groupRepo.findById(groupId).isPresent()) {
            List<Album> byGroup = albumRepo.findByGroup(groupRepo.findById(groupId).get());
            result.addAll(albumRepo.findByGroup(groupRepo.findById(groupId).get()));
        }
        return result;
    }

    @Override
    public Album getById(Long id) {
        Album result = null;
        if (albumRepo.findById(id).isPresent()) {
            result = albumRepo.findById(id).get();
        }
        return result;
    }

    @Override
    public Set<Album> getByParam(String param) {
        Set<Album> result = new HashSet<>();
        result.addAll(albumRepo.getByName("%" + param + "%"));
        if (param.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            String[] split = param.split("-");
            int year = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int day = Integer.parseInt(split[2]);
            try{
                LocalDate date = LocalDate.of(year, month, day);
                result.addAll(albumRepo.getByDate(date));
            } catch (DateTimeException dte) {
                return result;
            }
        }
        return result;
    }
}
