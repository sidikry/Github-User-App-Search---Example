package com.timkontrakan.githubusersearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUser {

    @SerializedName("items")
    @Expose
    private List<Users> mResultMember;

    public List<Users> getmResultMember() {
        return mResultMember;
    }

    public void setmResultMember(List<Users> mResultMember) {
        this.mResultMember = mResultMember;
    }
}
