package com.dislinkt.post.dto;

import java.util.Date;
import javax.validation.constraints.NotBlank;

public class PersonDTO {

    private String id;

    @NotBlank(message = "First name can't be blank")
    private String firstName;

    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @NotBlank(message = "Gender can't be blank")
    private String gender;

    @NotBlank(message = "Phone number can't be blank")
    private String phoneNumber;

    private Date dateOfBirth;

    private String bio;

    @NotBlank(message = "Privacy can't be blank")
    private String privacy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public PersonDTO() {
    }

    public PersonDTO(
            String id,
            @NotBlank(message = "First name can't be blank") String firstName,
            @NotBlank(message = "Last name can't be blank") String lastName,
            @NotBlank(message = "Gender can't be blank") String gender,
            @NotBlank(message = "Phone number can't be blank") String phoneNumber,
            Date dateOfBirth, String bio,
            @NotBlank(message = "Privacy can't be blank") String privacy) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.privacy = privacy;
    }
    
}
