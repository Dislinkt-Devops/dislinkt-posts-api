package com.dislinkt.post.model;

import javax.persistence.*;

import com.dislinkt.post.enums.ReactionType;

@Entity
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private ReactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
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

    public Reaction() {
    }

    public Reaction(Integer id, ReactionType type, Person person, Post post) {
        this.id = id;
        this.type = type;
        this.person = person;
        this.post = post;
    }

    public Reaction(ReactionType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
