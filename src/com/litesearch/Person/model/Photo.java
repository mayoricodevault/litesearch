package com.litesearch.Person.model;

public class Photo {

	private String url;
    private boolean isPrimary;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Photo{");
        sb.append("url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
