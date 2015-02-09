package com.litesearch.crawler;

import java.util.*;

import static com.litesearch.Mail.EmailValidation.isAddressValid;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class CrawledSearch {
    private List<String> crawledSearch = new ArrayList<String>();
    private List<String> targetList = new ArrayList<String>();
    private Queue<String> listOfSearch = new LinkedList<String>();
    private HashMap<String,String> content = new HashMap<String,String>() ;

    private boolean forceCreation;
    private String TargetQuery;
    private int depth;

    public boolean getForceCreation() {
        return forceCreation;
    }

    public void setForceCreation(boolean forceCreation) {
        this.forceCreation = forceCreation;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getTargetQuery() {
        return TargetQuery;
    }

    public void setTargetQuery(String targetQuery) {
            TargetQuery = targetQuery;
    }

    public void setCrawledQueries(List<String> crawledSearch) {
        this.crawledSearch = crawledSearch;
    }

    public List<String> getCrawledQueries() {
        return crawledSearch;
    }

    public HashMap<String, String> getContent() {
        return content;
    }
    public void addCrawledQueries(String query){
        this.crawledSearch.add(query);
    }

    public List<String> getCrawledSearch() {
        return crawledSearch;
    }

    public String getTopQuery(){
        return this.getListOfSearch().peek();
    }

    public synchronized String getSeedQuery()
    {
        return (this.getListOfSearch().remove());
    }

    public synchronized Queue<String> getListOfSearch() {
        return listOfSearch;
    }

    public void setListOfQueries(Queue<String> listOfQueries) {
        this.listOfSearch = listOfQueries;
    }

    public synchronized void addListOfSearch(String query){

        if (isAddressValid(query)) {
            if(!(this.getCrawledQueries().contains(query) || this.getListOfSearch().contains(query))){
                this.listOfSearch.add(query.trim());
            }
        }

    }
    public synchronized void addTarget(String query){

                this.targetList.add(query.trim());
           
    }
}
