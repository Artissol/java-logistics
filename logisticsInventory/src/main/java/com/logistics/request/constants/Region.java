package com.logistics.request.constants;

//import java.util.Date;

public class Region {

	private int regionid;
	private String regionName;
	private String locationname;
	private double min_lang;
	private double max_lang;
	private double min_lat;
	private double max_lat;
	private String createdby;
	private String createdon;
	private String message;
    private int code;
	public int getRegionid() {
		return regionid;
	}

	public void setRegionid(int regionid) {
		this.regionid = regionid;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public double getMin_lang() {
		return min_lang;
	}

	public void setMin_lang(double min_lang) {
		this.min_lang = min_lang;
	}

	public double getMax_lang() {
		return max_lang;
	}

	public void setMax_lang(double max_lang) {
		this.max_lang = max_lang;
	}

	public double getMin_lat() {
		return min_lat;
	}

	public void setMin_lat(double min_lat) {
		this.min_lat = min_lat;
	}

	public double getMax_lat() {
		return max_lat;
	}

	public void setMax_lat(double max_lat) {
		this.max_lat = max_lat;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getCreatedon() {
		return createdon;
	}

	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}

	@Override
	public String toString() {
		return "Region [regionid=" + regionid + ", regionName=" + regionName + ", locationid=" + locationname
				+ ", min_lang=" + min_lang + ", max_lang=" + max_lang + ", min_lat=" + min_lat + ", max_lat=" + max_lat
				+ ", createdby=" + createdby + "]";
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