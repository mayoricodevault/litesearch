package com.litesearch.Person.model;
public class SerpInfo {
	private String link;
    private String Title;
    private String ShorDesc;

    public String getTitle() {
        return Title;
    }

    public String getShorDesc() {
        return ShorDesc;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setShorDesc(String shorDesc) {
        ShorDesc = shorDesc;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SerpInfo{");
        sb.append("link=").append(link);
        sb.append(", title='").append(Title).append('\'');
        sb.append(", desc='").append(ShorDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
