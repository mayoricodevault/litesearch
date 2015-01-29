package com.litesearch.Person.model;
public class SocialProfile {

    private String typeName;
	private String url;
	private String username;
    private String bio;

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }



    public String getTypeName() {
        return typeName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SocialProfile{");
        sb.append("url='").append(url).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
