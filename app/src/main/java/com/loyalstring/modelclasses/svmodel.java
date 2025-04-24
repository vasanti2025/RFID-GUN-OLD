package com.loyalstring.modelclasses;

public class svmodel {

    String date;
    double grosswt;
    int totalqty;
    double totalwt;
    int matchedqty;
    double matchedwt;
    int unmatchedqty;
    double unmatchedwt;
    int notscan;
    double notscanwt;
    String status;

    public svmodel() {
    }

    public svmodel(String date, double grosswt, int totalqty, double totalwt, int matchedqty, double matchedwt, int unmatchedqty, double unmatchedwt, int notscan, double notscanwt, String status) {
        this.date = date;
        this.grosswt = grosswt;
        this.totalqty = totalqty;
        this.totalwt = totalwt;
        this.matchedqty = matchedqty;
        this.matchedwt = matchedwt;
        this.unmatchedqty = unmatchedqty;
        this.unmatchedwt = unmatchedwt;
        this.notscan = notscan;
        this.notscanwt = notscanwt;
        this.status = status;
    }

    public double getGrosswt() {
        return grosswt;
    }

    public void setGrosswt(double grosswt) {
        this.grosswt = grosswt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(int totalqty) {
        this.totalqty = totalqty;
    }

    public double getTotalwt() {
        return totalwt;
    }

    public void setTotalwt(double totalwt) {
        this.totalwt = totalwt;
    }

    public int getMatchedqty() {
        return matchedqty;
    }

    public void setMatchedqty(int matchedqty) {
        this.matchedqty = matchedqty;
    }

    public double getMatchedwt() {
        return matchedwt;
    }

    public void setMatchedwt(double matchedwt) {
        this.matchedwt = matchedwt;
    }

    public int getUnmatchedqty() {
        return unmatchedqty;
    }

    public void setUnmatchedqty(int unmatchedqty) {
        this.unmatchedqty = unmatchedqty;
    }

    public double getUnmatchedwt() {
        return unmatchedwt;
    }

    public void setUnmatchedwt(double unmatchedwt) {
        this.unmatchedwt = unmatchedwt;
    }

    public int getNotscan() {
        return notscan;
    }

    public void setNotscan(int notscan) {
        this.notscan = notscan;
    }

    public double getNotscanwt() {
        return notscanwt;
    }

    public void setNotscanwt(double notscanwt) {
        this.notscanwt = notscanwt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
