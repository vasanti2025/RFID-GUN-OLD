package com.loyalstring.apiresponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SkuResponse {
    @SerializedName("Id")
    public int id1;
    @SerializedName("StockKeepingUnit")
    public String stockKeepingUnit;
    @SerializedName("EmployeeId")
    public int employeeId;
    @SerializedName("SketchNo")
    public String sketchNo;
    @SerializedName("Description")
    public String description;
    @SerializedName("ProductRemark")
    public String productRemark;
    @SerializedName("CategoryId")
    public int categoryId;
    @SerializedName("ProductId")
    public int productId;
    @SerializedName("DesignId")
    public int designId;
    @SerializedName("PurityId")
    public int purityId;
    @SerializedName("Colour")
    public String colour;
    @SerializedName("Size")
    public String size;
    @SerializedName("HSNCode")
    public String hSNCode;
    @SerializedName("MetalName")
    public String metalName;
    @SerializedName("GrossWt")
    public String grossWt;
    @SerializedName("NetWt")
    public String netWt;
    @SerializedName("CollectionName")
    public String collectionName;
    @SerializedName("OccassionName")
    public String occassionName;
    @SerializedName("Gender")
    public String gender;
    @SerializedName("MRP")
    public String mRP;
    @SerializedName("Images")
    public String images;
    @SerializedName("MakingFixedAmt")
    public String makingFixedAmt;
    @SerializedName("MakingPerGram")
    public String makingPerGram;
    @SerializedName("MakingFixedWastage")
    public String makingFixedWastage;
    @SerializedName("MakingPercentage")
    public String makingPercentage;
    @SerializedName("TotalStoneWeight")
    public String totalStoneWeight;
    @SerializedName("TotalStoneAmount")
    public String totalStoneAmount;
    @SerializedName("TotalStonePieces")
    public String totalStonePieces;
    @SerializedName("TotalDiamondWeight")
    public String totalDiamondWeight;
    @SerializedName("TotalDiamondPieces")
    public String totalDiamondPieces;
    @SerializedName("TotalDiamondAmount")
    public String totalDiamondAmount;
    @SerializedName("Featured")
    public String featured;
    @SerializedName("Pieces")
    public String pieces;
    @SerializedName("HallmarkAmount")
    public String hallmarkAmount;
    @SerializedName("HUIDCode")
    public String hUIDCode;
    @SerializedName("BoxId")
    public String boxId;
    @SerializedName("Quantity")
    public String quantity;
    @SerializedName("BlackBeads")
    public String blackBeads;
    @SerializedName("Height")
    public String height;
    @SerializedName("Width")
    public String width;
    @SerializedName("CuttingGrossWt")
    public String cuttingGrossWt;
    @SerializedName("CuttingNetWt")
    public String cuttingNetWt;
    @SerializedName("MetalRate")
    public String metalRate;
    @SerializedName("PurchaseCost")
    public String purchaseCost;
    @SerializedName("Margin")
    public String margin;
    @SerializedName("BranchName")
    public String branchName;
    @SerializedName("BoxName")
    public String boxName;
    @SerializedName("EstimatedDays")
    public String estimatedDays;
    @SerializedName("OfferPrice")
    public String offerPrice;
    @SerializedName("Ranking")
    public String ranking;
    @SerializedName("CompanyId")
    public int companyId;
    @SerializedName("CounterId")
    public int counterId;
    @SerializedName("BranchId")
    public int branchId;
    @SerializedName("Status")
    public String status;
    @SerializedName("ClientCode")
    public String clientCode;
    @SerializedName("VendorId")
    public int vendorId;
    @SerializedName("MinQuantity")
    public String minQuantity;
    @SerializedName("MinWeight")
    public String minWeight;
    @SerializedName("WeightCategories")
    public String weightCategories;
    @SerializedName("TagWeight")
    public String tagWeight;
    @SerializedName("FindingWeight")
    public String findingWeight;
    @SerializedName("LanyardWeight")
    public String lanyardWeight;
    @SerializedName("OtherWeight")
    public String otherWeight;
    @SerializedName("PouchWeight")
    public String pouchWeight;
    @SerializedName("ProductName")
    public String productName;
    @SerializedName("CategoryName")
    public String categoryName;
    @SerializedName("DesignName")
    public String designName;
    @SerializedName("PurityName")
    public String purityName;
    @SerializedName("StoneLessPercent")
    public Object stoneLessPercent;
    @SerializedName("ClipWeight")
    public String clipWeight;
    @SerializedName("CollectionId")
    public int collectionId;
    @SerializedName("CollectionNameSKU")
    public Object collectionNameSKU;
    @SerializedName("SKUVendor")
    public ArrayList<SKUVendor> sKUVendor;
    @SerializedName("Diamonds")
    public ArrayList<Object> diamonds;
    @SerializedName("SKUStoneMain")
    public ArrayList<SKUStoneMain> sKUStoneMain;


    public static class SKUVendor{
        @SerializedName("SKUVendorId")
        public int sKUVendorId;
        @SerializedName("SKUId")
        public int sKUId;
        @SerializedName("VendorId")
        public int vendorId;
        @SerializedName("VendorName")
        public String vendorName;
        @SerializedName("ClientCode")
        public String clientCode;
        @SerializedName("BranchId")
        public int branchId;
        @SerializedName("CompanyId")
        public int companyId;
        @SerializedName("EmployeeId")
        public int employeeId;
    }

    @Override
    public String toString() {
        return "SkuResponse{" +
                "id=" + id1 +
                ", stockKeepingUnit='" + stockKeepingUnit + '\'' +
                ", employeeId=" + employeeId +
                ", sketchNo='" + sketchNo + '\'' +
                ", description='" + description + '\'' +
                ", productRemark='" + productRemark + '\'' +
                ", categoryId=" + categoryId +
                ", productId=" + productId +
                ", designId=" + designId +
                ", purityId=" + purityId +
                ", colour='" + colour + '\'' +
                ", size='" + size + '\'' +
                ", hSNCode='" + hSNCode + '\'' +
                ", metalName='" + metalName + '\'' +
                ", grossWt='" + grossWt + '\'' +
                ", netWt='" + netWt + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", occassionName='" + occassionName + '\'' +
                ", gender='" + gender + '\'' +
                ", mRP='" + mRP + '\'' +
                ", images='" + images + '\'' +
                ", makingFixedAmt='" + makingFixedAmt + '\'' +
                ", makingPerGram='" + makingPerGram + '\'' +
                ", makingFixedWastage='" + makingFixedWastage + '\'' +
                ", makingPercentage='" + makingPercentage + '\'' +
                ", totalStoneWeight='" + totalStoneWeight + '\'' +
                ", totalStoneAmount='" + totalStoneAmount + '\'' +
                ", totalStonePieces='" + totalStonePieces + '\'' +
                ", totalDiamondWeight='" + totalDiamondWeight + '\'' +
                ", totalDiamondPieces='" + totalDiamondPieces + '\'' +
                ", totalDiamondAmount='" + totalDiamondAmount + '\'' +
                ", featured='" + featured + '\'' +
                ", pieces='" + pieces + '\'' +
                ", hallmarkAmount='" + hallmarkAmount + '\'' +
                ", hUIDCode='" + hUIDCode + '\'' +
                ", boxId='" + boxId + '\'' +
                ", quantity='" + quantity + '\'' +
                ", blackBeads='" + blackBeads + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                ", cuttingGrossWt='" + cuttingGrossWt + '\'' +
                ", cuttingNetWt='" + cuttingNetWt + '\'' +
                ", metalRate='" + metalRate + '\'' +
                ", purchaseCost='" + purchaseCost + '\'' +
                ", margin='" + margin + '\'' +
                ", branchName='" + branchName + '\'' +
                ", boxName='" + boxName + '\'' +
                ", estimatedDays='" + estimatedDays + '\'' +
                ", offerPrice='" + offerPrice + '\'' +
                ", ranking='" + ranking + '\'' +
                ", companyId=" + companyId +
                ", counterId=" + counterId +
                ", branchId=" + branchId +
                ", status='" + status + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", vendorId=" + vendorId +
                ", minQuantity='" + minQuantity + '\'' +
                ", minWeight='" + minWeight + '\'' +
                ", weightCategories='" + weightCategories + '\'' +
                ", tagWeight='" + tagWeight + '\'' +
                ", findingWeight='" + findingWeight + '\'' +
                ", lanyardWeight='" + lanyardWeight + '\'' +
                ", otherWeight='" + otherWeight + '\'' +
                ", pouchWeight='" + pouchWeight + '\'' +
                ", productName='" + productName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", designName='" + designName + '\'' +
                ", purityName='" + purityName + '\'' +
                ", stoneLessPercent=" + stoneLessPercent +
                ", clipWeight='" + clipWeight + '\'' +
                ", collectionId=" + collectionId +
                ", collectionNameSKU=" + collectionNameSKU +
                ", sKUVendor=" + sKUVendor +
                ", diamonds=" + diamonds +
                ", sKUStoneMain=" + sKUStoneMain +
                '}';
    }
}



