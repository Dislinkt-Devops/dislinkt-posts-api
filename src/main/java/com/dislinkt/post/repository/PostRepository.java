package com.dislinkt.post.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dislinkt.post.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>{
    
    List<Post> findByPersonId(UUID id);
    @Query(
        value = "SELECT P.* FROM POST P" +
        " JOIN PERSON PE ON PE.ID = P.PERSON_ID" +
        " WHERE (PE.PRIVACY = 1 OR PE.ID = ?1 OR EXISTS (" + 
        " SELECT * FROM FOLLOWERS F WHERE F.FOLLOWER_ID = ?1 AND F.FOLLOWED_ID = PE.ID" +
        "))" +
        "AND NOT EXISTS (" + 
        " SELECT * FROM BLOCKING B WHERE B.BLOCKER_ID IN (?1, PE.ID) AND B.BLOCKED_ID IN (?1, PE.ID)" +
        ") ORDER BY P.CREATED_AT DESC",
        nativeQuery = true
    )
    List<Post> findAllByViewer(UUID id);
}
