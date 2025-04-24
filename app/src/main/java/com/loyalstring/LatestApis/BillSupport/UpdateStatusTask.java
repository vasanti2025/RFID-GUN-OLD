package com.loyalstring.LatestApis.BillSupport;

import android.os.AsyncTask;
import android.util.Log;

import com.loyalstring.LatestApis.ApiClient;
import com.loyalstring.LatestCallBacks.ActivationCallback;
import com.loyalstring.apiresponse.AlllabelResponse;
import com.loyalstring.interfaces.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateStatusTask extends AsyncTask<Void, Void, String> {
    private final List<String> itemCodes; // List of Item Codes
    private final String clientCode;       // Single Client Code
    ActivationCallback activationCallback;

    // Constructor
    public UpdateStatusTask(List<String> itemCodes, String clientCode, ActivationCallback activationCallback) {
        this.itemCodes = itemCodes;
        this.clientCode = clientCode;
        this.activationCallback = activationCallback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        List<BillRequest> requestItems = new ArrayList<>();

        // Create payload list with item codes and the client code
        for (String itemCode : itemCodes) {
            requestItems.add(new BillRequest(clientCode, itemCode));
        }

        try {
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            retrofit2.Response<List<AlllabelResponse.LabelItem>> response =
                    apiService.updateBarcodeItemStatus(requestItems).execute();

            if (response.isSuccessful() && response.body() != null) {
                List<AlllabelResponse.LabelItem> responseItems = response.body();
                Log.e("API_SUCCESS", "Request Success with code: " + response.code());
                return null; // Handle the successful response here
            } else {
                Log.e("API_ERROR", "Request failed with code: " + response.code());
                // Optionally log response.errorBody() for more details
                return "Request failed with code: " + response.code();
            }
        } catch (IOException e) {
            Log.e("API_EXCEPTION", "IOException occurred while making the request: " + e.getMessage());
            return "IOException: " + e.getMessage();
        } catch (Exception e) {
            Log.e("API_EXCEPTION", "Exception occurred while making the request: " + e.getMessage());
            return "Exception: " + e.getMessage();
        }

//        return null;
    }

    @Override
    protected void onPostExecute(String errorMessage) {
        super.onPostExecute(errorMessage);
        if (errorMessage == null) {
            activationCallback.onSaveSuccess();
        } else {
            activationCallback.onFailed(errorMessage);
        }
    }
}
