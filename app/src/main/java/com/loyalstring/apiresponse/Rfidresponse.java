package com.loyalstring.apiresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Rfidresponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<ItemModel> data;

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemModel> getData() {
        return data;
    }

    public void setData(List<ItemModel> data) {
        this.data = data;
    }


    public static class ItemModel {
        @SerializedName("TidValue")
        private String tid;

        @SerializedName("BarcodeNumber")
        private String barcodeNumber;

        @SerializedName("id")
        private int id;

        @SerializedName("createdOn")
        private String createdOn;

        @SerializedName("lastUpdated")
        private String lastUpdated;

        @SerializedName("statusType")
        private boolean statusType;


        public ItemModel() {
        }

        public ItemModel(String tid, String barcodeNumber) {
            this.tid = tid;
            this.barcodeNumber = barcodeNumber;
        }

        // Getters and Setters
        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getBarcodeNumber() {
            return barcodeNumber;
        }

        public void setBarcodeNumber(String barcodeNumber) {
            this.barcodeNumber = barcodeNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public boolean isStatusType() {
            return statusType;
        }

        public void setStatusType(boolean statusType) {
            this.statusType = statusType;
        }


        @Override
        public String toString() {
            return "ItemModel{" +
                    "tid='" + tid + '\'' +
                    ", barcodeNumber='" + barcodeNumber + '\'' +
                    ", id=" + id +
                    ", createdOn='" + createdOn + '\'' +
                    ", lastUpdated='" + lastUpdated + '\'' +
                    ", statusType=" + statusType +
                    '}';
        }
    }


}
