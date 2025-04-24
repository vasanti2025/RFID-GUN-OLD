package com.loyalstring.interfaces;

import com.loyalstring.apiresponse.Rfidresponse;

import java.util.List;

public interface FetchRfidCallback {
    void onFetchCompleted(List<Rfidresponse.ItemModel> rfidList);
}
