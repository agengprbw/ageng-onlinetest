package com.test.agengtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("todos")
    Call<List<Todo>> getTodos();

    @GET("todos/{id}")
    Call<Todo> getTodoDetail(@Path("id") int id);
}


