package com.example.secha.histoybookmark;

/**
 * Created by secha on 2019-01-15.
 */

public class BookMarkModel {
    private String bookmark_title, bookmark_url;

    public BookMarkModel(String bookmark_title, String bookmark_url) {
        this.bookmark_title = bookmark_title;
        this.bookmark_url = bookmark_url;

    }

    public String getBookmarkTitle() {
        return bookmark_title;
    }

    public String getBookmarkUrl() {
        return bookmark_url;
    }
}
