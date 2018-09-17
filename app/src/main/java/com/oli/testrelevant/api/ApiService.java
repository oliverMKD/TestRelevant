package com.oli.testrelevant.api;

import com.oli.testrelevant.klasi.Rewards;
import com.oli.testrelevant.klasi.User;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    @POST("user/signup")
    @FormUrlEncoded
    Call<User> getToken(@Field("email") String email, @Field("register_type") int register_type, @Field("appkey") String appkey, @Field("register_device_type") String device);
    @GET("rewards?")
    Call<Rewards> getNagradi(@Query("auth_token") String token, @Query("appkey") String appkey);


}

