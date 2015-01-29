package com.litesearch.crawler;

import java.util.*;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class CrawledSearch {
    private List<String> crawledSearch = new ArrayList<String>();
    private Queue<String> listOfSearch = new LinkedList<String>();
    private HashMap<String,String> content = new HashMap<String,String>() ;
    private List<String> crawledDomains = new ArrayList<String>();

    public List<String> getCrawledDomains() {
        return crawledDomains;
    }

    public void setCrawledDomains(List<String> crawledDomains) {
        this.crawledDomains = crawledDomains;
    }

    public HashMap<String, String> getContent() {
        return content;
    }

    public void setContent(HashMap<String, String> content) {
        this.content = content;
    }

    public synchronized void addContent(String key,String value){
        this.content.put(key,value);
    }

    public List<String> getCrawledSearch() {
        return crawledSearch;
    }

    public String getTopUrl(){
        return this.getListOfSearch().peek();
    }

    public synchronized String getSeedUrl()
    {
        return (this.getListOfSearch().remove());
    }

    public void addCrawledSites(String url){
        this.crawledSearch.add(url);
    }

    public void setCrawledSites(ArrayList<String> crawledSites) {
        this.crawledSearch = crawledSites;
    }

    public synchronized Queue<String> getListOfSearch() {
        return listOfSearch;
    }

    public void setListOfQueries(Queue<String> listOfQueries) {
        this.listOfSearch = listOfQueries;
    }

    public synchronized void addListOfSearch(String query){

        if(!(this.getCrawledSearch().contains(query)|| this.getListOfSearch().contains(query) || query.contains(".pdf"))){
            this.listOfSearch.add(query.trim());
        }


    }
}
