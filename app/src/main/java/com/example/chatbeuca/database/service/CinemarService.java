package com.example.chatbeuca.database.service;

import android.content.Context;

import com.example.chatbeuca.asyncTask.Callback;
import com.example.chatbeuca.database.dao.CinemaDao;

import java.util.List;
import java.util.concurrent.Callable;

import com.example.chatbeuca.asyncTask.AsyncTaskRunner;
import com.example.chatbeuca.database.DatabaseManager3;
import com.example.chatbeuca.database.model.Cinema;
import com.example.chatbeuca.database.model.Movie;

public class CinemarService {
    private final CinemaDao cinemaDao;
    private final AsyncTaskRunner asyncTask;

    public CinemarService(Context context) {
        cinemaDao = DatabaseManager3.getInstance(context)
                .getCinemaiDao();
        asyncTask = new AsyncTaskRunner();
    }

    public void getAllByCategory(final String denumire, final Callback<List<Cinema>> callback) {
        Callable<List<Cinema>> callable = new Callable<List<Cinema>>() {
            @Override
            public List<Cinema> call() {
                if (denumire == null || denumire.trim().isEmpty()) {
                    return null;
                }
                return cinemaDao.getAllByCategory(denumire);
            }
        };

        asyncTask.executeAsync(callable, callback);
    }

    public void getAll(Callback<List<Cinema>> callback) {
        Callable<List<Cinema>> callable = new Callable<List<Cinema>>() {
            @Override
            public List<Cinema> call() {
                return cinemaDao.getAll();
            }
        };
        asyncTask.executeAsync(callable, callback);
    }

    public void insert(final Cinema cinema,
                       Callback<Cinema> callback) {
        Callable<Cinema> callable = new Callable<Cinema>() {
            @Override
            public Cinema call() {
                if (cinema == null) {
                    return null;
                }
                long id = cinemaDao.insert(cinema);
                if (id == -1) {
                    return null;
                }
                cinema.setId((int) id);
                return cinema;
            }
        };
        asyncTask.executeAsync(callable, callback);
    }

    public void update(final Cinema cinema,
                       Callback<Cinema> callback) {
        Callable<Cinema> callable = new Callable<Cinema>() {
            @Override
            public Cinema call() {
                if (cinema == null) {
                    return null;
                }
                int count = (int) cinemaDao.update(cinema);
                if (count != 1) {
                    return null;
                }
                return cinema;
            }
        };
        asyncTask.executeAsync(callable, callback);
    }

    public void delete(final Cinema cinema,
                       Callback<Integer> callback) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (cinema == null) {
                    return -1;
                }
                return cinemaDao.delete(cinema);
            }
        };
        asyncTask.executeAsync(callable, callback);
    }

}
