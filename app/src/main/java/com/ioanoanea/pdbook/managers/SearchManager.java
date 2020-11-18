package com.ioanoanea.pdbook.managers;

import com.ioanoanea.pdbook.objects.Book;
import com.ioanoanea.pdbook.objects.SearchItem;

import java.util.ArrayList;

public class SearchManager {


    /** Search a sequence in a book
     *  search each page and if sequence found add entire sequence to sequence list */
    public ArrayList<SearchItem> searchInBook(String searchedValue, Book book){
        // create new search item list
        ArrayList<SearchItem> searchItems = new ArrayList<>();
        // determine book length
        int length = book.getNumberOfPages();

        // search in any page
        for (int i = 1; i <= length; i++){
            if(book.getPage(i).contains(searchedValue))
                searchItems.add(searchInPage(searchedValue,i,book));
        }

        return searchItems;
    }


    /** Search a sequence in a page
     *  for each page where sequence found return entire sequence in search item type */
    private SearchItem searchInPage(String searchedValue, int page, Book book){

        // crete and a search item with searched values
        SearchItem searchItem = new SearchItem();
        searchItem.setPage(page);
        searchItem.setSearchedText(getSequence(searchedValue,book.getPage(page)));

        return searchItem;
    }


    /** determine where sequence is in tne page */
    private String getSequence(String searchedString, String content){
        // determine where sequence start
        int start = content.indexOf(searchedString);

        // determine where sequence ends
        int end;
        if (start + 300 >= content.length())
            end = content.length() - 1;
        else end = start + 300;

        // determine sequence
        String sequence = "";
        for (int i = start; i <= end; i++)
            sequence += content.charAt(i);

        // return complete sequence
        return getStartSequence(start, content) + sequence + getEndSequence(end, content) + " ...";

    }


    /** determine start of a sequence
     *  return all characters before sequence until found ' ' or '\n' */
    private String getStartSequence(int start, String content){
        // if it is start of page or start of sequence, sequence is complete, return null
        if (start < 1 || content.charAt(start) == ' ' || content.charAt(start) == '\n')
            return "";
        // else return rest of sequence + current character
        else return getStartSequence(start - 1, content) + content.charAt(start - 1);
    }


    /** determine end of a sequence
     *  return all characters after sequence until found ' ' or '\n' */
    private String getEndSequence(int end, String content){
        // if it is end of page or end of sequence, sequence is complete, return null
        if (end >= content.length() || content.charAt(end) == ' ' || content.charAt(end) == '\n')
            return "";
        // else return current character + rest of sequence
        else return content.charAt(end) + getEndSequence(end + 1, content);

    }

}
