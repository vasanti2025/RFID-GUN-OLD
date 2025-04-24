package com.loyalstring.apiresponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SKUStoneMain {
    @SerializedName("Id")
    public int id1;
    @SerializedName("StoneMainName")
    public String stoneMainName;
    @SerializedName("StoneMainWeight")
    public String stoneMainWeight;
    @SerializedName("StoneMainPieces")
    public String stoneMainPieces;
    @SerializedName("StoneMainRate")
    public String stoneMainRate;
    @SerializedName("StoneMainAmount")
    public String stoneMainAmount;
    @SerializedName("StoneMainDescription")
    public String stoneMainDescription;
    @SerializedName("SKUId")
    public int sKUId;
    @SerializedName("CompanyId")
    public int companyId;
    @SerializedName("CounterId")
    public int counterId;
    @SerializedName("BranchId")
    public int branchId;
    @SerializedName("ClientCode")
    public String clientCode;
    @SerializedName("EmployeeId")
    public int employeeId;
    @SerializedName("SKUStoneItem")
    public ArrayList<SKUStoneItem> sKUStoneItem;

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public String getStoneMainName() {
        return stoneMainName;
    }

    public void setStoneMainName(String stoneMainName) {
        this.stoneMainName = stoneMainName;
    }

    public String getStoneMainWeight() {
        return stoneMainWeight;
    }

    public void setStoneMainWeight(String stoneMainWeight) {
        this.stoneMainWeight = stoneMainWeight;
    }

    public String getStoneMainPieces() {
        return stoneMainPieces;
    }

    public void setStoneMainPieces(String stoneMainPieces) {
        this.stoneMainPieces = stoneMainPieces;
    }

    public String getStoneMainRate() {
        return stoneMainRate;
    }

    public void setStoneMainRate(String stoneMainRate) {
        this.stoneMainRate = stoneMainRate;
    }

    public String getStoneMainAmount() {
        return stoneMainAmount;
    }

    public void setStoneMainAmount(String stoneMainAmount) {
        this.stoneMainAmount = stoneMainAmount;
    }

    public String getStoneMainDescription() {
        return stoneMainDescription;
    }

    public void setStoneMainDescription(String stoneMainDescription) {
        this.stoneMainDescription = stoneMainDescription;
    }

    public int getsKUId() {
        return sKUId;
    }

    public void setsKUId(int sKUId) {
        this.sKUId = sKUId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public ArrayList<SKUStoneItem> getsKUStoneItem() {
        return sKUStoneItem;
    }

    public void setsKUStoneItem(ArrayList<SKUStoneItem> sKUStoneItem) {
        this.sKUStoneItem = sKUStoneItem;
    }
}
