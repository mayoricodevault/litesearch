package com.litesearch;

import com.litesearch.Person.model.SerpInfo;
import com.litesearch.crawler.CrawledSearch;
import com.litesearch.crawler.model.TargetInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Created by @mmayorivera on 2/17/15.
 */
public class HTMLParser {
    
    protected static Level logLevel = Level.ALL;
    private final static Pattern Filters= Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4|xls|doc|php|html|"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf|docx|xlsx|"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    
    public static List<SerpInfo> getSerps(CrawledSearch crawledQueries, String searchEndPoint) throws IOException, InterruptedException {
        Document HTMLdoc;
        List<SerpInfo> serpInfoCol = new ArrayList<SerpInfo>();
        long taskTimeMs =0;
        long startTimeMs = System.currentTimeMillis( );
        Utils.info("BEGIN << "+ searchEndPoint + ">> ("+startTimeMs+")");
        try {
            Thread.sleep(randInt(CSConstants.min_number_of_wait_times, CSConstants.max_number_of_wait_times) * 100);
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
                    linkref = linkref.substring(7, linkref.indexOf("&"));
                    if (shouldVisit(linkref)) {
                        SerpInfo serpInfo = new SerpInfo();
                        serpInfo.setLink(linkref);
                        long serpTimeMs = System.currentTimeMillis() - startTimeMs;
                        serpInfo.setShorDesc(serpsFull.get(nextSerp).text());
                        serpInfo.setTitle(serp.text());
                        serpInfo.setTimeMS(serpTimeMs);
                        serpInfoCol.add(serpInfo);
                        Utils.info("(+) "+ linkref + ">> Added");
                    } else {
                        Utils.info("(-) "+linkref + ">> Discarted");
                    }
                    // Set Social
                    nextSerp++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskTimeMs = System.currentTimeMillis() - startTimeMs;
        Utils.info("END << "+ searchEndPoint + ">> ("+taskTimeMs+")");
        return serpInfoCol;
    }

    public static List<SerpInfo> crawlSerps(CrawledSearch crawledQueries, TargetInfo targetDomain, String searchEndPoint) throws IOException, InterruptedException {
        Document HTMLdoc;
        List<SerpInfo> serpInfoCol = new ArrayList<SerpInfo>();
        long taskTimeMs =0;
        long startTimeMs = System.currentTimeMillis( );
        Utils.info("BEGIN << "+ searchEndPoint + ">> ("+startTimeMs+")");
        try {
            Thread.sleep(randInt(CSConstants.min_number_of_wait_times, CSConstants.max_number_of_wait_times) * 100);
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
                    linkref = linkref.substring(7, linkref.indexOf("&"));
                    if (shouldVisit(linkref)) {
                        SerpInfo serpInfo = new SerpInfo();
                        serpInfo.setLink(linkref);
                        long serpTimeMs = System.currentTimeMillis() - startTimeMs;
                        serpInfo.setShorDesc(serpsFull.get(nextSerp).text());
                        serpInfo.setTitle(serp.text());
                        serpInfo.setTimeMS(serpTimeMs);
                       
                        if (targetDomain.getTargetSelector() != null) {
                            String selectorVisited = parseDocumentBySelector(linkref, targetDomain.getTargetSelector());
                            if (selectorVisited.length() > 0) {
                                serpInfo.setVcard(selectorVisited);
                                Utils.info("(Crawl) "+ linkref + ">> Got It");
                            }
                        }
                        serpInfoCol.add(serpInfo);
                        Utils.info("(+) "+ linkref + ">> Added");
                    } else {
                        Utils.info("(-) "+linkref + ">> Discarted");
                    }
                    // Set Social
                    nextSerp++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskTimeMs = System.currentTimeMillis() - startTimeMs;
        Utils.info("END << "+ searchEndPoint + ">> ("+taskTimeMs+")");
        return serpInfoCol;
    }
    
    public static String parseDocumentBySelector(String searchEndPoint, String selector) throws IOException, InterruptedException  {
        Document crawledSite;
        Elements links = null;
        String OuterHtml= null;
        long taskTimeMs =0;
        long startTimeMs = System.currentTimeMillis( );
        Utils.info("............Crawling Begin << "+ searchEndPoint + "->"+ selector + ">> ("+startTimeMs+")");
        try {
            Thread.sleep(randInt(CSConstants.min_number_of_wait_times, CSConstants.max_number_of_wait_times) * 10);
            crawledSite = Jsoup.connect(searchEndPoint)
                    .userAgent(CSConstants.USER_AGENT)
                    .ignoreHttpErrors(true)
                    .timeout(CSConstants.PARAM_CS_TIMEOUTMS)
                    .get();
            links = crawledSite.select(selector);
        } catch (IOException e) {
            OuterHtml = null;
        } catch (InterruptedException e) {
            OuterHtml = null;
        } finally {
            if (links != null) {
                OuterHtml = links.outerHtml();
            }
        }
        taskTimeMs = System.currentTimeMillis() - startTimeMs;
        Utils.info("............Crawling End << "+ searchEndPoint + "->"+ selector + ">> ("+taskTimeMs+")");
        return OuterHtml;
    }

    public static boolean shouldVisit(String url){
        String cleanUrl = url.toLowerCase().trim();
        return !((Filters.matcher(cleanUrl).matches()));

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
