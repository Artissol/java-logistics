package com.logistics.request.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Warehouse {
	@JsonProperty("warehouse_name")
	private String warehouseName;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zip;
	private String phone;
	private String email;
	private String warehouse_type;
	private String warehouseid;
	private int type;
	private int regionId;
	private double warehouse_lat;
	private double warehouse_lang;
	private int status;
	private String message;
    private int code;

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWarehouse_type() {
		return warehouse_type;
	}

	public void setWarehouse_type(String warehouse_type) {
		this.warehouse_type = warehouse_type;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public double getWarehouse_lat() {
		return warehouse_lat;
	}

	public void setWarehouse_lat(double warehouse_lat) {
		this.warehouse_lat = warehouse_lat;
	}

	public double getWarehouse_lang() {
		return warehouse_lang;
	}

	public void setWarehouse_lang(double warehouse_lang) {
		this.warehouse_lang = warehouse_lang;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Warehouse [warehouseName=" + warehouseName + ", address=" + address + ", city=" + city + ", state="
				+ state + ", country=" + country + ", zip=" + zip + ", phone=" + phone + ", email=" + email
				+ ", warehouse_type=" + warehouse_type + ", warehouseid=" + warehouseid + ", regionId=" + regionId
				+ ", warehouse_lat=" + warehouse_lat + ", warehouse_lang=" + warehouse_lang + ", status=" + status
				+ ", type=" + type + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
}
