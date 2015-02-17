package com.litesearch.threading;

import com.litesearch.CustomSearch;
import com.litesearch.CustomSearchException;
import com.litesearch.crawler.CrawledSearch;

import java.io.IOException;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class Crawler implements Runnable{
    private int numberOfQueriesToCrawl;
    private CrawledSearch crawledQueries;

    public void setCrawledQueries(CrawledSearch crawledQueries,int numberOfQueriesToCrawl) {
        this.crawledQueries = crawledQueries;
        this.numberOfQueriesToCrawl=numberOfQueriesToCrawl;
    }

    Crawler(int number){
        this.numberOfQueriesToCrawl = number;

    }

    Crawler(CrawledSearch crawledQueries, int number){
        this.crawledQueries = crawledQueries;
        this.numberOfQueriesToCrawl=number;
    }

    public void setNumberOfQueriesToCrawl(int numberOfLinksToCrawl) {
        this.numberOfQueriesToCrawl = numberOfLinksToCrawl;
    }

    public int getNumberOfQueriesToCrawl() {
        return numberOfQueriesToCrawl;
    }

    @Override

    public void run() {

        try {

            while(crawledQueries.getCrawledQueries().size()<numberOfQueriesToCrawl) {
                CustomSearch.search(crawledQueries,  crawledQueries.getForceCreation());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CustomSearchException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void initializeCrawling(int numberOfThreads,CrawledSearch crawledQueries,int maximumLimit){
        for(int i=0;i<numberOfThreads;++i){
            new Thread(new Crawler(crawledQueries,maximumLimit)).start();
        }
    }

    public static void addSeedPages(CrawledSearch crawledQueries, String url){
        crawledQueries.addListOfSearch(url);

    }

}
