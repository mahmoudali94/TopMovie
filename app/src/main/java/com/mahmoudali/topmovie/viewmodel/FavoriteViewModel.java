package com.mahmoudali.topmovie.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoudali.topmovie.db.FavoriteMovie;
import com.mahmoudali.topmovie.repository.Repository;

import java.util.List;

public class FavoriteViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<FavoriteMovie>> favoriteMoviesList;

    @ViewModelInject
    public FavoriteViewModel(Repository repository) {
        this.repository = repository;
        favoriteMoviesList = repository.getFavoriteList();
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMoviesList() {
        return favoriteMoviesList;
    }

    public void clearWishList(){
        repository.clearFavoriteList();
    }
}
