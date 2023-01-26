package com.dislinkt.post.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class CommentDTO {
    
    private UUID id;

    @NotBlank(message = "You can't leave an empty comment!")
    private String text;

    private UUID personId;

    private UUID postId;
    
    private Date createdAt;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CommentDTO() {
    }

    public CommentDTO(UUID id, @NotBlank(message = "You can't leave an empty comment!") String text, UUID personId,
    UUID postId) {
        this.id = id;
        this.text = text;
        this.personId = personId;
        this.postId = postId;
    }
    
}
