package com.litesearch;

import com.litesearch.Person.SearchResponse;
import com.litesearch.Person.model.SerpInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
/**
 * Created by @mmayorivera on 1/28/15.
 */
public class CustomSearch {
    protected static Level logLevel = Level.OFF;


    public static void search(String keyword,String targetName , Boolean getContent, int nb_depth) throws IOException, InterruptedException {
        org.jsoup.nodes.Document HTMLdoc;
        int depth = 0;
        int nb_results = 0;
        boolean found = false;
        int rankFound=0;
        String targetFound;
        long startTimeMs = System.currentTimeMillis( );
        SearchResponse SR = new SearchResponse();
        List<SerpInfo> serpInfoCol = new ArrayList<SerpInfo>();
        SR.setRequestId(keyword);
        System.setProperty("http.agent", "");
        while (depth < nb_depth && !found) {
            try {
                Thread.sleep(randInt(CSConstants.min_number_of_wait_times, CSConstants.max_number_of_wait_times) * 1000);
                Utils.info("Fetching a new page" + keyword);
                HTMLdoc =  Jsoup.connect(
                        CSConstants.GOOGLE_QUERY_BASE_URL + CSConstants.PARAM_CS_QUERY + keyword + CSConstants.PARAM_CS_QUERY_PAGE + Integer.toString(depth * 10))
                        .userAgent(CSConstants.USER_AGENT)
                        .ignoreHttpErrors(true)
                        .timeout(CSConstants.PARAM_CS_TIMEOUTMS)
                        .get();
                Elements serpsFull = HTMLdoc.select(CSConstants.SEARCH_CS_XSELECTOR);
                Elements serps = HTMLdoc.select(CSConstants.SEARCH_CS_SELECTOR);
                int nextSerp=0;
                for (Element serp : serps) {
                    Element link = serp.getElementsByTag("a").first();
                    String linkref = link.attr("href");
                    if (linkref.startsWith("/url?q=")){
                        nb_results++;
                        linkref = linkref.substring(7,linkref.indexOf("&"));
                    }
                    if (linkref.contains(targetName)){
                        rankFound=nb_results;
                        targetFound=linkref;
                        // TODO: Get another instace of jsoup to parase more
                        found=true;
                    }
                    // Set Contact Info
                    SerpInfo serpInfo = new SerpInfo();
                    serpInfo.setLink(linkref);
                    serpInfo.setShorDesc(serpsFull.get(nextSerp).text());
                    serpInfo.setTitle(serp.text());
                    serpInfoCol.add(serpInfo);
                    // Set Social
                    System.out.println("Link ref: "+linkref);
                    System.out.println("Title: "+serp.text());
                    System.out.println("Desc: " + serpsFull.get(nextSerp).text());
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
        long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
        System.out.println(taskTimeMs);
        if (nb_results == 0){
            System.out.println("Warning captcha");
        }else {
            System.out.println("Number of links : "+nb_results);
        }
        SR.setSerp((List<SerpInfo>) serpInfoCol);

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
