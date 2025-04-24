package com.loyalstring.apiresponse;

import java.util.List;

public class ProductUpdateResponse {
    String status;

    List<ProductUpdate> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductUpdate> getData() {
        return data;
    }

    public void setData(List<ProductUpdate> data) {
        this.data = data;
    }
}
