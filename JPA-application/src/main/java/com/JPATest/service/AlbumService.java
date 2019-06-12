package com.JPATest.service;

import com.JPATest.data.Album;
import com.JPATest.data.Group;

import java.util.List;
import java.util.Set;

public interface AlbumService {

    void save(Album album);

    void delete(Long id);

    List<Album> getAll();

    List<Album> getAllByGroup(Long groupId);

    Album getById(Long id);

    Set<Album> getByParam(String param);
}
