package com.JPATest.repo;

import com.JPATest.data.Album;
import com.JPATest.data.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlbumRepo extends PagingAndSortingRepository<Album, Long> {

    List<Album> findByGroup(Group group);

    @Query(value = "SELECT a FROM Album a WHERE a.name LIKE :name")
    List<Album> getByName(@Param(value = "name") String name);

    @Query(value = "SELECT a FROM Album a WHERE a.date = :date")
    List<Album> getByDate(@Param(value = "date") LocalDate date);
}
