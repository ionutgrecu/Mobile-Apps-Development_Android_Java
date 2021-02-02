package com.example.chatbeuca.asyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
