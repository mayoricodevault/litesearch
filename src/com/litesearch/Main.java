package com.litesearch;

import com.litesearch.crawler.CrawledSearch;

import static com.litesearch.threading.Crawler.initializeCrawling;

public class Main {

    //public static CustomSearch serpSearch;

    public static void main(String[] args) throws Exception {


        Utils.info("Miner... Time ");


        String target_name = "vcard";

        CrawledSearch crawledQueries = new CrawledSearch();
        crawledQueries.setDepth(1);
        crawledQueries.addListOfSearch("augusto.flores@coderoad.com");
        crawledQueries.setTargetType(CSConstants.CONTEXT_GENERAL);
 //       crawledQueries.addListOfSearch("ignacio.ballivian@coderoad.com");
        crawledQueries.addTargetList("linkedin.com");
        crawledQueries.addTargetList("twitter.com");
        crawledQueries.setTargetQuery(target_name);  // Vtarget
        crawledQueries.setForceCreation(true);

        initializeCrawling(crawledQueries.getListOfSearch().size(), crawledQueries, 1);


    }

}
