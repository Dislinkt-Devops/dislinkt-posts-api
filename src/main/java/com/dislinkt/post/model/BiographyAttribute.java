package com.dislinkt.post.model;

import java.util.UUID;

import javax.persistence.*;

import com.dislinkt.post.enums.BiographyAttributeType;

@Entity
public class BiographyAttribute {

    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

    @Column(name = "attribute_type")
    private BiographyAttributeType attributeType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
    
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public BiographyAttribute() {
    }

    public BiographyAttribute(BiographyAttributeType attributeType, String attributeName, String attributeValue) {
        this.attributeType = attributeType;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BiographyAttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(BiographyAttributeType attributeType) {
        this.attributeType = attributeType;
    }
    
}
