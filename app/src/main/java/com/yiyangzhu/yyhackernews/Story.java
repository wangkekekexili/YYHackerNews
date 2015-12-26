package com.yiyangzhu.yyhackernews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Story object represents a Hacker News story.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

    private int id;
    private String title;
    private String url;

    public Story() {
    }

    public Story(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Story{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
