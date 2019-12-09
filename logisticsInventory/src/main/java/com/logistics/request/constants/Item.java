package com.logistics.request.constants;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

	private String name;
	private String unit;
	private String product_type;
	private double rate;
	private String sku;
	private double purchase_rate;
	private String purchase_description;
	private String item_type;
	@JsonProperty("custom_fields")
	private List<CustomFields> customFields = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getPurchase_rate() {
		return purchase_rate;
	}

	public void setPurchase_rate(double purchase_rate) {
		this.purchase_rate = purchase_rate;
	}

	public String getPurchase_description() {
		return purchase_description;
	}

	public void setPurchase_description(String purchase_description) {
		this.purchase_description = purchase_description;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public List<CustomFields> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<CustomFields> customFields) {
		this.customFields = customFields;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", unit=" + unit + ", product_type=" + product_type + ", rate=" + rate + ", sku="
				+ sku + ", purchase_rate=" + purchase_rate + ", purchase_description=" + purchase_description
				+ ", item_type=" + item_type + "]";
	}

}