package mx.mango.pics.rest;

import mx.mango.pics.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("users")
    Call<User> signup(@Body User user);
}