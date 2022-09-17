package com.dislinkt.post.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class CommentDTO {
    
    private Integer id;

    @NotBlank(message = "You can't leave an empty comment!")
    private String text;

    private UUID personId;

    private Integer postId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public CommentDTO() {
    }

    public CommentDTO(Integer id, @NotBlank(message = "You can't leave an empty comment!") String text, UUID personId,
            Integer postId) {
        this.id = id;
        this.text = text;
        this.personId = personId;
        this.postId = postId;
    }
    
}
