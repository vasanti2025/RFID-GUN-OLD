package com.loyalstring.LatestApis;

public class ProductRequestPayload  {
    private String ClientCode;
    private String Status;
    private int Id;
    private String Size;

    public ProductRequestPayload(String clientCode, String status, int id, String size) {
        this.ClientCode = clientCode;
        this.Status = status;
        this.Id = id;
        this.Size = size;
    }

    // Getters and setters
}