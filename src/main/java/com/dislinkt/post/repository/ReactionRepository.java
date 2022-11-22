package com.dislinkt.post.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dislinkt.post.enums.ReactionType;
import com.dislinkt.post.model.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer>{

    List<Reaction> findByPostId(Integer id);

    List<Reaction> findByTypeAndPostId(ReactionType type, Integer id);

    Reaction findByPostIdAndPersonId(Integer postId, UUID personId);
    
}
