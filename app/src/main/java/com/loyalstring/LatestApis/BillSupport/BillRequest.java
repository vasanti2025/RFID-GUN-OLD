package com.loyalstring.LatestApis.BillSupport;

public class BillRequest {
    private String ClientCode;
    private String ItemCode;

    // Constructor, getters, and setters


    public BillRequest(String clientCode, String itemCode) {
        ClientCode = clientCode;
        ItemCode = itemCode;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }
}
