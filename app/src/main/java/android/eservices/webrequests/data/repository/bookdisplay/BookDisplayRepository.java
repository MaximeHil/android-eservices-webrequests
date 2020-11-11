package android.eservices.webrequests.data.repository.bookdisplay;

import android.eservices.webrequests.data.api.model.BookSearchResponse;
import android.eservices.webrequests.data.entity.BookEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface BookDisplayRepository {

    Single<BookSearchResponse> getBookSearchResponse(String keywords);

    Flowable<List<BookEntity>> getFavoriteBooks();

    Completable addBookToFavorites(String id);

    Completable removeBookFromFavorites(String id);
}
