package com.loyalstring.apiresponse;

import com.google.gson.annotations.SerializedName;
import com.loyalstring.modelclasses.Productmodel;

import java.util.List;

public class ProductResponse {
//    @SerializedName("data")
//    private List<Productmodel> data;
//
//    public List<Productmodel> getData() {
//        return data;
//    }

    List<Productmodel> data;

    public List<Productmodel> getData() {
        return data;
    }

    public void setData(List<Productmodel> data) {
        this.data = data;
    }
}
