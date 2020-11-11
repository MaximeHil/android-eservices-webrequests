package android.eservices.webrequests.data.repository.bookdisplay;

import android.eservices.webrequests.data.api.model.Book;
import android.eservices.webrequests.data.api.model.BookSearchResponse;
import android.eservices.webrequests.data.entity.BookEntity;
import android.eservices.webrequests.data.repository.bookdisplay.local.BookDisplayLocalDataSource;
import android.eservices.webrequests.data.repository.bookdisplay.remote.BookDisplayRemoteDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class BookDisplayDataRepository implements BookDisplayRepository {

    private BookDisplayRemoteDataSource bookDisplayRemoteDataSource;
    private BookDisplayLocalDataSource bookDisplayLocalDataSource;
    private BookToBookEntityMapper bookToBookEntityMapper;

    public BookDisplayDataRepository(BookDisplayLocalDataSource bookDisplayLocalDataSource,
                                     BookDisplayRemoteDataSource bookDisplayRemoteDataSource) {
        this.bookDisplayRemoteDataSource = bookDisplayRemoteDataSource;
        this.bookDisplayLocalDataSource = bookDisplayLocalDataSource;
        this.bookToBookEntityMapper = new BookToBookEntityMapper();
    }

    @Override
    public Single<BookSearchResponse> getBookSearchResponse(String keywords) {
        return bookDisplayRemoteDataSource.getBookSearchResponse((keywords))
                .zipWith(bookDisplayLocalDataSource.getFavoriteIdList(), new BiFunction<BookSearchResponse, List<String>, BookSearchResponse>() {
            @NonNull
            @Override
            public BookSearchResponse apply(@NonNull BookSearchResponse bookSearchResponse, @NonNull List<String> idList) throws Exception {
                List<Book> books = bookSearchResponse.getBookList();
                for(Book b : books){
                    if(idList.contains(b.getId())){
                        b.setFavorite();
                    }
                }
                bookSearchResponse.setBookList(books);
                return bookSearchResponse;
            }
        });
    }

    @Override
    public Flowable<List<BookEntity>> getFavoriteBooks() {
        return bookDisplayLocalDataSource.getFavoriteBooks();
    }

    @Override
    public Completable addBookToFavorites(String id) {
        return bookDisplayRemoteDataSource.getBookDetails(id)
                .map(new Function<Book, BookEntity>() {
                    @Override
                    public BookEntity apply(@NonNull Book book) throws Exception {
                        return bookToBookEntityMapper.map(book);
                    }
                }).flatMapCompletable(new Function<BookEntity, CompletableSource>() {
                    @Override
                    public CompletableSource apply(@NonNull BookEntity bookEntity) throws Exception {
                        return bookDisplayLocalDataSource.addBookToFavorites(bookEntity);
                    }
                });
    }

    @Override
    public Completable removeBookFromFavorites(String id) {
        return bookDisplayLocalDataSource.removeBookFromFavorites(id);
    }
}
