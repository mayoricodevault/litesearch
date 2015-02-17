package com.litesearch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litesearch.Person.SearchResponse;
import com.litesearch.Person.model.SerpInfo;
import com.litesearch.crawler.CrawledSearch;
import com.litesearch.crawler.model.TargetInfo;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
/**
 * Created by @mmayorivera on 1/28/15.
 */
public class CustomSearch extends HTMLParser{
    protected static Level logLevel = Level.ALL;
    private static ObjectMapper mapper = new ObjectMapper();

    public static void search(CrawledSearch crawledQueries,  Boolean getContent) throws IOException, InterruptedException, CustomSearchException {
        int nb_results = 0;
        long taskTimeMs =0;
        String keyword = crawledQueries.getSeedQuery(), searchEndPoint, targetSearch, targetFound = "?";  // Get the keyword searching
        SearchResponse SR = new SearchResponse();
        List<SerpInfo> serpInfoCol;
        long startTimeMs = System.currentTimeMillis( );
        Utils.info("BEGIN Fetching new Subject " + keyword  + "("+startTimeMs+")");
        if (crawledQueries.getListOfSearch().size()<0) {
            throw new CustomSearchException("Nothing to Search So Far"+crawledQueries.getListOfSearch().size());
        }
        if (crawledQueries.getListOfTargets().isEmpty()) {
            crawledQueries.setTargetType(CSConstants.GENERAL_CONTEXT);
        } 
        if (!Utils.hasCached(CSConstants.DIR_QUERY_BASE_FILE+keyword+".json", getContent)) {
            SR.setRequestId(keyword);
            System.setProperty("http.agent", "");
            if (crawledQueries.getTargetType().matches(CSConstants.DOMAIN_CONTEXT)) {
                for(TargetInfo tList : crawledQueries.getTargetList()) {
                    String targetDomain = tList.getTargetDomain();
                    int depth = 0;
                    targetSearch = "site:" + targetDomain + "+" + keyword;
                    searchEndPoint = CSConstants.GOOGLE_QUERY_BASE_URL + CSConstants.PARAM_CS_QUERY + targetSearch + CSConstants.PARAM_CS_QUERY_PAGE + Integer.toString(depth * 10);
                    while (depth < crawledQueries.getDepth()) {
                        serpInfoCol = crawlSerps(crawledQueries, tList, searchEndPoint);
                        if (serpInfoCol.size()>0) {
                            SR.setSerp(serpInfoCol);
                        }    
                        depth++;
                    }
                }
            } else {
                int depth = 0;
                targetSearch = keyword;
                searchEndPoint = CSConstants.GOOGLE_QUERY_BASE_URL + CSConstants.PARAM_CS_QUERY + targetSearch + CSConstants.PARAM_CS_QUERY_PAGE + Integer.toString(depth * 10);
                while (depth < crawledQueries.getDepth()) {
                    serpInfoCol = getSerps(crawledQueries, searchEndPoint);
                    if (serpInfoCol.size()>0) {
                        SR.setSerp(serpInfoCol);
                    }
                    depth++;
                }
            }
            taskTimeMs = System.currentTimeMillis() - startTimeMs;
            SR.setTimeMS(taskTimeMs);
            SR.setMessage(" : " + SR.getSerpInfoList().size());
            try {
                SR.toJson(new File(CSConstants.DIR_QUERY_BASE_FILE + keyword + ".json"));
            } catch (CustomSearchException e) {
                e.printStackTrace();
            }
            
        } else {
            try {
                taskTimeMs = System.currentTimeMillis() - startTimeMs;
                SR = fromJson(new File(CSConstants.DIR_QUERY_BASE_FILE + keyword + ".json"));
            } catch (CustomSearchException e) {
                e.printStackTrace();
            }
        }
        crawledQueries.addCrawledQueries(keyword);
        Utils.info(MessageFormat.format("End OF Search subject {0} ({1})", keyword, taskTimeMs));

    }

    /**
     * Factory method to create a webhook response from json.
     * @param json
     * @return a new PersonResponse represented by the Json string
     * @throws com.litesearch.CustomSearchException if there is a parsing/mapping error.
     */
    public static SearchResponse fromJson(File json) throws CustomSearchException {
        //Properties not present in the POJO are ignored instead of throwing exceptions
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //An empty string ("") is interpreted as null
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            return mapper.readValue(json, SearchResponse.class);
        } catch(JsonMappingException e) {
            throw new CustomSearchException("Failed to convert serp json to a response", e);
        } catch(JsonParseException e) {
            throw new CustomSearchException("Json is not valid format", e);
        } catch(IOException e) {
            throw new CustomSearchException("Unexpected exception when parsing json", e);
        }
    }

    /**
     * Set what level to log at (default OFF). Level.INFO will post important logs (client creations, errors).
     * Level.FINE will log  workflow.
     * @param log
     */
    public static void setLogLevel(Level log) {
        logLevel = log;
    }
    


}
