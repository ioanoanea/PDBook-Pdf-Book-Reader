package com.ioanoanea.pdbook.objects;

public class BookListItem {

    private int id;
    private String title;
    private String author;
    private Cover cover;
    private int numberOfPages;

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Cover getCover() {
        return cover;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
}
