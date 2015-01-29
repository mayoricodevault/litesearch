package com.litesearch;

import com.litesearch.crawler.CrawledSearch;

import static com.litesearch.threading.Crawler.initializeCrawling;

public class Main {

    //public static CustomSearch serpSearch;

    public static void main(String[] args) throws Exception {
	// write your code here


        Utils.info("Miner... Time ");


        String target_name = "linkedin.com";

        CrawledSearch crawledQueries = new CrawledSearch();
        crawledQueries.setDepth(2);
        crawledQueries.addListOfSearch("mmayorivera@gmail.com");
        crawledQueries.addListOfSearch("hugo.loza@coderoad.com");
        crawledQueries.setTargetQuery(target_name);
        crawledQueries.setForceCreation(true);

        initializeCrawling(crawledQueries.getListOfSearch().size(), crawledQueries, 1);
            //serpSearch.search(keyword,target_name, true, 2 );





    }

}
