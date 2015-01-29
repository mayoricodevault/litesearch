package com.litesearch;
@SuppressWarnings("serial")
/**
 * Created by @mmayorivera on 1/28/15.
 */
public class CustomSearchException extends Exception {
    public CustomSearchException(String message) {
        super(message);
    }

    public CustomSearchException(String message, Integer error, Throwable cause) {
        super(message, cause);
        errorCode = error;
    }

    public CustomSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    private Integer errorCode;

    /**
     * Returns the HTTP error code that caused the exception,
     * or null if it was not an HTTP-based exception.
     * @return the HTTP error code, or null if it was not an HTTP based exception
     */
    public Integer getErrorCode() {
        return errorCode;
    }


}
