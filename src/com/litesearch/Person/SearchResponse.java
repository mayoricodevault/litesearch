package com.litesearch.Person;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litesearch.CustomSearchException;
import com.litesearch.Person.model.SerpInfo;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class SearchResponse {
    private static ObjectMapper mapper = new ObjectMapper();
    private List<SerpInfo> serpInfoList = Collections.emptyList();
    private String requestId;
    private String message;
    private String targetLink;
    private long timeMS;


    public long getTimeMS() {
        return timeMS;
    }

    public void setTimeMS(long timeMS) {
        this.timeMS = timeMS;
    }

    public void setTargetLink(String targetLink) {
        this.targetLink = targetLink;
    }


    public void setSerp(List<SerpInfo> serpList) {
        if (this.serpInfoList.size()>0) {
            for(SerpInfo serp : serpList) {
                this.serpInfoList.add(serp);
            }
        } else {
            this.serpInfoList = serpList;
        } 
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SerpInfo> getSerpInfoList() {
        return serpInfoList;
    }


    public String getRequestId() {
        return requestId;
    }

    public String getMessage() {
        return message;
    }

    public String getTargetLink() {
        return targetLink;
    }

    public void toJson(File fileResult) throws CustomSearchException {
        //Properties not present in the POJO are ignored instead of throwing exceptions
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //An empty string ("") is interpreted as null
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            mapper.writeValue(fileResult , this);
        } catch(JsonMappingException e) {
            throw new CustomSearchException("Failed to convert serp json to a response", e);
        } catch(JsonParseException e) {
            throw new CustomSearchException("Json is not valid format", e);
        } catch(IOException e) {
            throw new CustomSearchException("Unexpected exception when parsing json", e);
        }
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchResponse{");
        sb.append("serpInfo=").append(requestId);
        sb.append('}');
        return sb.toString();
    }

}
