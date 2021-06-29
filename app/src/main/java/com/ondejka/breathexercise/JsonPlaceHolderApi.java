package com.ondejka.breathexercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("auth/login/")
    Call<Token> getToken(@Body Login login);

    @FormUrlEncoded
    @POST("auth/registration/")
    Call<Token> registration(@Field("username") String login,
                             @Field("password1") String password1,
                             @Field("password2") String password2);

    @GET("score")
    Call<List<ResultFromCloud>> getScore(@Header("Authorization") String token);

    @POST("score")
    Call<ResultFromCloud> postScore(@Body ScoreForPostToCloud score,
                                    @Header("Authorization") String token
    );


}
