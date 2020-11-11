package android.eservices.webrequests.presentation.bookdisplay.favorite.mapper;


import android.eservices.webrequests.data.api.model.BookInfo;
import android.eservices.webrequests.data.api.model.ImageLinks;
import android.eservices.webrequests.data.entity.BookEntity;
import android.eservices.webrequests.presentation.bookdisplay.favorite.adapter.BookDetailViewItem;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BookEntityToDetailViewModelMapper {

    //TODO
    public BookDetailViewItem map(BookEntity bookEntity){
        Log.e("Test mapper", "Dans le mapper");
        BookDetailViewItem bookDetailViewItem = new BookDetailViewItem();
        bookDetailViewItem.setBookTitle(bookEntity.getTitle());
        bookDetailViewItem.setBookAuthors(bookEntity.getAuthors());
        if(bookEntity.getDescription() != null) {
            bookDetailViewItem.setBookDescription(bookEntity.getDescription());
        }
        bookDetailViewItem.setBookId(bookEntity.getId());
        String langue = "Le livre est en " + languageMapper(bookEntity.getLanguage());
        bookDetailViewItem.setBookLanguage(langue);
        String date = "Publié le " + bookEntity.getPublishedDate();
        bookDetailViewItem.setBookPublishedDate(date);
        bookDetailViewItem.setIconUrl(bookEntity.getThumbUrl());
        return bookDetailViewItem;
    }

    public List<BookDetailViewItem> map(List<BookEntity> bookList) {
        ArrayList<BookDetailViewItem> bookDetailViewItems = new ArrayList<>();
        for(BookEntity be : bookList){
            bookDetailViewItems.add(map(be));
        }
        return bookDetailViewItems;
    }

    private String languageMapper(String input) {
        switch (input) {
            case "en":
                return "anglais";
            case "fr":
                return "français";
            default:
                return "Inconnu (" + input + ")";
        }
    }
}
