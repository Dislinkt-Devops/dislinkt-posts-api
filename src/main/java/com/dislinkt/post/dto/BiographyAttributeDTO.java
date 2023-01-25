package com.dislinkt.post.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class BiographyAttributeDTO {

    private UUID id;

    @NotBlank(message = "Attribute name can't be blank")
    private String attributeName;

    @NotBlank(message = "Attribute value can't be blank")
    private String attributeValue;

    @NotBlank(message = "Attribute type can't be blank")
    private String attributeType;

    private UUID personId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public BiographyAttributeDTO() {
    }

    public BiographyAttributeDTO(UUID id, @NotBlank(message = "Attribute name can't be blank") String attributeName,
            @NotBlank(message = "Attribute value can't be blank") String attributeValue,
            @NotBlank(message = "Attribute type can't be blank") String attributeType, UUID personId) {
        this.id = id;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.attributeType = attributeType;
        this.personId = personId;
    }
    
}
