package com.litesearch.Person;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litesearch.CustomSearchException;
import com.litesearch.Person.model.ContactInfo;
import com.litesearch.Person.model.Organization;
import com.litesearch.Person.model.Photo;
import com.litesearch.Person.model.SocialProfile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class PersonResponse {
    private static ObjectMapper mapper = new ObjectMapper();
    private ContactInfo contactInfo = new ContactInfo();
    private List<Organization> organizations = Collections.emptyList();
    private List<Photo> photos = Collections.emptyList();
    private double likelihood;
    private List<SocialProfile> socialProfiles = Collections.emptyList();
    private String requestId;
    private String message;

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setLikelihood(double likelihood) {
        this.likelihood = likelihood;
    }

    public void setSocialProfiles(List<SocialProfile> socialProfiles) {
        this.socialProfiles = socialProfiles;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the social profile of a certain type
     * @return the SocialProfile, or null if it doesn't exist.
     */
    public SocialProfile getSocialProfile(String TypeName) {
        for(SocialProfile p : socialProfiles) {
            if(TypeName.equals(p.getTypeName())) {
                return p;
            }
        }
        return null;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public double getLikelihood() {
        return likelihood;
    }

    public List<SocialProfile> getSocialProfiles() {
        return socialProfiles;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getMessage() {
        return message;
    }

    public void toJson(File fileResult) throws CustomSearchException {
        //Properties not present in the POJO are ignored instead of throwing exceptions
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //An empty string ("") is interpreted as null
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            mapper.writeValue(fileResult , this);
        } catch(JsonMappingException e) {
            throw new CustomSearchException("Failed to convert person json to a response", e);
        } catch(JsonParseException e) {
            throw new CustomSearchException("Json is not valid format", e);
        } catch(IOException e) {
            throw new CustomSearchException("Unexpected exception when parsing json", e);
        }
    }

    /**
     * Factory method to create a webhook response from json.
     * @param json
     * @return a new PersonResponse represented by the Json string
     * @throws CustomSearchException if there is a parsing/mapping error.
     */
    public static PersonResponse fromJson(String json) throws CustomSearchException {
        //Properties not present in the POJO are ignored instead of throwing exceptions
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //An empty string ("") is interpreted as null
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            return mapper.readValue(json, PersonResponse.class);
        } catch(JsonMappingException e) {
            throw new CustomSearchException("Failed to convert person json to a response", e);
        } catch(JsonParseException e) {
            throw new CustomSearchException("Json is not valid format", e);
        } catch(IOException e) {
            throw new CustomSearchException("Unexpected exception when parsing json", e);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonResponse{");
        sb.append("contactInfo=").append(contactInfo);
        sb.append('}');
        return sb.toString();
    }

}
