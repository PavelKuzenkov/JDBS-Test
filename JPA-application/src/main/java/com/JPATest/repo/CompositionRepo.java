package com.JPATest.repo;

import com.JPATest.data.Album;
import com.JPATest.data.Composition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompositionRepo extends PagingAndSortingRepository<Composition, Long> {

    List<Composition> findByAlbum(Album album);

    @Query(value = "SELECT c FROM Composition c WHERE c.name LIKE :name")
    List<Composition> getByName(@Param(value = "name") String name);

    @Query(value = "SELECT c FROM Composition c WHERE c.duration = :duration")
    List<Composition> getByDuration(@Param(value = "duration") Double duration);
}
