package com.timkontrakan.githubusersearch.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.timkontrakan.githubusersearch.R;
import com.timkontrakan.githubusersearch.api.Api;
import com.timkontrakan.githubusersearch.api.ApiInterface;
import com.timkontrakan.githubusersearch.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersViewModel extends ViewModel {
    private final MutableLiveData<List<Users>> listFollowers = new MutableLiveData<>();
    private Context context;


    public MutableLiveData<List<Users>> getListFollowers() {
        return listFollowers;
    }

    public void setListFollowers(String username, Context context){
        ApiInterface apiInterface;
        retrofit2.Call<List<Users>> listCall;

        try {
            apiInterface = Api.getApi().create(ApiInterface.class);
            listCall = apiInterface.getListFollowers(username);
            listCall.enqueue(new Callback<List<Users>>() {
                @Override
                public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                    Log.d("Response", "" + " " + response.body());
                    List<Users> usersList;
                    usersList = response.body();
                    listFollowers.postValue(usersList);
                    if (usersList.isEmpty()){
                        Toast.makeText(context, context.getString(R.string.belum_ada_followers), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Users>> call, Throwable t) {
                    Log.d("Message", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
