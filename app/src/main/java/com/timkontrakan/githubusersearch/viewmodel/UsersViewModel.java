package com.timkontrakan.githubusersearch.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.timkontrakan.githubusersearch.R;
import com.timkontrakan.githubusersearch.api.Api;
import com.timkontrakan.githubusersearch.api.ApiInterface;
import com.timkontrakan.githubusersearch.model.ResponseUser;
import com.timkontrakan.githubusersearch.model.Users;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends ViewModel {

    private final MutableLiveData<List<Users>> usersList = new MutableLiveData<>();

    public MutableLiveData<List<Users>> getUsersList() {
        return usersList;
    }

    public void setListUsers(String username, Context context) {
        ApiInterface apiInterface;
        retrofit2.Call<ResponseUser> call;

        try {
            apiInterface = Api.getApi().create(ApiInterface.class);
            call = apiInterface.getListUser(username);
            call.enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    Log.d("Response", "" + " " + response.body());
                    List<Users> listUser;
                    assert response.body() != null;
                    listUser = response.body().getmResultMember();
                    usersList.postValue(listUser);
                    if (listUser.isEmpty()) {
                        Toast.makeText(context, R.string.not_found, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    Log.d("Message", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
