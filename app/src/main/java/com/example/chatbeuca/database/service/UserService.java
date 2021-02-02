package com.example.chatbeuca.database.service;

import android.content.Context;

import com.example.chatbeuca.asyncTask.Callback;
import com.example.chatbeuca.database.dao.UsersDao;
import com.example.chatbeuca.database.model.User;

import java.util.List;
import java.util.concurrent.Callable;

import com.example.chatbeuca.asyncTask.AsyncTaskRunner;
import com.example.chatbeuca.database.DatabaseManager;

public class UserService {
    private final UsersDao usersDao;
    private final AsyncTaskRunner asyncTask;

    public UserService(Context context) {
        usersDao = DatabaseManager.getInstance(context)
                    .getUserDao();
        asyncTask = new AsyncTaskRunner();
    }

    public void getAllByCategory(final String category, final Callback<List<User>> callback) {
        Callable<List<User>> callable = new Callable<List<User>>() {
            @Override
            public List<User> call() {
                if (category == null || category.trim().isEmpty()) {
                    return null;
                }
                return usersDao.getAllByCategory(category);
            }
        };

        asyncTask.executeAsync(callable, callback);
    }

    public void getAll(Callback<List<User>> callback) {
        Callable<List<User>> callable = new Callable<List<User>>() {
            @Override
            public List<User> call() {
                return usersDao.getAll();
            }
        };
        asyncTask.executeAsync(callable, callback);
    }

    public void insert(final User user,
                       Callback<User> callback) {
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() {
                if (user == null) {
                    return null;
                }
                long id = usersDao.insert(user);
                if (id == -1) {
                    return null;
                }
                user.setId(id);
                return user;
            }
        };
        asyncTask.executeAsync(callable, callback);
    }

    public void update(final User user,
                       Callback<User> callback) {
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() {
                if (user == null) {
                    return null;
                }
                int count = usersDao.update(user);
                if (count != 1) {
                    return null;
                }
                return user;
            }
        };
        asyncTask.executeAsync(callable, callback);
    }

    public void delete(final User user,
                       Callback<Integer> callback) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (user == null) {
                    return -1;
                }
                return usersDao.delete(user);
            }
        };
        asyncTask.executeAsync(callable, callback);
    }

}
