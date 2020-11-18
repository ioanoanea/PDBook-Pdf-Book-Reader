package com.ioanoanea.pdbook.objects;

import java.util.ArrayList;

public class Bookmark {

    private ArrayList<Integer> bookmark = new ArrayList<>();

    /** add new bookmark to existing bookmark list
     *  and always keep the bookmark list sorted **/
    public void addBookmark(int page){
        // determine bookmark list length
        int length = bookmark.size();
        // put new bookmark at the end of the list
        bookmark.add(page);

        boolean insert = false;
        int i = length - 1;
        /* while there are more elements in list and new bookmark is not inserted
        *  determine correct position for new bookmark */
        while (i >=0 && !insert){
            // move all list items right with 1 position
            bookmark.set(i+1,bookmark.get(i));
            /* if new bookmark page number is bigger than current list position
            *  insert new bookmark on next position and list is sorted */
            if(page >= bookmark.get(i)){
                bookmark.set(i+1,page);
                insert = true;
            } // if all list elements are bigger than new bookmark insert new bookmark on first position
            else if(i == 0 && page < bookmark.get(i)){
                bookmark.set(i,page);
            }
            i--;
        }

    }

    /** remove a bookmark from current bookmark list **/
    public void removeBookmark(int page){
        bookmark.remove(searchItem(0,bookmark.size()-1,bookmark,page));
    }

    /** get current list with bookmarks **/
    public ArrayList<Integer> getBookMark(){
        return bookmark;
    }

    /** binary search function -> verify if a value exists
     *  in a specified sorted list **/
    public int searchItem(int start, int end, ArrayList<Integer> list, int searchedValue) {
        // determine the middle of list
        int middle = (start+end)/2;
        /* if searching interval is null, searched value not found
         *  return false */
        if(start > end)
            return -1;
            /* if searched value equals to middle, searched values found
             *  return true */
        else if(searchedValue == list.get(middle))
            return middle;
            /* if searched value is smaller than middle
             *  continue searching only on list items smaller than middle */
        else if(searchedValue < list.get(middle))
            return searchItem(start,middle-1,list,searchedValue);
            /* else searched value is bigger than middle
             *  continue searching only on list items bigger than middle */
        else return searchItem(middle+1,end,list,searchedValue);
    }

}
