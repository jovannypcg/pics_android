package mx.mango.pics.rest;

import mx.mango.pics.models.ApiUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("users")
    Call<ApiUser> signup(@Body ApiUser apiUser);
   @POST("login")
    Call<ApiUser> login(@Body ApiUser apiUser);
}