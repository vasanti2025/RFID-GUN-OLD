package com.loyalstring.modelclasses;

public class Ratemodel {

    private String category;
    private String purity;
    private double rate;


    public Ratemodel() {
    }

    public Ratemodel(String category, String purity, double rate) {
        this.category = category;
        this.purity = purity;
        this.rate = rate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
