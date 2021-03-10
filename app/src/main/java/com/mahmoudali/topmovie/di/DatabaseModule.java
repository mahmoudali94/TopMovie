package com.mahmoudali.topmovie.di;

import android.app.Application;

import androidx.room.Room;

import com.mahmoudali.topmovie.Utils.Constants;
import com.mahmoudali.topmovie.db.FavoriteDao;
import com.mahmoudali.topmovie.db.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {


    @Provides
    @Singleton
    MovieDatabase provideMovieDatabase(Application application){
        return Room.databaseBuilder(application,MovieDatabase.class, Constants.DataBaseName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    FavoriteDao provideFavoriteDao(MovieDatabase movieDatabase){
        return movieDatabase.favoriteDao();
    }
}
