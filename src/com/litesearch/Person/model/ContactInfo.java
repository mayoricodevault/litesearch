package com.litesearch.Person.model;
import java.util.Collections;
import java.util.List;

public class ContactInfo {
	private String fullName;
    private List<Website> websites = Collections.emptyList();

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setWebsites(List<Website> websites) {
        this.websites = websites;
    }

    public String getFullName() {
        return fullName;
    }

    public List<Website> getWebsites() {
        return websites;
    }


    public static class Website {

        private String url;
        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Website{");
            sb.append("url='").append(url).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContactInfo{");
        sb.append("websites=").append(websites.size());
        sb.append(", fullName='").append(fullName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
