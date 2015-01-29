package com.litesearch;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by @mmayorivera on 1/28/15.
 */
public class Utils {


    public static File loadFile(String fileName) throws IOException {
        return new File("src/test/resources/" + fileName);
    }

    public static boolean hasCached(String fileName, boolean forceDelete) throws IOException {
        boolean found = false;

        File f = new File(fileName);
        if(f.exists()){
            if (forceDelete) {
                f.delete();
            } else {
                found = true;
            }

        }
        return found;
    }

    public static void log(Level l, String log) {
        if(l.intValue() >= CustomSearch.logLevel.intValue()) {
            System.out.println(CSConstants.LOG_PREFIX + " " + log);
        }
    }


    public static boolean isAlphabeticalOnly(String str) {
        return str.matches("[a-zA-z]*");
    }

    /**
     * Log important events like client startup, exceptions, etc.
     * @param log string to log
     */
    public static void info(String log) {
        log(Level.INFO, log);
    }

    /**
     * Log workflow-related events, like sending a request, debug info, etc.
     * @param log string to log
     */
    public static void verbose(String log) {
        log(Level.FINE, log);
    }

}

