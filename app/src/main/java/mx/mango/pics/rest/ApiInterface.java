package mx.mango.pics.rest;

import java.util.List;

import mx.mango.pics.models.ApiSnap;
import mx.mango.pics.models.ApiUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("users")
    Call<ApiUser> signup(@Body ApiUser apiUser);
    @POST("login")
    Call<ApiUser> login(@Body ApiUser apiUser);
    @POST("snaps")
    Call<ApiSnap> createSnap(@Body ApiSnap apiSnap);
    @GET("users/{user_id}/snaps")
    Call<List<ApiSnap>> getSnaps(@Path("user_id") String user);
}