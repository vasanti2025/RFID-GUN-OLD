package com.loyalstring.nmodels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Itemmodelweb {
    @SerializedName("Id")
    public int id;
    @SerializedName("SKUId")
    public int sKUId;
    @SerializedName("ProductTitle")
    public String productTitle;
    @SerializedName("ClipWeight")
    public String clipWeight;
    @SerializedName("ClipQuantity")
    public String clipQuantity;
    @SerializedName("ItemCode")
    public String itemCode;
    @SerializedName("HSNCode")
    public String hSNCode;
    @SerializedName("Description")
    public String description;
    @SerializedName("ProductCode")
    public String productCode;
    @SerializedName("MetalName")
    public String metalName;
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
    @SerializedName("MRP")
    public String mRP;
    @SerializedName("VendorId")
    public int vendorId;
    @SerializedName("VendorName")
    public String vendorName;
    @SerializedName("FirmName")
    public String firmName;
    @SerializedName("BoxId")
    public int boxId;
    @SerializedName("TIDNumber")
    public String tIDNumber;
    @SerializedName("RFIDCode")
    public String rFIDCode;
    @SerializedName("FinePercent")
    public String finePercent;
    @SerializedName("WastagePercent")
    public String wastagePercent;
    @SerializedName("Images")
    public Object images;
    @SerializedName("BlackBeads")
    public String blackBeads;
    @SerializedName("Height")
    public String height;
    @SerializedName("Width")
    public String width;
    @SerializedName("OrderedItemId")
    public Object orderedItemId;
    @SerializedName("CuttingGrossWt")
    public String cuttingGrossWt;
    @SerializedName("CuttingNetWt")
    public String cuttingNetWt;
    @SerializedName("MetalRate")
    public String metalRate;
    @SerializedName("LotNumber")
    public String lotNumber;
    @SerializedName("DeptId")
    public int deptId;
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
    @SerializedName("Rating")
    public String rating;
    @SerializedName("SKU")
    public String sKU;
    @SerializedName("Ranking")
    public String ranking;
    @SerializedName("CompanyId")
    public int companyId;
    @SerializedName("CounterId")
    public int counterId;
    @SerializedName("BranchId")
    public int branchId;
    @SerializedName("EmployeeId")
    public int employeeId;
    @SerializedName("Status")
    public String status;
    @SerializedName("ClientCode")
    public String clientCode;
    @SerializedName("UpdatedFrom")
    public String updatedFrom;
    public int count;
    @SerializedName("MetalId")
    public int metalId;
    @SerializedName("WarehouseId")
    public int warehouseId;
    @SerializedName("CreatedOn")
    public String createdOn;
    @SerializedName("LastUpdated")
    public String lastUpdated;
    @SerializedName("TaxId")
    public int taxId;
    @SerializedName("TaxPercentage")
    public String taxPercentage;
    @SerializedName("OtherWeight")
    public String otherWeight;
    @SerializedName("PouchWeight")
    public String pouchWeight;
    @SerializedName("CategoryName")
    public String categoryName;
    @SerializedName("PurityName")
    public String purityName;
    @SerializedName("TodaysRate")
    public String todaysRate;
    @SerializedName("ProductName")
    public String productName;
    @SerializedName("DesignName")
    public String designName;
    @SerializedName("DiamondSize")
    public String diamondSize;
    @SerializedName("DiamondWeight")
    public String diamondWeight;
    @SerializedName("DiamondPurchaseRate")
    public String diamondPurchaseRate;
    @SerializedName("DiamondSellRate")
    public String diamondSellRate;
    @SerializedName("DiamondClarity")
    public String diamondClarity;
    @SerializedName("DiamondColour")
    public String diamondColour;
    @SerializedName("DiamondShape")
    public String diamondShape;
    @SerializedName("DiamondCut")
    public String diamondCut;
    @SerializedName("DiamondSettingType")
    public String diamondSettingType;
    @SerializedName("DiamondCertificate")
    public String diamondCertificate;
    @SerializedName("DiamondPieces")
    public String diamondPieces;
    @SerializedName("DiamondPurchaseAmount")
    public String diamondPurchaseAmount;
    @SerializedName("DiamondSellAmount")
    public String diamondSellAmount;
    @SerializedName("DiamondDescription")
    public String diamondDescription;
    @SerializedName("TagWeight")
    public String tagWeight;
    @SerializedName("FindingWeight")
    public String findingWeight;
    @SerializedName("LanyardWeight")
    public String lanyardWeight;
    @SerializedName("PacketId")
    public int packetId;
    @SerializedName("PacketName")
    public String packetName;
    @SerializedName("Stones")
    public ArrayList<Object> stones;
    @SerializedName("Diamonds")
    public ArrayList<Object> diamonds;
}


