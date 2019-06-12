package com.JPATest.repo;

import com.JPATest.data.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends PagingAndSortingRepository<Group, Long> {

    @Query(value = "SELECT g FROM Group g WHERE g.name LIKE :name")
    List<Group> getByName(@Param(value = "name") String name);

    @Query(value = "SELECT g FROM Group g WHERE g.country LIKE :country")
    List<Group> getByCountry(@Param(value = "country")String country);

    @Query(value = "SELECT g FROM Group g WHERE g.genre LIKE :genre")
    List<Group> getByGenre(@Param(value = "genre")String genre);
}
