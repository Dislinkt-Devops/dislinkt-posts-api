package com.dislinkt.post.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dislinkt.post.model.BiographyAttribute;

@Repository
public interface BiographyAttributeRepository extends JpaRepository<BiographyAttribute, UUID>{

    List<BiographyAttribute> findByPersonId(UUID id);
    
}
