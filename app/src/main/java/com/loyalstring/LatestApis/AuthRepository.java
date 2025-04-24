package com.loyalstring.LatestApis;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private AuthService authService;

    public AuthRepository() {
        authService = ApiClient.getRetrofitInstance().create(AuthService.class);
    }

    public void login(String username, String password, Callback<LoginResponse> callback) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}