package com.ioanoanea.pdbook.managers;

import android.content.Context;

import com.ioanoanea.pdbook.objects.Book;
import com.ioanoanea.pdbook.objects.BookListItem;
import com.ioanoanea.pdbook.tiny_db.TinyDB;

import java.util.ArrayList;

public class Library {

    public Context context;
    private TinyDB tinyDB;

    public Library(Context context){
        //initializing data
        this.context = context;
        tinyDB = new TinyDB(context);
    }


    /** Update entire book list from shared preferences */
    private void updateBooks(ArrayList<Object> booksList){
        //update the entire book library
        tinyDB.putListObject("books",booksList);
    }

    /** Add new book to current book list */
    public void addNewBook(Book book){
        ArrayList<Object> myBooks;
        //get book library
        myBooks = getBooks();
        //add book
        myBooks.add(book);
        //update book library
        updateBooks(myBooks);
    }

    /** update book with specified id
     *  replace book with new book */
    public void updateBook(int id, Book book){
        // get all books
        ArrayList<Object> books = getBooks();
        // determine book index by id
        int index = getBookIndex(books.size(), id, books);
        // if book exists update book
        if(index != -1){
            books.set(index,book);
            updateBooks(books);
        }
    }

    /** remove a book from book list
     *  remove from list
     *  remove from shared preferences
     *  remove from progress manager */
    public void removeBook(int id){
        // get all books
        ArrayList<Object> books = getBooks();
        // determine book index by id
        int index = getBookIndex(books.size(),id,books);
        // if book exists remove book
        if(index != -1){
            books.remove(index);
            updateBooks(books);

            // remove book from progress list
            ProgressManager progressManager = new ProgressManager(context);
            progressManager.removeBookFromListOrder(id);
        }
    }


    /** determine position in book list of a book with specified id */
    private int getBookIndex(int length, int id, ArrayList<Object> books){
        // if list is null book does not exists - return -1
        if(length < 1)
            return -1;
        else {
            // get book by length - 1 index
            Book book = (Book) books.get(length-1);
            // if book has specified id return specified index
            if(book.getId() == id)
                return length - 1;
            // search book from rest of books
            else return getBookIndex(length - 1, id, books);
        }
    }

    /** get from book list book with specified  id */
    public Book getBook(int id){
        // get all books
        ArrayList<Object> books = getBooks();
        // get specified book
        return (Book) books.get(getBookIndex(books.size(),id,books));
    }


    /** get all books from book list*/
    private ArrayList<Object> getBooks(){
        //get all books from books library
        return tinyDB.getListObject("books",Book.class);
    }


    /** this functions a list of few data about books
     *  return ony data required to display
     *  this reduce memory required to display book list */
    public ArrayList<BookListItem> getBookListItems(){
        // get all books from list
        ArrayList<Object> books = getBooks();

        // create new book item list
        ArrayList<BookListItem> items = new ArrayList<>();

        // get required data from books (io, title author, cover)
        int length = books.size();
        for(int i = 0; i < length; i++){
            Book book = (Book) books.get(i);

            // get data from book and put data to book list item
            BookListItem listItem = new BookListItem();
            listItem.setId(book.getId());
            listItem.setTitle(book.getTitle());
            listItem.setAuthor(book.getAuthor());
            listItem.setCover(book.getCover());
            listItem.setNumberOfPages(book.getNumberOfPages());

            items.add(listItem);
        }

        return items;
    }


    /** get required data from book with specified id
     *  return only required data to reduce memory required to display it */
    public BookListItem getBookListItem(int id){
        // get book with specified id
        Book book = getBook(id);

        // create new book list item
        BookListItem listItem = new BookListItem();

        // get data from book and put data to book list item
        listItem.setId(book.getId());
        listItem.setTitle(book.getTitle());
        listItem.setAuthor(book.getAuthor());
        listItem.setCover(book.getCover());
        listItem.setNumberOfPages(book.getNumberOfPages());

        return listItem;
    }


}
