package com.loyalstring.modelclasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Issuemode implements Parcelable {

    long OperationTime, EntryDate, TransactionDate, RepaymentDate;
    String TidValue, EpcValue, Branch, Category, Product, Purity, DiamondMetal, DiamondColor, DiamondClarity, DiamondSetting,
            DiamondShape, DiamondSize, DiamondCertificate, BarCode, ItemCode, Box, HuidCode, PartyCode, Description,
            Status, TagTransaction, Operation, TransactionType, InvoiceNumber, CustomerName, ItemAddmode,
            PaymentMode, PaymentDescription, GstApplied;
    double DiamondWt, DiamondPcs,
            DiamondRate, DiamondAmount,
            GrossWt, StoneWt, NetWt, MakingGm, MakingPer, FixedAmount,
            FixedWastage, StoneAmount, Mrp, HallmarkCharges,
            AvlQty, MatchQty, TotalGwt, MatchGwt, TotalStonewt, MatchStonewt, TotalNwt, MatchNwt,
            GoldRate, TotalMaking, ItemPrice, AppliedDiscount, ItempriceAfterdiscount, GstRate, PayableAmount, PayableAmountincgst, ItemGst,
            TotalBilleditems, TotalBilledgwt, TotalBilledamount, TotalBillAmountExcGst, TotalBillAmountincgst,
            TotalGst, TotalDiscount, PaidAmount, Balance;

    String GunUpdate, WebUpdate, ImageUrl, VideoUrl, Pcs, Issue, IssueId;
    private long id1;


    public Issuemode() {
    }

    public Issuemode(long operationTime, long entryDate, long transactionDate, long repaymentDate, String tidValue, String epcValue, String branch, String category, String product, String purity, String diamondMetal, String diamondColor, String diamondClarity, String diamondSetting, String diamondShape, String diamondSize, String diamondCertificate, String barCode, String itemCode, String box, String huidCode, String partyCode, String description, String status, String tagTransaction, String operation, String transactionType, String invoiceNumber, String customerName, String itemAddmode, String paymentMode, String paymentDescription, String gstApplied, double diamondWt, double diamondPcs, double diamondRate, double diamondAmount, double grossWt, double stoneWt, double netWt, double makingGm, double makingPer, double fixedAmount, double fixedWastage, double stoneAmount, double mrp, double hallmarkCharges, double avlQty, double matchQty, double totalGwt, double matchGwt, double totalStonewt, double matchStonewt, double totalNwt, double matchNwt, double goldRate, double totalMaking, double itemPrice, double appliedDiscount, double itempriceAfterdiscount, double gstRate, double payableAmount, double payableAmountincgst, double itemGst, double totalBilleditems, double totalBilledgwt, double totalBilledamount, double totalBillAmountExcGst, double totalBillAmountincgst, double totalGst, double totalDiscount, double paidAmount, double balance, String gunUpdate, String webUpdate) {
        OperationTime = operationTime;
        EntryDate = entryDate;
        TransactionDate = transactionDate;
        RepaymentDate = repaymentDate;
        TidValue = tidValue;
        EpcValue = epcValue;
        Branch = branch;
        Category = category;
        Product = product;
        Purity = purity;
        DiamondMetal = diamondMetal;
        DiamondColor = diamondColor;
        DiamondClarity = diamondClarity;
        DiamondSetting = diamondSetting;
        DiamondShape = diamondShape;
        DiamondSize = diamondSize;
        DiamondCertificate = diamondCertificate;
        BarCode = barCode;
        ItemCode = itemCode;
        Box = box;
        HuidCode = huidCode;
        PartyCode = partyCode;
        Description = description;
        Status = status;
        TagTransaction = tagTransaction;
        Operation = operation;
        TransactionType = transactionType;
        InvoiceNumber = invoiceNumber;
        CustomerName = customerName;
        ItemAddmode = itemAddmode;
        PaymentMode = paymentMode;
        PaymentDescription = paymentDescription;
        GstApplied = gstApplied;
        DiamondWt = diamondWt;
        DiamondPcs = diamondPcs;
        DiamondRate = diamondRate;
        DiamondAmount = diamondAmount;
        GrossWt = grossWt;
        StoneWt = stoneWt;
        NetWt = netWt;
        MakingGm = makingGm;
        MakingPer = makingPer;
        FixedAmount = fixedAmount;
        FixedWastage = fixedWastage;
        StoneAmount = stoneAmount;
        Mrp = mrp;
        HallmarkCharges = hallmarkCharges;
        AvlQty = avlQty;
        MatchQty = matchQty;
        TotalGwt = totalGwt;
        MatchGwt = matchGwt;
        TotalStonewt = totalStonewt;
        MatchStonewt = matchStonewt;
        TotalNwt = totalNwt;
        MatchNwt = matchNwt;
        GoldRate = goldRate;
        TotalMaking = totalMaking;
        ItemPrice = itemPrice;
        AppliedDiscount = appliedDiscount;
        ItempriceAfterdiscount = itempriceAfterdiscount;
        GstRate = gstRate;
        PayableAmount = payableAmount;
        PayableAmountincgst = payableAmountincgst;
        ItemGst = itemGst;
        TotalBilleditems = totalBilleditems;
        TotalBilledgwt = totalBilledgwt;
        TotalBilledamount = totalBilledamount;
        TotalBillAmountExcGst = totalBillAmountExcGst;
        TotalBillAmountincgst = totalBillAmountincgst;
        TotalGst = totalGst;
        TotalDiscount = totalDiscount;
        PaidAmount = paidAmount;
        Balance = balance;
        GunUpdate = gunUpdate;
        WebUpdate = webUpdate;
    }

    public Issuemode(String imageUrl, String videoUrl, String pcs, String issue, String issueId) {
        ImageUrl = imageUrl;
        VideoUrl = videoUrl;
        Pcs = pcs;
        Issue = issue;
        IssueId = issueId;
    }
    public Issuemode(long id1) {
        this.id1 = id1;
    }

    public Issuemode(Itemmodel oitem) {
        this.OperationTime = oitem.getOperationTime();
        this.EntryDate = oitem.getEntryDate();
        this.TransactionDate = oitem.getTransactionDate();
        this.RepaymentDate = oitem.getRepaymentDate();
        this.TidValue = oitem.getTidValue();
        this.EpcValue = oitem.getEpcValue();
        this.Branch = oitem.getBranch();
        this.Category = oitem.getCategory();
        this.Product = oitem.getProduct();
        this.Purity = oitem.getPurity();
        this.DiamondMetal = oitem.getDiamondMetal();
        this.DiamondColor = oitem.getDiamondColor();
        this.DiamondClarity = oitem.getDiamondClarity();
        this.DiamondSetting = oitem.getDiamondSetting();
        this.DiamondShape = oitem.getDiamondShape();
        this.DiamondSize = oitem.getDiamondSize();
        this.DiamondCertificate = oitem.getDiamondCertificate();
        this.BarCode = oitem.getBarCode();
        this.ItemCode = oitem.getItemCode();
        this.Box = oitem.getBox();
        this.HuidCode = oitem.getHuidCode();
        this.PartyCode = oitem.getPartyCode();
        this.Description = oitem.getDescription();
        this.Status = oitem.getStatus();
        this.TagTransaction = oitem.getTagTransaction();
        this.Operation = oitem.getOperation();
        this.TransactionType = oitem.getTransactionType();
        this.InvoiceNumber = oitem.getInvoiceNumber();
        this.CustomerName = oitem.getCustomerName();
        this.ItemAddmode = oitem.getItemAddmode();
        this.PaymentMode = oitem.getPaymentMode();
        this.PaymentDescription = oitem.getPaymentDescription();
        this.GstApplied = oitem.getGstApplied();
        this.DiamondWt = oitem.getDiamondWt();
        this.DiamondPcs = oitem.getDiamondPcs();
        this.DiamondRate = oitem.getDiamondRate();
        this.DiamondAmount = oitem.getDiamondAmount();
        this.GrossWt = oitem.getGrossWt();
        this.StoneWt = oitem.getStoneWt();
        this.NetWt = oitem.getNetWt();
        this.MakingGm = oitem.getMakingGm();
        this.MakingPer = oitem.getMakingPer();
        this.FixedAmount = oitem.getFixedAmount();
        this.FixedWastage = oitem.getFixedWastage();
        this.StoneAmount = oitem.getStoneAmount();
        this.Mrp = oitem.getMrp();
        this.HallmarkCharges = oitem.getHallmarkCharges();
        this.AvlQty = oitem.getAvlQty();
        this.MatchQty = oitem.getMatchQty();
        this.TotalGwt = oitem.getTotalGwt();
        this.MatchGwt = oitem.getMatchGwt();
        this.TotalStonewt = oitem.getTotalStonewt();
        this.MatchStonewt = oitem.getMatchStonewt();
        this.TotalNwt = oitem.getTotalNwt();
        this.MatchNwt = oitem.getMatchNwt();
        this.GoldRate = oitem.getGoldRate();
        this.TotalMaking = oitem.getTotalMaking();
        this.ItemPrice = oitem.getItemPrice();
        this.AppliedDiscount = oitem.getAppliedDiscount();
        this.ItempriceAfterdiscount = oitem.getItempriceAfterdiscount();
        this.GstRate = oitem.getGstRate();
        this.PayableAmount = oitem.getPayableAmount();
        this.PayableAmountincgst = oitem.getPayableAmountincgst();
        this.ItemGst = oitem.getItemGst();
        this.TotalBilleditems = oitem.getTotalBilleditems();
        this.TotalBilledgwt = oitem.getTotalBilledgwt();
        this.TotalBilledamount = oitem.getTotalBilledamount();
        this.TotalBillAmountExcGst = oitem.getTotalBillAmountExcGst();
        this.TotalBillAmountincgst = oitem.getTotalBillAmountincgst();
        this.TotalGst = oitem.getTotalGst();
        this.TotalDiscount = oitem.getTotalDiscount();
        this.PaidAmount = oitem.getPaidAmount();
        this.Balance = oitem.getBalance();
        this.GunUpdate = oitem.getGunUpdate();
        this.WebUpdate = oitem.getWebUpdate();
        this.ImageUrl = oitem.getImageUrl();
        this.VideoUrl = oitem.getVideoUrl();
        this.Pcs = oitem.getPcs();
        this.id1 = oitem.getId1();


    }


    protected Issuemode(Parcel in) {
        OperationTime = in.readLong();
        EntryDate = in.readLong();
        TransactionDate = in.readLong();
        RepaymentDate = in.readLong();
        TidValue = in.readString();
        EpcValue = in.readString();
        Branch = in.readString();
        Category = in.readString();
        Product = in.readString();
        Purity = in.readString();
        DiamondMetal = in.readString();
        DiamondColor = in.readString();
        DiamondClarity = in.readString();
        DiamondSetting = in.readString();
        DiamondShape = in.readString();
        DiamondSize = in.readString();
        DiamondCertificate = in.readString();
        BarCode = in.readString();
        ItemCode = in.readString();
        Box = in.readString();
        HuidCode = in.readString();
        PartyCode = in.readString();
        Description = in.readString();
        Status = in.readString();
        TagTransaction = in.readString();
        Operation = in.readString();
        TransactionType = in.readString();
        InvoiceNumber = in.readString();
        CustomerName = in.readString();
        ItemAddmode = in.readString();
        PaymentMode = in.readString();
        PaymentDescription = in.readString();
        GstApplied = in.readString();
        DiamondWt = in.readDouble();
        DiamondPcs = in.readDouble();
        DiamondRate = in.readDouble();
        DiamondAmount = in.readDouble();
        GrossWt = in.readDouble();
        StoneWt = in.readDouble();
        NetWt = in.readDouble();
        MakingGm = in.readDouble();
        MakingPer = in.readDouble();
        FixedAmount = in.readDouble();
        FixedWastage = in.readDouble();
        StoneAmount = in.readDouble();
        Mrp = in.readDouble();
        HallmarkCharges = in.readDouble();
        AvlQty = in.readDouble();
        MatchQty = in.readDouble();
        TotalGwt = in.readDouble();
        MatchGwt = in.readDouble();
        TotalStonewt = in.readDouble();
        MatchStonewt = in.readDouble();
        TotalNwt = in.readDouble();
        MatchNwt = in.readDouble();
        GoldRate = in.readDouble();
        TotalMaking = in.readDouble();
        ItemPrice = in.readDouble();
        AppliedDiscount = in.readDouble();
        ItempriceAfterdiscount = in.readDouble();
        GstRate = in.readDouble();
        PayableAmount = in.readDouble();
        PayableAmountincgst = in.readDouble();
        ItemGst = in.readDouble();
        TotalBilleditems = in.readDouble();
        TotalBilledgwt = in.readDouble();
        TotalBilledamount = in.readDouble();
        TotalBillAmountExcGst = in.readDouble();
        TotalBillAmountincgst = in.readDouble();
        TotalGst = in.readDouble();
        TotalDiscount = in.readDouble();
        PaidAmount = in.readDouble();
        Balance = in.readDouble();
        GunUpdate = in.readString();
        WebUpdate = in.readString();
        ImageUrl = in.readString();
        VideoUrl = in.readString();
        Pcs = in.readString();
        id1 = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(OperationTime);
        dest.writeLong(EntryDate);
        dest.writeLong(TransactionDate);
        dest.writeLong(RepaymentDate);
        dest.writeString(TidValue);
        dest.writeString(EpcValue);
        dest.writeString(Branch);
        dest.writeString(Category);
        dest.writeString(Product);
        dest.writeString(Purity);
        dest.writeString(DiamondMetal);
        dest.writeString(DiamondColor);
        dest.writeString(DiamondClarity);
        dest.writeString(DiamondSetting);
        dest.writeString(DiamondShape);
        dest.writeString(DiamondSize);
        dest.writeString(DiamondCertificate);
        dest.writeString(BarCode);
        dest.writeString(ItemCode);
        dest.writeString(Box);
        dest.writeString(HuidCode);
        dest.writeString(PartyCode);
        dest.writeString(Description);
        dest.writeString(Status);
        dest.writeString(TagTransaction);
        dest.writeString(Operation);
        dest.writeString(TransactionType);
        dest.writeString(InvoiceNumber);
        dest.writeString(CustomerName);
        dest.writeString(ItemAddmode);
        dest.writeString(PaymentMode);
        dest.writeString(PaymentDescription);
        dest.writeString(GstApplied);
        dest.writeDouble(DiamondWt);
        dest.writeDouble(DiamondPcs);
        dest.writeDouble(DiamondRate);
        dest.writeDouble(DiamondAmount);
        dest.writeDouble(GrossWt);
        dest.writeDouble(StoneWt);
        dest.writeDouble(NetWt);
        dest.writeDouble(MakingGm);
        dest.writeDouble(MakingPer);
        dest.writeDouble(FixedAmount);
        dest.writeDouble(FixedWastage);
        dest.writeDouble(StoneAmount);
        dest.writeDouble(Mrp);
        dest.writeDouble(HallmarkCharges);
        dest.writeDouble(AvlQty);
        dest.writeDouble(MatchQty);
        dest.writeDouble(TotalGwt);
        dest.writeDouble(MatchGwt);
        dest.writeDouble(TotalStonewt);
        dest.writeDouble(MatchStonewt);
        dest.writeDouble(TotalNwt);
        dest.writeDouble(MatchNwt);
        dest.writeDouble(GoldRate);
        dest.writeDouble(TotalMaking);
        dest.writeDouble(ItemPrice);
        dest.writeDouble(AppliedDiscount);
        dest.writeDouble(ItempriceAfterdiscount);
        dest.writeDouble(GstRate);
        dest.writeDouble(PayableAmount);
        dest.writeDouble(PayableAmountincgst);
        dest.writeDouble(ItemGst);
        dest.writeDouble(TotalBilleditems);
        dest.writeDouble(TotalBilledgwt);
        dest.writeDouble(TotalBilledamount);
        dest.writeDouble(TotalBillAmountExcGst);
        dest.writeDouble(TotalBillAmountincgst);
        dest.writeDouble(TotalGst);
        dest.writeDouble(TotalDiscount);
        dest.writeDouble(PaidAmount);
        dest.writeDouble(Balance);
        dest.writeString(GunUpdate);
        dest.writeString(WebUpdate);
        dest.writeString(ImageUrl);
        dest.writeString(VideoUrl);
        dest.writeString(Pcs);
        dest.writeLong(id1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Itemmodel> CREATOR = new Creator<Itemmodel>() {
        @Override
        public Itemmodel createFromParcel(Parcel in) {
            return new Itemmodel(in);
        }

        @Override
        public Itemmodel[] newArray(int size) {
            return new Itemmodel[size];
        }
    };

    public long getId() {
        return id1;
    }

    public void setId(long id1) {
        this.id1 = id1;
    }

    public String getIssueId() {
        return IssueId;
    }

    public void setIssueId(String issueId) {
        IssueId = issueId;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getPcs() {
        return Pcs;
    }

    public void setPcs(String pcs) {
        Pcs = pcs;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getGunUpdate() {
        return GunUpdate;
    }

    public void setGunUpdate(String gunUpdate) {
        GunUpdate = gunUpdate;
    }

    public String getWebUpdate() {
        return WebUpdate;
    }

    public void setWebUpdate(String webUpdate) {
        WebUpdate = webUpdate;
    }

    public long getOperationTime() {
        return OperationTime;
    }

    public void setOperationTime(long operationTime) {
        OperationTime = operationTime;
    }

    public long getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(long entryDate) {
        EntryDate = entryDate;
    }

    public long getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        TransactionDate = transactionDate;
    }

    public long getRepaymentDate() {
        return RepaymentDate;
    }

    public void setRepaymentDate(long repaymentDate) {
        RepaymentDate = repaymentDate;
    }

    public String getTidValue() {
        return TidValue;
    }

    public void setTidValue(String tidValue) {
        TidValue = tidValue;
    }

    public String getEpcValue() {
        return EpcValue;
    }

    public void setEpcValue(String epcValue) {
        EpcValue = epcValue;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getPurity() {
        return Purity;
    }

    public void setPurity(String purity) {
        Purity = purity;
    }

    public String getDiamondMetal() {
        return DiamondMetal;
    }

    public void setDiamondMetal(String diamondMetal) {
        DiamondMetal = diamondMetal;
    }

    public String getDiamondColor() {
        return DiamondColor;
    }

    public void setDiamondColor(String diamondColor) {
        DiamondColor = diamondColor;
    }

    public String getDiamondClarity() {
        return DiamondClarity;
    }

    public void setDiamondClarity(String diamondClarity) {
        DiamondClarity = diamondClarity;
    }

    public String getDiamondSetting() {
        return DiamondSetting;
    }

    public void setDiamondSetting(String diamondSetting) {
        DiamondSetting = diamondSetting;
    }

    public String getDiamondShape() {
        return DiamondShape;
    }

    public void setDiamondShape(String diamondShape) {
        DiamondShape = diamondShape;
    }

    public String getDiamondSize() {
        return DiamondSize;
    }

    public void setDiamondSize(String diamondSize) {
        DiamondSize = diamondSize;
    }

    public String getDiamondCertificate() {
        return DiamondCertificate;
    }

    public void setDiamondCertificate(String diamondCertificate) {
        DiamondCertificate = diamondCertificate;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getBox() {
        return Box;
    }

    public void setBox(String box) {
        Box = box;
    }

    public String getHuidCode() {
        return HuidCode;
    }

    public void setHuidCode(String huidCode) {
        HuidCode = huidCode;
    }

    public String getPartyCode() {
        return PartyCode;
    }

    public void setPartyCode(String partyCode) {
        PartyCode = partyCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTagTransaction() {
        return TagTransaction;
    }

    public void setTagTransaction(String tagTransaction) {
        TagTransaction = tagTransaction;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getItemAddmode() {
        return ItemAddmode;
    }

    public void setItemAddmode(String itemAddmode) {
        ItemAddmode = itemAddmode;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getPaymentDescription() {
        return PaymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        PaymentDescription = paymentDescription;
    }

    public String getGstApplied() {
        return GstApplied;
    }

    public void setGstApplied(String gstApplied) {
        GstApplied = gstApplied;
    }

    public double getDiamondWt() {
        return DiamondWt;
    }

    public void setDiamondWt(double diamondWt) {
        DiamondWt = diamondWt;
    }

    public double getDiamondPcs() {
        return DiamondPcs;
    }

    public void setDiamondPcs(double diamondPcs) {
        DiamondPcs = diamondPcs;
    }

    public double getDiamondRate() {
        return DiamondRate;
    }

    public void setDiamondRate(double diamondRate) {
        DiamondRate = diamondRate;
    }

    public double getDiamondAmount() {
        return DiamondAmount;
    }

    public void setDiamondAmount(double diamondAmount) {
        DiamondAmount = diamondAmount;
    }

    public double getGrossWt() {
        return GrossWt;
    }

    public void setGrossWt(double grossWt) {
        GrossWt = grossWt;
    }

    public double getStoneWt() {
        return StoneWt;
    }

    public void setStoneWt(double stoneWt) {
        StoneWt = stoneWt;
    }

    public double getNetWt() {
        return NetWt;
    }

    public void setNetWt(double netWt) {
        NetWt = netWt;
    }

    public double getMakingGm() {
        return MakingGm;
    }

    public void setMakingGm(double makingGm) {
        MakingGm = makingGm;
    }

    public double getMakingPer() {
        return MakingPer;
    }

    public void setMakingPer(double makingPer) {
        MakingPer = makingPer;
    }

    public double getFixedAmount() {
        return FixedAmount;
    }

    public void setFixedAmount(double fixedAmount) {
        FixedAmount = fixedAmount;
    }

    public double getFixedWastage() {
        return FixedWastage;
    }

    public void setFixedWastage(double fixedWastage) {
        FixedWastage = fixedWastage;
    }

    public double getStoneAmount() {
        return StoneAmount;
    }

    public void setStoneAmount(double stoneAmount) {
        StoneAmount = stoneAmount;
    }

    public double getMrp() {
        return Mrp;
    }

    public void setMrp(double mrp) {
        Mrp = mrp;
    }

    public double getHallmarkCharges() {
        return HallmarkCharges;
    }

    public void setHallmarkCharges(double hallmarkCharges) {
        HallmarkCharges = hallmarkCharges;
    }

    public double getAvlQty() {
        return AvlQty;
    }

    public void setAvlQty(double avlQty) {
        AvlQty = avlQty;
    }

    public double getMatchQty() {
        return MatchQty;
    }

    public void setMatchQty(double matchQty) {
        MatchQty = matchQty;
    }

    public double getTotalGwt() {
        return TotalGwt;
    }

    public void setTotalGwt(double totalGwt) {
        TotalGwt = totalGwt;
    }

    public double getMatchGwt() {
        return MatchGwt;
    }

    public void setMatchGwt(double matchGwt) {
        MatchGwt = matchGwt;
    }

    public double getTotalStonewt() {
        return TotalStonewt;
    }

    public void setTotalStonewt(double totalStonewt) {
        TotalStonewt = totalStonewt;
    }

    public double getMatchStonewt() {
        return MatchStonewt;
    }

    public void setMatchStonewt(double matchStonewt) {
        MatchStonewt = matchStonewt;
    }

    public double getTotalNwt() {
        return TotalNwt;
    }

    public void setTotalNwt(double totalNwt) {
        TotalNwt = totalNwt;
    }

    public double getMatchNwt() {
        return MatchNwt;
    }

    public void setMatchNwt(double matchNwt) {
        MatchNwt = matchNwt;
    }

    public double getGoldRate() {
        return GoldRate;
    }

    public void setGoldRate(double goldRate) {
        GoldRate = goldRate;
    }

    public double getTotalMaking() {
        return TotalMaking;
    }

    public void setTotalMaking(double totalMaking) {
        TotalMaking = totalMaking;
    }

    public double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(double itemPrice) {
        ItemPrice = itemPrice;
    }

    public double getAppliedDiscount() {
        return AppliedDiscount;
    }

    public void setAppliedDiscount(double appliedDiscount) {
        AppliedDiscount = appliedDiscount;
    }

    public double getItempriceAfterdiscount() {
        return ItempriceAfterdiscount;
    }

    public void setItempriceAfterdiscount(double itempriceAfterdiscount) {
        ItempriceAfterdiscount = itempriceAfterdiscount;
    }

    public double getGstRate() {
        return GstRate;
    }

    public void setGstRate(double gstRate) {
        GstRate = gstRate;
    }

    public double getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        PayableAmount = payableAmount;
    }

    public double getPayableAmountincgst() {
        return PayableAmountincgst;
    }

    public void setPayableAmountincgst(double payableAmountincgst) {
        PayableAmountincgst = payableAmountincgst;
    }

    public double getItemGst() {
        return ItemGst;
    }

    public void setItemGst(double itemGst) {
        ItemGst = itemGst;
    }

    public double getTotalBilleditems() {
        return TotalBilleditems;
    }

    public void setTotalBilleditems(double totalBilleditems) {
        TotalBilleditems = totalBilleditems;
    }

    public double getTotalBilledgwt() {
        return TotalBilledgwt;
    }

    public void setTotalBilledgwt(double totalBilledgwt) {
        TotalBilledgwt = totalBilledgwt;
    }

    public double getTotalBilledamount() {
        return TotalBilledamount;
    }

    public void setTotalBilledamount(double totalBilledamount) {
        TotalBilledamount = totalBilledamount;
    }

    public double getTotalBillAmountExcGst() {
        return TotalBillAmountExcGst;
    }

    public void setTotalBillAmountExcGst(double totalBillAmountExcGst) {
        TotalBillAmountExcGst = totalBillAmountExcGst;
    }

    public double getTotalBillAmountincgst() {
        return TotalBillAmountincgst;
    }

    public void setTotalBillAmountincgst(double totalBillAmountincgst) {
        TotalBillAmountincgst = totalBillAmountincgst;
    }

    public double getTotalGst() {
        return TotalGst;
    }

    public void setTotalGst(double totalGst) {
        TotalGst = totalGst;
    }

    public double getTotalDiscount() {
        return TotalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        TotalDiscount = totalDiscount;
    }

    public double getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        PaidAmount = paidAmount;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }


    @Override
    public String toString() {
        return "Itemmodel{" +
                "OperationTime=" + OperationTime +
                ", EntryDate=" + EntryDate +
                ", TransactionDate=" + TransactionDate +
                ", RepaymentDate=" + RepaymentDate +
                ", TidValue='" + TidValue + '\'' +
                ", EpcValue='" + EpcValue + '\'' +
                ", Branch='" + Branch + '\'' +
                ", Category='" + Category + '\'' +
                ", Product='" + Product + '\'' +
                ", Purity='" + Purity + '\'' +
                ", DiamondMetal='" + DiamondMetal + '\'' +
                ", DiamondColor='" + DiamondColor + '\'' +
                ", DiamondClarity='" + DiamondClarity + '\'' +
                ", DiamondSetting='" + DiamondSetting + '\'' +
                ", DiamondShape='" + DiamondShape + '\'' +
                ", DiamondSize='" + DiamondSize + '\'' +
                ", DiamondCertificate='" + DiamondCertificate + '\'' +
                ", BarCode='" + BarCode + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", Box='" + Box + '\'' +
                ", HuidCode='" + HuidCode + '\'' +
                ", PartyCode='" + PartyCode + '\'' +
                ", Description='" + Description + '\'' +
                ", Status='" + Status + '\'' +
                ", TagTransaction='" + TagTransaction + '\'' +
                ", Operation='" + Operation + '\'' +
                ", TransactionType='" + TransactionType + '\'' +
                ", InvoiceNumber='" + InvoiceNumber + '\'' +
                ", CustomerName='" + CustomerName + '\'' +
                ", ItemAddmode='" + ItemAddmode + '\'' +
                ", PaymentMode='" + PaymentMode + '\'' +
                ", PaymentDescription='" + PaymentDescription + '\'' +
                ", GstApplied='" + GstApplied + '\'' +
                ", DiamondWt=" + DiamondWt +
                ", DiamondPcs=" + DiamondPcs +
                ", DiamondRate=" + DiamondRate +
                ", DiamondAmount=" + DiamondAmount +
                ", GrossWt=" + GrossWt +
                ", StoneWt=" + StoneWt +
                ", NetWt=" + NetWt +
                ", MakingGm=" + MakingGm +
                ", MakingPer=" + MakingPer +
                ", FixedAmount=" + FixedAmount +
                ", FixedWastage=" + FixedWastage +
                ", StoneAmount=" + StoneAmount +
                ", Mrp=" + Mrp +
                ", HallmarkCharges=" + HallmarkCharges +
                ", AvlQty=" + AvlQty +
                ", MatchQty=" + MatchQty +
                ", TotalGwt=" + TotalGwt +
                ", MatchGwt=" + MatchGwt +
                ", TotalStonewt=" + TotalStonewt +
                ", MatchStonewt=" + MatchStonewt +
                ", TotalNwt=" + TotalNwt +
                ", MatchNwt=" + MatchNwt +
                ", GoldRate=" + GoldRate +
                ", TotalMaking=" + TotalMaking +
                ", ItemPrice=" + ItemPrice +
                ", AppliedDiscount=" + AppliedDiscount +
                ", ItempriceAfterdiscount=" + ItempriceAfterdiscount +
                ", GstRate=" + GstRate +
                ", PayableAmount=" + PayableAmount +
                ", PayableAmountincgst=" + PayableAmountincgst +
                ", ItemGst=" + ItemGst +
                ", TotalBilleditems=" + TotalBilleditems +
                ", TotalBilledgwt=" + TotalBilledgwt +
                ", TotalBilledamount=" + TotalBilledamount +
                ", TotalBillAmountExcGst=" + TotalBillAmountExcGst +
                ", TotalBillAmountincgst=" + TotalBillAmountincgst +
                ", TotalGst=" + TotalGst +
                ", TotalDiscount=" + TotalDiscount +
                ", PaidAmount=" + PaidAmount +
                ", Balance=" + Balance +
                ", GunUpdate='" + GunUpdate + '\'' +
                ", WebUpdate='" + WebUpdate + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", VideoUrl='" + VideoUrl + '\'' +
                '}';
    }
}