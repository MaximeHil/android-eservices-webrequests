package android.eservices.webrequests.data.db;

import android.eservices.webrequests.data.entity.BookEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    Flowable<List<BookEntity>> getFavorites();

    @Insert
    Completable addBook(BookEntity bookEntity);

    @Query("DELETE FROM books WHERE id = :id")
    Completable deleteBook(String id);



    @Query("SELECT id from books")
    Single<List<String>> getFavoriteIdList();

}
