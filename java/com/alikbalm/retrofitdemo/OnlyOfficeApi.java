package com.alikbalm.retrofitdemo;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface OnlyOfficeApi {

    @GET("api/2.0/mail/folders")
    Call<ResponseBody> rootMailFolders(
            @Header("Authorization") String token
    );



}