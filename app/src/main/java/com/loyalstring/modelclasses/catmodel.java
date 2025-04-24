package com.loyalstring.modelclasses;

public class catmodel {

    String category;
    String product;
    String purity;
    String box;

    public catmodel() {
    }

    public catmodel(String category, String product, String purity, String box) {
        this.category = category;
        this.product = product;
        this.purity = purity;
        this.box = box;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }
}
