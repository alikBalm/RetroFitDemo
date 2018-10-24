package com.alikbalm.retrofitdemo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FileUploadService {
    @Multipart
    @POST("api/2.0/files/{folderId}/upload")
    Call<ResponseBody> upload(
            @Header("Authorization") String token,
            @Header("Content-Type") String form_data,
            @Path("folderId") String folderId,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);
}