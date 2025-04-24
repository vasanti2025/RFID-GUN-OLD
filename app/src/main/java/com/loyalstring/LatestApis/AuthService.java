package com.loyalstring.LatestApis;

import com.loyalstring.apiresponse.AlllabelResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("ClientOnboarding/ClientOnboardingLogin")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);


}