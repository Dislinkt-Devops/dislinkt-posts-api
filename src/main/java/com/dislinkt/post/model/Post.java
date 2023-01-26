package com.dislinkt.post.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Post {

    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column
    private String text;

    @Column(name = "image_url")
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name="links")
    private List<String> links;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "post")
    private Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "post")
    private Set<Reaction> reactions;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Post() {
    }

    public Post(UUID id, String text, String imageUrl, List<String> links) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.links = links;
        this.comments = new HashSet<>();
        this.reactions = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
