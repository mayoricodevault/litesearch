package com.litesearch.threading;

import com.litesearch.CustomSearch;
import com.litesearch.crawler.CrawledSearch;

import java.io.IOException;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class Crawler implements Runnable{
    private int numberOfQueriesToCrawl;
    private CrawledSearch crawledSites;

    public CrawledSearch getCrawledSites() {
        return crawledSites;
    }

    public void setCrawledSites(CrawledSearch crawledSites,int numberOfQueriesToCrawl) {
        this.crawledSites = crawledSites;
        this.numberOfQueriesToCrawl=numberOfQueriesToCrawl;
    }

    Crawler(int number){
        this.numberOfQueriesToCrawl = number;

    }

    Crawler(CrawledSearch crawledSites, int number){
        this.crawledSites = crawledSites;
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
        //To change body of implemented methods use File | Settings | File Templates.
        try {

            while(crawledSites.getListOfSearch().size()<numberOfQueriesToCrawl) {
                CustomSearch.search(crawledSites, false , 1);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void initializeCrawling(int numberOfThreads,CrawledSearch crawledSites,int maximumLimit){
        for(int i=0;i<numberOfThreads;++i){
            new Thread(new Crawler(crawledSites,maximumLimit)).start();
        }
    }

    public static void addSeedPages(CrawledSearch crawledSites, String url){
        crawledSites.addListOfSearch(url);

    }

}
