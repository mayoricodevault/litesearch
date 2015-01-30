package com.litesearch;

import com.litesearch.crawler.CrawledSearch;

import static com.litesearch.threading.Crawler.initializeCrawling;

public class Main {

    //public static CustomSearch serpSearch;

    public static void main(String[] args) throws Exception {


        Utils.info("Miner... Time ");


        String target_name = "linkedin.com";

        CrawledSearch crawledQueries = new CrawledSearch();
        crawledQueries.setDepth(2);
        crawledQueries.addListOfSearch("augusto.flores@coderoad.com");
        crawledQueries.addListOfSearch("ignacio.ballivian.loza@coderoad.com");
        crawledQueries.setTargetQuery(target_name);
        crawledQueries.setForceCreation(true);

        initializeCrawling(crawledQueries.getListOfSearch().size(), crawledQueries, 1);


    }

}
