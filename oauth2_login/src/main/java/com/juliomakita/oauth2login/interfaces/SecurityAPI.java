package com.juliomakita.oauth2login.interfaces;

import com.juliomakita.oauth2login.model.SecurityUser;
import com.juliomakita.oauth2login.model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SecurityAPI {

    //@Headers("Content-Type: application/json")
    @Headers({"Accept: application/json"})
    @POST("/generateToken")
    Call<TokenResponse> getToken(@Body SecurityUser securityUser);
}
