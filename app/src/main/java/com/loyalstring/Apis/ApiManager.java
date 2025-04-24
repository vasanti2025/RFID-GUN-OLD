package com.loyalstring.Apis;

import android.util.Log;

import com.loyalstring.apiresponse.AlllabelResponse;
import com.loyalstring.apiresponse.ClientCodeRequest;
import com.loyalstring.apiresponse.Rfidresponse;
import com.loyalstring.apiresponse.SkuResponse;
import com.loyalstring.interfaces.ApiService;
import com.loyalstring.interfaces.interfaces;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ApiManager {


    private ApiService apiService;

    public ApiManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public void fetchAllSKU(String clientCode, interfaces.ApiCallback<List<SkuResponse>> callback) {
        new Thread(() -> {
            try {
                Call<List<SkuResponse>> call = apiService.getAllSKU(new ClientCodeRequest(clientCode));
                Response<List<SkuResponse>> response = call.execute();
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error fetching SKU: " + response.errorBody().string()));
                }
            } catch (IOException e) {
                callback.onError(e);
            }
        }).start();
    }

    public void fetchAllLabeledStock(String clientCode, interfaces.ApiCallback<List<AlllabelResponse.LabelItem>> callback) {
        new Thread(() -> {
            try {
                Call<List<AlllabelResponse.LabelItem>> call = apiService.getAlllableproducts(new ClientCodeRequest(clientCode));
                Response<List<AlllabelResponse.LabelItem>> response = call.execute();
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error fetching Labeled Stock: " + response.errorBody().string()));
                }
            } catch (IOException e) {
                callback.onError(e);
            }
        }).start();
    }


    public void fetchallrfid(String clientcode, interfaces.OnRFIDFetched onRFIDFetched) {
        new Thread(() -> {
            try {
                ClientCodeRequest clientCodeRequest = new ClientCodeRequest(clientcode);
                Call<List<Rfidresponse.ItemModel>> call = apiService.getRfiddata(clientCodeRequest);
                Response<List<Rfidresponse.ItemModel>> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {
                    // Convert the list to JSON or any other format if required for onSuccess
                    onRFIDFetched.onSuccess(response.body());
                } else {
                    onRFIDFetched.onError(new Exception("Error fetching Labeled Stock: " + response.errorBody().string()));
                }
            } catch (IOException e) {
                onRFIDFetched.onError(e);
            }
        }).start();
    }



}
