package com.ioanoanea.pdbook.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {
    private ArrayList<String> page = new ArrayList<>();
    private ArrayList<Integer> chapters = new ArrayList<>();
    private String title;
    private String author;
    private int id;
    private Cover cover = new Cover();

    public Book(ArrayList content){
        // for each pdf page
           for(int i = 0; i < content.size(); i++){
               // split page content in lines
               String[] pageContent = content.get(i).toString().split("\n");
               // set page content
               setPage(makePageNormal(i+1,0, pageContent));
               if(isNewChapter(i,content))
                   addNewChapter(i+1);
           }
    }


    /** transform content into a normal page format
     *  remove unnecessary brake lines
     *  remove page number from page content if exists**/
    private String makePageNormal(int pageNumber, int startLine, String[] content){
        int i = startLine;
        // get page number of lines
        int length = content.length;
        // if current line index is bigger than last line index return null
        if (i == length)
            return "";
        // else if is first line add break line (that's because first line can be title line)
        else if (i == length - 1 && content[i].equals(String.valueOf(pageNumber)))
            return "";
        else if (i == 0)
            return content[i] + "\n" + makePageNormal(pageNumber,i+1, content);
        // else if  line ends with an ending character (ex: '.', '!'. '?', etc.) add a break line
        else if(isEndingChar(content[i].charAt(content[i].length() - 1)))
            return content[i] + "\n" + makePageNormal(pageNumber,i+1,content);
        // else return the line unmodified
        else return content[i] + makePageNormal(pageNumber,i+1,content);
    }

    /** determine if a character can be used before an end line or not
        character like '.', '!', '?', etc. can be used before a break line **/
    private boolean isEndingChar(char i){
        if(i == '.' || i == '!' || i == '?' || i == ';' || i == ':' || i == '\n' || Character.isUpperCase(i) || Character.isTitleCase(i))
            return true;
        else return false;
    }

    /** determine if a page can be a new chapter **/
    private boolean isNewChapter(int page, ArrayList<String> pageContent) {
        /* if is first page cannot be a new chapter, return false
         * (the first page usually contains info about book, author, etc.) */
        if(page == 0)
            return false;
        /* else if there is a big difference between page number of lines and  anterior page
         *  number of lines, it can be a new chapter, return true, else return false */
        else if(getNumberOfLines(pageContent.get(page)) - getNumberOfLines(pageContent.get(page-1)) > 3)
            return true;
        else return false;
    }


    /** determine number of lines of a page **/
    private int getNumberOfLines(String pageText){
        // split page text at any '\n' character
        String[] lines = pageText.split("\n");
        // return number of splits
        return lines.length;
    }

    /** mark a page as beginning of a new chapter **/
    private void addNewChapter(int page){
        chapters.add(page);
    }


    public void setPage(String content){
        if(!content.equals(""))
            page.add(content);
    }

    public void setCover(Cover cover){
        this.cover = cover;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }


    public String getPage(int nr){
        return page.get(nr-1);
    }

    public int getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public int getNumberOfPages() {
        return page.size();
    }

    public Cover getCover() {
        return cover;
    }

    public ArrayList<Integer> getChapters(){
        return chapters;
    }

}
