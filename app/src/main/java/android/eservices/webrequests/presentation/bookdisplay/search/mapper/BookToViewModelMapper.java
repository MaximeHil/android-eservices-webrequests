package android.eservices.webrequests.presentation.bookdisplay.search.mapper;

import android.eservices.webrequests.data.api.model.Book;
import android.eservices.webrequests.presentation.bookdisplay.search.adapter.BookViewItem;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class BookToViewModelMapper {

    private BookViewItem map(Book book) {
        BookViewItem bookViewItem = new BookViewItem();
        bookViewItem.setBookTitle(book.getVolumeInfo().getTitle());
        bookViewItem.setBookId(book.getId());
        if (book.getVolumeInfo().getImageLinks() != null) {
            bookViewItem.setIconUrl(book.getVolumeInfo().getImageLinks().getThumbnail());
        }
        bookViewItem.setFavorite(book.isFavorite());
        if (book.getVolumeInfo().getAuthorList() == null) {
            bookViewItem.setBookAuthors("N.C.");
        } else {
            bookViewItem.setBookAuthors(TextUtils.join(", ", book.getVolumeInfo().getAuthorList()));
        }

        return bookViewItem;
    }

    public List<BookViewItem> map(List<Book> bookList) {
        List<BookViewItem> bookViewItemList = new ArrayList<>();
        if(bookList != null) {
            for (Book book : bookList) {
                bookViewItemList.add(map(book));
            }
        }
        return bookViewItemList;
    }
}
