package com.litesearch;

import com.litesearch.crawler.CrawledSearch;

import static com.litesearch.threading.Crawler.initializeCrawling;

public class Main {

    //public static CustomSearch serpSearch;

    public static void main(String[] args) throws Exception {


        Utils.info("Miner... Time!!!!!");
        CrawledSearch crawledQueries = new CrawledSearch();
        crawledQueries.setDepth(1);
        crawledQueries.setTargetType(CSConstants.DOMAIN_CONTEXT);
       // crawledQueries.addListOfSearch("augusto.flores@coderoad.com");
        crawledQueries.addListOfSearch("Miguel Mayori");
        
 //       crawledQueries.addListOfSearch("ignacio.ballivian@coderoad.com");
        crawledQueries.addTargetList("linkedin.com", "div[class=profile-card vcard]");
        crawledQueries.addTargetList("twitter.com", "div[class=ProfileHeaderCard]");
        crawledQueries.setForceCreation(true);

        initializeCrawling(crawledQueries.getListOfSearch().size(), crawledQueries, 1);


    }

}
