package com.dislinkt.post.dto;

import java.util.List;

public class PostDTO {

    private Integer id;

    private String text;

    private String imageUrl;

    private List<String> links;

    private String personId;

    private List<CommentDTO> comments;

    private List<ReactionDTO> reactions;

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

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<ReactionDTO> getReactions() {
        return reactions;
    }

    public void setReactions(List<ReactionDTO> reactions) {
        this.reactions = reactions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PostDTO() {
    }

    public PostDTO(Integer id, String text, String imageUrl, List<String> links, String personId, List<CommentDTO> comments,
            List<ReactionDTO> reactions) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.links = links;
        this.personId = personId;
        this.comments = comments;
        this.reactions = reactions;
    }

    public PostDTO(Integer id, String text, String imageUrl, List<String> links, String personId) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.links = links;
        this.personId = personId;
    }

}
