package com.dislinkt.post.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dislinkt.post.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    
    List<Post> findByPersonId(UUID id);

}
