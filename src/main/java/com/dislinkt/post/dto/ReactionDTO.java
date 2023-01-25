package com.dislinkt.post.dto;

import java.util.UUID;

public class ReactionDTO {

    private UUID id;

    private String type;

    private UUID personId;

    private UUID postId;

    private PersonInfo personInfo;

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
    
    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public ReactionDTO() {
    }

    public ReactionDTO(UUID id, String type, UUID personId, UUID postId) {
        this.id = id;
        this.type = type;
        this.personId = personId;
        this.postId = postId;
    }
    
}
