package com.dislinkt.post.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import com.dislinkt.post.enums.Gender;
import com.dislinkt.post.enums.ProfilePrivacy;

@Entity
public class Person {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column
    private String bio;

    @Column
    private ProfilePrivacy privacy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "person")
    private Set<BiographyAttribute> biographyAttributes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "person")
    private Set<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "person")
    private Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "person")
    private Set<Reaction> reactions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "followers",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "followed_id"))
    private Set<Person> following;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "followers",
        joinColumns = @JoinColumn(name = "followed_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<Person> followers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "blocking",
        joinColumns = @JoinColumn(name = "blocker_id"),
        inverseJoinColumns = @JoinColumn(name = "blocked_id"))
    private Set<Person> blocked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "blocking",
        joinColumns = @JoinColumn(name = "blocked_id"),
        inverseJoinColumns = @JoinColumn(name = "blocker_id"))
    private Set<Person> blockedBy;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ProfilePrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(ProfilePrivacy privacy) {
        this.privacy = privacy;
    }
    
    public Set<BiographyAttribute> getBiographyAttributes() {
        return biographyAttributes;
    }

    public void setBiographyAttributes(Set<BiographyAttribute> biographyAttributes) {
        this.biographyAttributes = biographyAttributes;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
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

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object arg0) {
        Person person = (Person) arg0;
        if (!(arg0 instanceof Person)) return false;
        int comparison = person.getId().compareTo(this.id);
        return comparison == 0;
    }

    public Person() {
    }

    public Person(String firstName, String lastName, Gender gender, String phoneNumber, Date dateOfBirth,
            String bio,ProfilePrivacy privacy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.privacy = privacy;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.biographyAttributes = new HashSet<>();
        this.comments = new HashSet<>();
        this.posts = new HashSet<>();
        this.reactions = new HashSet<>();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.blocked = new HashSet<>();
        this.blockedBy = new HashSet<>();
    }

    public Set<Person> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Person> following) {
        this.following = following;
    }

    public Set<Person> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Person> followers) {
        this.followers = followers;
    }

    public Set<Person> getBlocked() {
        return blocked;
    }

    public void setBlocked(Set<Person> blocked) {
        this.blocked = blocked;
    }

    public Set<Person> getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(Set<Person> blockedBy) {
        this.blockedBy = blockedBy;
    }

}
