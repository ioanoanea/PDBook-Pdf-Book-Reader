package com.ioanoanea.pdbook.objects;

public class SearchItem {

    private String searchedText;
    private int Page;

    public void setSearchedText(String searchedText) {
        this.searchedText = searchedText;
    }

    public void setPage(int page) {
        Page = page;
    }

    public String getSearchedText() {
        return searchedText;
    }

    public int getPage() {
        return Page;
    }
}
