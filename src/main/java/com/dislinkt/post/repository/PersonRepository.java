package com.dislinkt.post.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dislinkt.post.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    @Query(
        value = "SELECT * FROM PERSON P WHERE" +
                " P.FIRST_NAME ILIKE %?1% OR P.LAST_NAME ILIKE %?1% OR P.ID IN (?2)",
        nativeQuery = true
    )
    List<Person> findProfilesByAllNameTypes(String keyword, List<UUID> userIds);
}
