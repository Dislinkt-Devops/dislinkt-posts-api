package com.dislinkt.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dislinkt.post.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

    List<Comment> findByPostId(Integer id);
    
}
