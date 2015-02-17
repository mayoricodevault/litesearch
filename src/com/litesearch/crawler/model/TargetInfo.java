package com.litesearch.crawler.model;
public class TargetInfo {
	private String targetDomain;

    public String getTargetSelector() {
        return targetSelector;
    }

    public void setTargetSelector(String targetSelector) {
        this.targetSelector = targetSelector;
    }

    public String getTargetDomain() {
        return targetDomain;
    }

    public void setTargetDomain(String targetDomain) {
        this.targetDomain = targetDomain;
    }

    private String targetSelector;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TargetInfo{");
        sb.append("target=").append(targetDomain);
        sb.append(", domain='").append(targetDomain).append('\'');
        sb.append(", target='").append(targetSelector).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
