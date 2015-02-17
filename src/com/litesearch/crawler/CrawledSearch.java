package com.litesearch.crawler;

import com.litesearch.CSConstants;
import com.litesearch.crawler.model.TargetInfo;

import java.util.*;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class CrawledSearch {
    private List<String> crawledSearch = new ArrayList<String>();
    private Queue<TargetInfo> targetList = new LinkedList<TargetInfo>();
    private Queue<String> listOfSearch = new LinkedList<String>();
    private HashMap<String,String> content = new HashMap<String,String>() ;

    
    private boolean forceCreation;
    private String TargetType = CSConstants.GENERAL_CONTEXT;
    private int depth;
    
    public String getTargetType() {
        return TargetType;
    }

    public void setTargetType(String targetType) {
        TargetType = targetType;
    }

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

    public synchronized TargetInfo getSeedTarget()
    {
        return (this.getTargetList().remove());
    }
    
    public synchronized Queue<TargetInfo> getTargetList() {
        return targetList;
    }

    public synchronized Queue<TargetInfo> getListOfTargets() {
        return targetList;
    }

    public void setListOfTargets(Queue<TargetInfo> listOfTargets) {
        this.targetList = listOfTargets;
    }
    public void setListOfQueries(Queue<String> listOfQueries) {
        this.listOfSearch = listOfQueries;
    }

    public synchronized void addListOfSearch(String query){

//        if (isAddressValid(query)) {
            if(!(this.getCrawledQueries().contains(query) || this.getListOfSearch().contains(query))){
                this.listOfSearch.add(query.trim());
//            }
        }

    }
    
    public synchronized void addTargetList(String tdomain, String tselector){
        TargetInfo targetInfo = new TargetInfo();
        Boolean found=false;
        for (TargetInfo tl : targetList) {
            if (tl.getTargetDomain().contains(tdomain)) {
                found=true;
            }
        }
        targetInfo.setTargetDomain(tdomain);
        targetInfo.setTargetSelector(tselector);
        if (!found) {
            this.targetList.add(targetInfo);
        }
           
    }
}
