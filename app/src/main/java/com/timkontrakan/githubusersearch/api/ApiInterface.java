package com.timkontrakan.githubusersearch.api;

import com.timkontrakan.githubusersearch.model.ResponseUser;
import com.timkontrakan.githubusersearch.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/search/users")
    Call<ResponseUser> getListUser(@Query("q") String username);

    @GET("/users/{username}")
    Call<Users> getDetailUser(@Path("username") String username);

    @GET("/users/{username}/followers")
    Call<List<Users>> getListFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    Call<List<Users>> getListFollowing(@Path("username") String username);
}
