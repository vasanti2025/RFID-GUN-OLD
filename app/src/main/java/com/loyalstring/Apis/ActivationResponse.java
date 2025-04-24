package com.loyalstring.Apis;

public class ActivationResponse {
	private String DeviceType;
	private String DeviceCode;
	private String DeviceActivationDate;
	private String DeviceDeactivationDate;
	private String DeviceStatus;
	private String DeviceSerialNo;
	private String DeviceBuildNo;
	private String DeviceModel;
	private String MobileNo;
	private String Id;
	private String CreatedBy;
	private String ModifiedBy;
	private String CreatedOn;
	private String LastUpdated;
	private boolean StatusType;

	public ActivationResponse() {
	}

	public String getDeviceType() {
		return DeviceType;
	}

	public void setDeviceType(String deviceType) {
		DeviceType = deviceType;
	}

	public String getDeviceCode() {
		return DeviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		DeviceCode = deviceCode;
	}

	public String getDeviceActivationDate() {
		return DeviceActivationDate;
	}

	public void setDeviceActivationDate(String deviceActivationDate) {
		DeviceActivationDate = deviceActivationDate;
	}

	public String getDeviceDeactivationDate() {
		return DeviceDeactivationDate;
	}

	public void setDeviceDeactivationDate(String deviceDeactivationDate) {
		DeviceDeactivationDate = deviceDeactivationDate;
	}

	public String getDeviceStatus() {
		return DeviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		DeviceStatus = deviceStatus;
	}

	public String getDeviceSerialNo() {
		return DeviceSerialNo;
	}

	public void setDeviceSerialNo(String deviceSerialNo) {
		DeviceSerialNo = deviceSerialNo;
	}

	public String getDeviceBuildNo() {
		return DeviceBuildNo;
	}

	public void setDeviceBuildNo(String deviceBuildNo) {
		DeviceBuildNo = deviceBuildNo;
	}

	public String getDeviceModel() {
		return DeviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		DeviceModel = deviceModel;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	public String getCreatedOn() {
		return CreatedOn;
	}

	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}

	public String getLastUpdated() {
		return LastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		LastUpdated = lastUpdated;
	}

	public boolean isStatusType() {
		return StatusType;
	}

	public void setStatusType(boolean statusType) {
		StatusType = statusType;
	}

	@Override
	public String toString() {
		return "ActivationResponse{" +
				"DeviceType='" + DeviceType + '\'' +
				", DeviceCode='" + DeviceCode + '\'' +
				", DeviceActivationDate='" + DeviceActivationDate + '\'' +
				", DeviceDeactivationDate='" + DeviceDeactivationDate + '\'' +
				", DeviceStatus='" + DeviceStatus + '\'' +
				", DeviceSerialNo='" + DeviceSerialNo + '\'' +
				", DeviceBuildNo='" + DeviceBuildNo + '\'' +
				", DeviceModel='" + DeviceModel + '\'' +
				", MobileNo='" + MobileNo + '\'' +
				", Id='" + Id + '\'' +
				", CreatedBy='" + CreatedBy + '\'' +
				", ModifiedBy='" + ModifiedBy + '\'' +
				", CreatedOn='" + CreatedOn + '\'' +
				", LastUpdated='" + LastUpdated + '\'' +
				", StatusType=" + StatusType +
				'}';
	}
}
