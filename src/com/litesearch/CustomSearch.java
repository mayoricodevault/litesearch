package com.litesearch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litesearch.Person.SearchResponse;
import com.litesearch.Person.model.SerpInfo;
import com.litesearch.crawler.CrawledSearch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
/**
 * Created by @mmayorivera on 1/28/15.
 */
public class CustomSearch {
    protected static Level logLevel = Level.ALL;
    private static ObjectMapper mapper = new ObjectMapper();

    public static void search(CrawledSearch crawledQueries,String targetName , Boolean getContent, int nb_depth) throws IOException, InterruptedException {
        org.jsoup.nodes.Document HTMLdoc;
        int depth = 0;
        int nb_results = 0;
        boolean found = false;
        long taskTimeMs =0;
        String keyword = crawledQueries.getSeedQuery();
        String searchEndPoint;
        String targetSite;
        SearchResponse SR = new SearchResponse();
        List<SerpInfo> serpInfoCol = new ArrayList<SerpInfo>();
        String targetFound = "?";
        long startTimeMs = System.currentTimeMillis( );
        if (!Utils.hasCached(CSConstants.DIR_QUERY_BASE_FILE+keyword+".json", getContent)) {
            SR.setRequestId(keyword);
            System.setProperty("http.agent", "");
            while (depth < nb_depth && !found) {
                try {
                    Thread.sleep(randInt(CSConstants.min_number_of_wait_times, CSConstants.max_number_of_wait_times) * 1000);
                    Utils.info("Fetching a new page " + keyword);
                    targetSite = targetName!=""?"site:"+targetName+"+":""+keyword;
                    searchEndPoint = CSConstants.GOOGLE_QUERY_BASE_URL + CSConstants.PARAM_CS_QUERY + targetSite + keyword + CSConstants.PARAM_CS_QUERY_PAGE + Integer.toString(depth * 10);
                    Utils.info(searchEndPoint);
                    HTMLdoc = Jsoup.connect(searchEndPoint)
                            .userAgent(CSConstants.USER_AGENT)
                            .ignoreHttpErrors(true)
                            .timeout(CSConstants.PARAM_CS_TIMEOUTMS)
                            .get();
                    Elements serpsFull = HTMLdoc.select(CSConstants.SEARCH_CS_XSELECTOR);
                    Elements serps = HTMLdoc.select(CSConstants.SEARCH_CS_SELECTOR);
                    int nextSerp = 0;
                    for (Element serp : serps) {
                        Element link = serp.getElementsByTag("a").first();
                        String linkref = link.attr("href");
                        if (linkref.startsWith("/url?q=")) {
                            nb_results++;
                            linkref = linkref.substring(7, linkref.indexOf("&"));
                        }
                        if (linkref.contains(targetName)) {
                            targetFound = linkref;
                            found = true;
                        }
                        long serpTimeMs = System.currentTimeMillis() - startTimeMs;
                        Utils.info(linkref);

                        // Set Contact Info
                        SerpInfo serpInfo = new SerpInfo();
                        serpInfo.setLink(linkref);
                        serpInfo.setShorDesc(serpsFull.get(nextSerp).text());
                        serpInfo.setTitle(serp.text());
                        serpInfo.setTimeMS(serpTimeMs);
                        serpInfoCol.add(serpInfo);
                        // Set Social
                        nextSerp++;
                    }
                    if (nb_results == 0) {
                        Utils.verbose("Warning captcha");
                    }
                    depth++;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            taskTimeMs = System.currentTimeMillis() - startTimeMs;
            if (found) {
                SR.setTargetLink(targetFound);
            }
            SR.setTimeMS(taskTimeMs);
            SR.setMessage("Number of links : " + nb_results);
            SR.setSerp((List<SerpInfo>) serpInfoCol);
            try {
                Utils.info("Fetching cache for " + keyword);
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
        Utils.info(MessageFormat.format("End OF Search {0}", taskTimeMs));

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

    public static int randInt(int min, int max) {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }



}
