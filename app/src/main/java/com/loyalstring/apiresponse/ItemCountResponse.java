package com.loyalstring.apiresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemCountResponse {
    private String status;
    @SerializedName("itemCount")
    private int itemCount;

    // Getters and Setters
}

/*
public class Item {
    @SerializedName("rfid_tag")
    private String rfidTag;
    // Add the rest of the fields here following the same pattern

    // Getters and Setters
}

public class ItemDataResponse {
    private String status;
    private List<Item> data;

    // Getters and Setters
}*/
