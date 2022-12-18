package com.dislinkt.post.dto;

import java.util.UUID;

public class ReactionDTO {

    private Integer id;

    private String type;

    private UUID personId;

    private Integer postId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }
    
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public ReactionDTO() {
    }

    public ReactionDTO(Integer id, String type, UUID personId, Integer postId) {
        this.id = id;
        this.type = type;
        this.personId = personId;
        this.postId = postId;
    }
    
}
