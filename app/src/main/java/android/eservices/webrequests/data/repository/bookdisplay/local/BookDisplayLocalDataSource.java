package android.eservices.webrequests.data.repository.bookdisplay.local;

import android.eservices.webrequests.data.db.BookDatabase;
import android.eservices.webrequests.data.entity.BookEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class BookDisplayLocalDataSource {

    private BookDatabase bookDatabase;

    public BookDisplayLocalDataSource(BookDatabase bookDatabase){
        this.bookDatabase = bookDatabase;
    }


    public Flowable<List<BookEntity>> getFavoriteBooks() {

        return bookDatabase.bookDao().getFavorites();
    }

    public Completable addBookToFavorites(BookEntity id) {
        return bookDatabase.bookDao().addBook(id);
    }

    public Completable removeBookFromFavorites(String id) {
        return bookDatabase.bookDao().deleteBook(id);
    }

    public Single<List<String>> getFavoriteIdList(){
        return bookDatabase.bookDao().getFavoriteIdList();
    }

    public BookDatabase getBookDatabase() {
        return bookDatabase;
    }
}
