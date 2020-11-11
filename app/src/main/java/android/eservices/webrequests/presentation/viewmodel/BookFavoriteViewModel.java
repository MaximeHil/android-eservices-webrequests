package android.eservices.webrequests.presentation.viewmodel;

import android.eservices.webrequests.data.api.model.BookSearchResponse;
import android.eservices.webrequests.data.repository.bookdisplay.BookDisplayRepository;
import android.eservices.webrequests.presentation.bookdisplay.favorite.mapper.BookEntityToDetailViewModelMapper;
import android.eservices.webrequests.presentation.bookdisplay.search.adapter.BookViewItem;
import android.eservices.webrequests.presentation.bookdisplay.search.mapper.BookToViewModelMapper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BookFavoriteViewModel extends ViewModel {

    private BookDisplayRepository bookDisplayRepository;
    private CompositeDisposable compositeDisposable;
    private BookEntityToDetailViewModelMapper bookEntityToDetailViewModelMapper;

    public BookFavoriteViewModel(BookDisplayRepository bookDisplayRepository) {
        this.bookDisplayRepository = bookDisplayRepository;
        this.compositeDisposable = new CompositeDisposable();
        this.bookEntityToDetailViewModelMapper = new BookEntityToDetailViewModelMapper();
    }

    private MutableLiveData<List<BookViewItem>> favoriteBooks = new MutableLiveData<>();
    //private MutableLiveData<Boolean> isDataLoading = new MutableLiveData<Boolean>();

    public MutableLiveData<List<BookViewItem>> getFavoriteBooks() {
        return favoriteBooks;
    }

    /*public MutableLiveData<Boolean> getIsDataLoading() {
        return isDataLoading;
    }
    */

    public void handleToggleFavorite(String bookId, boolean isFavorite){

        if(isFavorite) {
            compositeDisposable.clear();
            compositeDisposable.add(bookDisplayRepository.addBookToFavorites(bookId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {

                        @Override
                        public void onComplete() {
                            //favoriteBooks.setValue(bookDisplayRepository.);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            System.out.println(e.toString());
                        }
                    }));
        } else {
            compositeDisposable.clear();
            compositeDisposable.add(bookDisplayRepository.removeBookFromFavorites(bookId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableCompletableObserver() {

                        @Override
                        public void onComplete() {
                            System.out.println("success");
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            System.out.println(e.toString());
                        }
                    }));
        }

    }

}
