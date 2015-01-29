package com.litesearch;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class CSConstants {
    public static final String GOOGLE_QUERY_BASE_URL = "https://www.google.fr/search";
    public static final String DIR_QUERY_BASE_FILE = "./results/";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)";
    public static int min_number_of_wait_times = 6;
    public static int max_number_of_wait_times = 15;
    public static final String PARAM_CS_QUERY = "?q=";
    public static final String PARAM_CS_QUERY_PAGE = "&start";
    public static final int PARAM_CS_DEPTH = 1;
    public static final String LOG_PREFIX = "[cs4j]";
    public static final int PARAM_CS_TIMEOUTMS = 0;
    public static final String SEARCH_CS_SELECTOR = "h3[class=r]";
    public static final String SEARCH_CS_XSELECTOR = "span[class=st]";



}
