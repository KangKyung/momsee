package com.example.android0211.Retrofit;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import io.reactivex.Observable;


public interface INodeJS {

    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password")String password);

    @POST("register_child")
    @FormUrlEncoded
    Observable<String> registerUser_child(@Field("child_name")String child_name,
                                         @Field("email")String email,
                                         @Field("child_age")String child_age);
    @POST("login_child")
    @FormUrlEncoded
    Observable<String> loginUser_child(@Field("email") String email,
                        @Field("child_name") String child_name);
    @POST("lock_unlock")
    @FormUrlEncoded
    Observable<String> lock_unlock(@Field("email")String email);
}
