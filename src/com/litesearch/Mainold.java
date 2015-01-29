package com.litesearch;

import com.litesearch.crawler.CrawledSearch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;

import static com.litesearch.Mail.EmailValidation.isAddressValid;

public class Mainold {
    private static int min_number_of_wait_times = 6;
    private static int max_number_of_wait_times = 15;
    public static void main(String[] args) throws Exception {
	// write your code here

        if(args.length == 0) {
            System.out.println("Please include a business email as the first parameter.");
            return;
        }

        CrawledSearch crawledSearch = new CrawledSearch();

        crawledSearch.addListOfSearch("mmayorivera@gmail.com");
        crawledSearch.addListOfSearch("mmayorivera1@gmail.com");
        crawledSearch.addListOfSearch("mmayorivera2@gmail.com");
        crawledSearch.addListOfSearch("mmayorivera3@gmail.com");

        System.out.println(crawledSearch.getListOfSearch());

        //if (true) return;

        String keyword =  args[0];
        String target_name = args[1];
        if (!isAddressValid(keyword))
            throw new Exception( "Address is not valid!" );

        System.setProperty("http.agent", "");
        int nb_depth = 2;
        long startTimeMs = System.currentTimeMillis( );
        org.jsoup.nodes.Document doc;
        int depth=0;
        int nb_results=0;
        int my_rank=50;
        String my_url = "";
        boolean found = false;
        while (depth<nb_depth && !found){
            try{
                // we wait between x and xx seconds
                Thread.sleep(randInt(min_number_of_wait_times,max_number_of_wait_times)*1000);
                System.out.println("Fetching a new page");
                doc =  Jsoup.connect(
                        "https://www.google.fr/search?q=" + keyword + "&start=" + Integer.toString(depth * 10))
                        .userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)")
                        .ignoreHttpErrors(true)
                        .timeout(0)
                        .get();

                Elements serpsFull = doc.select("span[class=st]");
                Elements serps = doc.select("h3[class=r]");
                int nextSerp=0;
                for (Element serp : serps) {
                    Element link = serp.getElementsByTag("a").first();
                    String linkref = link.attr("href");
                    if (linkref.startsWith("/url?q=")){
                        nb_results++;
                        linkref = linkref.substring(7,linkref.indexOf("&"));
                    }
                    if (linkref.contains(target_name)){
                        my_rank=nb_results;
                        my_url=linkref;
                        found=true;
                    }
                    System.out.println("Link ref: "+linkref);
                    System.out.println("Title: "+serp.text());
                    System.out.println("Desc: " + serpsFull.get(nextSerp).text());
                    nextSerp++;
                }
                if (nb_results == 0){
                    System.out.println("Warning captcha");
                }
                depth++;
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        long taskTimeMs  = System.currentTimeMillis( ) - startTimeMs;
        System.out.println(taskTimeMs);
//        info.setPosition(my_rank);
//        info.setUrl(my_url);
        if (nb_results == 0){
            System.out.println("Warning captcha");
        }else {
            System.out.println("Number of links : "+nb_results);
        }
        System.out.println("My rank : "+my_rank+" for keyword : "+keyword);
        System.out.println("My URL : "+my_url+" for keyword : "+keyword);
//        return info;

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
