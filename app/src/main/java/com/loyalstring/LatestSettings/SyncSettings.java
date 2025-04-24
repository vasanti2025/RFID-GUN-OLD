package com.loyalstring.LatestSettings;

public class SyncSettings {
    private boolean product;
    private boolean inventory;
    private boolean bills;
    private boolean reports;

    // Getters and setters
    public boolean isProduct() {
        return product;
    }

    public void setProduct(boolean product) {
        this.product = product;
    }

    public boolean isInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }

    public boolean isBills() {
        return bills;
    }

    public void setBills(boolean bills) {
        this.bills = bills;
    }

    public boolean isReports() {
        return reports;
    }

    public void setReports(boolean reports) {
        this.reports = reports;
    }
}
