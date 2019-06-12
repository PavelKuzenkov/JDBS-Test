package com.JPATest.repo;

import com.JPATest.Gender;
import com.JPATest.data.Group;
import com.JPATest.data.Performer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformerRepo extends PagingAndSortingRepository<Performer, Long> {

    List<Performer> findByGroup(Group group);

    @Query(value = "SELECT p FROM Performer p WHERE p.firstName LIKE :firstName")
    List<Performer> getByFirstName(@Param(value = "firstName") String firstName);

    @Query(value = "SELECT p FROM Performer p WHERE p.lastName LIKE :lastName")
    List<Performer> getByLastName(@Param(value = "lastName")String lastName);

    @Query(value = "SELECT p FROM Performer p WHERE p.middleName LIKE :middleName")
    List<Performer> getByMiddleName(@Param(value = "middleName")String middleName);

    @Query(value = "SELECT p FROM Performer p WHERE p.gender = :gender")
    List<Performer> getByGender(@Param(value = "gender") Gender gender);

    @Query(value = "SELECT p FROM Performer p WHERE p.age = :age")
    List<Performer> getByAge(@Param(value = "age")Integer age);

    @Query(value = "SELECT p FROM Performer p WHERE p.role LIKE :role")
    List<Performer> getByRole(@Param(value = "role")String role);

}
