package com.loyalstring.modelclasses;

public class Inventorytopmodel {

    String Category, Product, Box;
    double TotalQty, MatchQty, TotalGwt, MatchGwt, TotalStonewt, MatchStonewt, TotalNwt, matchNwt, Status;


    public Inventorytopmodel() {
    }

    public Inventorytopmodel(String category, String product, String box, double totalQty, double matchQty, double totalGwt, double matchGwt, double totalStonewt, double matchStonewt, double totalNwt, double matchNwt, double status) {
        Category = category;
        Product = product;
        Box = box;
        TotalQty = totalQty;
        MatchQty = matchQty;
        TotalGwt = totalGwt;
        MatchGwt = matchGwt;
        TotalStonewt = totalStonewt;
        MatchStonewt = matchStonewt;
        TotalNwt = totalNwt;
        this.matchNwt = matchNwt;
        Status = status;
    }


    public String getBox() {
        return Box;
    }

    public void setBox(String box) {
        Box = box;
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

    public double getTotalQty() {
        return TotalQty;
    }

    public void setTotalQty(double totalQty) {
        TotalQty = totalQty;
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

    public double getStatus() {
        return Status;
    }

    public void setStatus(double status) {
        Status = status;
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
        return matchNwt;
    }

    public void setMatchNwt(double matchNwt) {
        this.matchNwt = matchNwt;
    }
}
