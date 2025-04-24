package com.loyalstring.Billmodels;

import com.google.gson.annotations.SerializedName;

public class CustomersmodelItem{
	@SerializedName("FirstName")
	public String firstName;
	@SerializedName("LastName")
	public String lastName;
	@SerializedName("PerAddStreet")
	public String perAddStreet;
	@SerializedName("CurrAddStreet")
	public String currAddStreet;
	@SerializedName("Mobile")
	public String mobile;
	@SerializedName("Email")
	public String email;
	@SerializedName("Password")
	public String password;
	@SerializedName("CustomerLoginId")
	public String customerLoginId;
	@SerializedName("DateOfBirth")
	public String dateOfBirth;
	@SerializedName("MiddleName")
	public String middleName;
	@SerializedName("PerAddPincode")
	public String perAddPincode;
	@SerializedName("Gender")
	public String gender;
	@SerializedName("OnlineStatus")
	public String onlineStatus;
	@SerializedName("CurrAddTown")
	public String currAddTown;
	@SerializedName("CurrAddPincode")
	public String currAddPincode;
	@SerializedName("CurrAddState")
	public String currAddState;
	@SerializedName("PerAddTown")
	public String perAddTown;
	@SerializedName("PerAddState")
	public String perAddState;
	@SerializedName("GstNo")
	public String gstNo;
	@SerializedName("PanNo")
	public String panNo;
	@SerializedName("AadharNo")
	public String aadharNo;
	@SerializedName("BalanceAmount")
	public String balanceAmount;
	@SerializedName("AdvanceAmount")
	public String advanceAmount;
	@SerializedName("Discount")
	public String discount;
	@SerializedName("CreditPeriod")
	public String creditPeriod;
	@SerializedName("FineGold")
	public String fineGold;
	@SerializedName("FineSilver")
	public String fineSilver;
	@SerializedName("ClientCode")
	public String clientCode;
	@SerializedName("VendorId")
	public int vendorId;
	@SerializedName("AddToVendor")
	public boolean addToVendor;
	@SerializedName("CustomerSlabId")
	public int customerSlabId;
	@SerializedName("CreditPeriodId")
	public int creditPeriodId;
	@SerializedName("RateOfInterestId")
	public int rateOfInterestId;
	@SerializedName("Id")
	public int id;
	@SerializedName("CreatedOn")
	public String createdOn;
	@SerializedName("LastUpdated")
	public String lastUpdated;
	@SerializedName("StatusType")
	public boolean statusType;

	public void setCurrAddPincode(String currAddPincode){
		this.currAddPincode = currAddPincode;
	}

	public String getCurrAddPincode(){
		return currAddPincode;
	}

	public void setAdvanceAmount(String advanceAmount){
		this.advanceAmount = advanceAmount;
	}

	public String getAdvanceAmount(){
		return advanceAmount;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setDiscount(String discount){
		this.discount = discount;
	}

	public String getDiscount(){
		return discount;
	}

	public void setVendorId(int vendorId){
		this.vendorId = vendorId;
	}

	public int getVendorId(){
		return vendorId;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setPanNo(String panNo){
		this.panNo = panNo;
	}

	public String getPanNo(){
		return panNo;
	}

	public void setCurrAddTown(String currAddTown){
		this.currAddTown = currAddTown;
	}

	public String getCurrAddTown(){
		return currAddTown;
	}

	public void setCurrAddStreet(String currAddStreet){
		this.currAddStreet = currAddStreet;
	}

	public String getCurrAddStreet(){
		return currAddStreet;
	}

	public void setGstNo(String gstNo){
		this.gstNo = gstNo;
	}

	public String getGstNo(){
		return gstNo;
	}

	public void setCurrAddState(String currAddState){
		this.currAddState = currAddState;
	}

	public String getCurrAddState(){
		return currAddState;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setLastUpdated(String lastUpdated){
		this.lastUpdated = lastUpdated;
	}

	public String getLastUpdated(){
		return lastUpdated;
	}

	public void setCreditPeriodId(int creditPeriodId){
		this.creditPeriodId = creditPeriodId;
	}

	public int getCreditPeriodId(){
		return creditPeriodId;
	}

	public void setFineSilver(String fineSilver){
		this.fineSilver = fineSilver;
	}

	public String getFineSilver(){
		return fineSilver;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setDateOfBirth(String dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfBirth(){
		return dateOfBirth;
	}

	public void setAadharNo(String aadharNo){
		this.aadharNo = aadharNo;
	}

	public String getAadharNo(){
		return aadharNo;
	}

	public void setBalanceAmount(String balanceAmount){
		this.balanceAmount = balanceAmount;
	}

	public String getBalanceAmount(){
		return balanceAmount;
	}

	public void setCustomerSlabId(int customerSlabId){
		this.customerSlabId = customerSlabId;
	}

	public int getCustomerSlabId(){
		return customerSlabId;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setOnlineStatus(String onlineStatus){
		this.onlineStatus = onlineStatus;
	}

	public String getOnlineStatus(){
		return onlineStatus;
	}

	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}

	public String getMiddleName(){
		return middleName;
	}

	public void setPerAddPincode(String perAddPincode){
		this.perAddPincode = perAddPincode;
	}

	public String getPerAddPincode(){
		return perAddPincode;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setPerAddTown(String perAddTown){
		this.perAddTown = perAddTown;
	}

	public String getPerAddTown(){
		return perAddTown;
	}

	public void setAddToVendor(boolean addToVendor){
		this.addToVendor = addToVendor;
	}

	public boolean isAddToVendor(){
		return addToVendor;
	}

	public void setPerAddStreet(String perAddStreet){
		this.perAddStreet = perAddStreet;
	}

	public String getPerAddStreet(){
		return perAddStreet;
	}

	public void setStatusType(boolean statusType){
		this.statusType = statusType;
	}

	public boolean isStatusType(){
		return statusType;
	}

	public void setClientCode(String clientCode){
		this.clientCode = clientCode;
	}

	public String getClientCode(){
		return clientCode;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCreditPeriod(String creditPeriod){
		this.creditPeriod = creditPeriod;
	}

	public String getCreditPeriod(){
		return creditPeriod;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFineGold(String fineGold){
		this.fineGold = fineGold;
	}

	public String getFineGold(){
		return fineGold;
	}

	public void setRateOfInterestId(int rateOfInterestId){
		this.rateOfInterestId = rateOfInterestId;
	}

	public int getRateOfInterestId(){
		return rateOfInterestId;
	}

	public void setCustomerLoginId(String customerLoginId){
		this.customerLoginId = customerLoginId;
	}

	public String getCustomerLoginId(){
		return customerLoginId;
	}

	public void setPerAddState(String perAddState){
		this.perAddState = perAddState;
	}

	public String getPerAddState(){
		return perAddState;
	}
}
