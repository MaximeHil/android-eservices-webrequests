package android.eservices.webrequests.presentation.viewmodel;

import android.eservices.webrequests.data.entity.BookEntity;
import android.eservices.webrequests.data.repository.bookdisplay.BookDisplayRepository;
import android.eservices.webrequests.presentation.bookdisplay.favorite.adapter.BookDetailViewItem;
import android.eservices.webrequests.presentation.bookdisplay.favorite.mapper.BookEntityToDetailViewModelMapper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class BookFavoriteViewModel extends ViewModel {

    private BookDisplayRepository bookDisplayRepository;
    private CompositeDisposable compositeDisposable;
    private BookEntityToDetailViewModelMapper bookEntityToDetailViewModelMapper;

    public BookFavoriteViewModel(BookDisplayRepository bookDisplayRepository) {
        this.bookDisplayRepository = bookDisplayRepository;
        this.compositeDisposable = new CompositeDisposable();
        this.bookEntityToDetailViewModelMapper = new BookEntityToDetailViewModelMapper();
    }

    private MutableLiveData<List<BookDetailViewItem>> favoriteBooks;
    private MutableLiveData<Boolean> isDataLoading = new MutableLiveData<>();
    final MutableLiveData<Event<String>> bookAddedEvent = new MutableLiveData<>();
    final MutableLiveData<Event<String>> bookDeletedEvent = new MutableLiveData<>();

    public MutableLiveData<List<BookDetailViewItem>> getFavoriteBooks() {
        isDataLoading.setValue(true);
        Log.i("Test getBooks", "En train de recuperer les books");

        if(favoriteBooks == null){
            Log.i("Test if", "Dans le if");
            favoriteBooks = new MutableLiveData<>();
            compositeDisposable.add(bookDisplayRepository.getFavoriteBooks()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ResourceSubscriber<List<BookEntity>>() {
                        @Override
                        public void onNext(List<BookEntity> bookEntities) {
                            Log.i("Test onNext", "Dans le onNext");
                            isDataLoading.setValue(false);
                            favoriteBooks.setValue(bookEntityToDetailViewModelMapper.map(bookEntities));
                        }

                        @Override
                        public void onError(Throwable t) {
                            isDataLoading.setValue(false);
                        }

                        @Override
                        public void onComplete() {
                            isDataLoading.setValue(false);
                        }
                    }));
        }
        return favoriteBooks;
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        return isDataLoading;
    }

    public MutableLiveData<Event<String>> getBookAddedEvent() {
        return bookAddedEvent;
    }

    public MutableLiveData<Event<String>> getBookDeletedEvent() {
        return bookDeletedEvent;
    }

    public void addBookToFavorite(final String bookId){
        compositeDisposable.add(bookDisplayRepository.addBookToFavorites(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        bookAddedEvent.setValue(new Event<>(bookId));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println(e.toString());
                    }
                }));
    }

    public void removeBookFromFavorite(final String bookId){
        compositeDisposable.add(bookDisplayRepository.removeBookFromFavorites(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        bookDeletedEvent.setValue(new Event<>(bookId));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println(e.toString());
                    }
                }));
    }



}
