package com.dislinkt.post.model;

import java.util.UUID;

import javax.persistence.*;

@Entity
public class Comment {
    
    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment() {
    }

    public Comment(UUID id, String text, Person person, Post post) {
        this.id = id;
        this.text = text;
        this.person = person;
        this.post = post;
    }

    public Comment(String text) {
        this.text = text;
    }

}
