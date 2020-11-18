package com.ioanoanea.pdbook.managers;

import android.content.Context;

import com.ioanoanea.pdbook.objects.Bookmark;
import com.ioanoanea.pdbook.tiny_db.TinyDB;

import java.util.ArrayList;

public class ProgressManager {
    private Context context;
    private TinyDB tinyDB;


    public ProgressManager(Context context){
        this.context = context;
        tinyDB = new TinyDB(context);
    }


    /** add new book item in book progress list
     *  this is used when user ads a new book **/
    public void  addProgress(int progress){
        // add new book with new progress
        ArrayList<Integer> list = getTotalProgress();
        list.add(progress);
        tinyDB.putListInt("progress",list);
    }


    /** set new progress for specified book **/
    public void setProgress(int id, int progress){
        // set a book progress
        ArrayList<Integer> list = getTotalProgress();
        list.set(id,progress);
        tinyDB.putListInt("progress",list);;
    }


    /** get entire progress list (from all books)
     *  thi is helpful when we need to update the progress list**/
    private ArrayList<Integer> getTotalProgress(){
        return tinyDB.getListInt("progress");
    }

    /** get current progress for a specified book**/
    public int getProgress(int id){
        return tinyDB.getListInt("progress").get(id);
    }

    /** update progress list order **/
    public void updateListOrder(int id){
        ArrayList<Integer> listOrder = tinyDB.getListInt("list_order");
        // remove id from list if exists
        removeValue(id,listOrder.size(),listOrder);
        // add id to list
        listOrder.add(id);
        tinyDB.putListInt("list_order",listOrder);
    }


    /** get book progress list order
     *  get book in order of their last opening **/
    public ArrayList<Integer> getListOrder(){
        return tinyDB.getListInt("list_order");
    }


    /** create a new bookmark item in list of books
     *  thi is used when user ads a new book **/
    public void createBookMak(Bookmark bookmark){
        // get current bookmark list
        ArrayList<Object> bookmarks = getAllBookmarks();
        // add new bookmark
        bookmarks.add(bookmark);
        // update bookmark list
        tinyDB.putListObject("bookmark",bookmarks);
    }


    /** add a new bookmark **/
    public void addBookmark(int bookId, int page){
        // get current bookmark list
        ArrayList<Object> bookmarks = getAllBookmarks();
        // get bookmark list for specified book
        Bookmark bookmark = (Bookmark) bookmarks.get(bookId);
        // add new bookmark
        bookmark.addBookmark(page);
        // update bookmark list
        bookmarks.set(bookId,bookmark);
        tinyDB.putListObject("bookmark",bookmarks);
    }


    /** remove an existing bookmark **/
    public void removeBookMark(int bookId, int page){
        // get all bookmark list
        ArrayList<Object> bookmarks = getAllBookmarks();
        // get bookmark list for specified book
        Bookmark bookmark = (Bookmark) bookmarks.get(bookId);
        // remove specified bookmark
        bookmark.removeBookmark(page);
        // update bookmark list
        bookmarks.set(bookId,bookmark);
        tinyDB.putListObject("bookmark",bookmarks);
    }

    /** get bookmarks for a specified book **/
    public ArrayList<Integer> getBookmark(int bookId){
        Bookmark bookmark = (Bookmark) tinyDB.getListObject("bookmark",Bookmark.class).get(bookId);
        return bookmark.getBookMark();
    }

    /** get the entire bookmark list (from all books)
     *  this is helpful when we need to update bookmark list **/
    public ArrayList<Object> getAllBookmarks(){
        return tinyDB.getListObject("bookmark",Bookmark.class);
    }

    public int getNumberOfBooks(){
        return getTotalProgress().size();
    }

    /** remove a book if book is in list order list (if book was opened) */
    public void removeBookFromListOrder(int id){
        // remove book from list order
        ArrayList<Integer> listOrder = getListOrder();
        removeValue(id,listOrder.size(),listOrder);
        tinyDB.putListInt("list_order", listOrder);
    }

    /** search ar value in a list (integer) **/
    private int searchIntegerValue(int searchedValue, int length, ArrayList<Integer> list){
        // if list is null, value not found, return 0
        if(length == 0)
            return -1;
        // if current item equals to searched value return index
        else if (list.get(length - 1) == searchedValue)
            return length - 1;
        // else continue search for rest of the list
        else return searchIntegerValue(searchedValue,length - 1, list);
    }

    /** remove a value from a list (integer) **/
    private void removeValue(int value, int length, ArrayList<Integer> list){
        // if current value equals to value remove it from list
        if(length > 0 && list.get(length - 1) == value)
            list.remove(length - 1);
        // if length > 1 remove value for rest of the list
        if(length > 1)
           removeValue(value, length - 1, list);
    }

}
