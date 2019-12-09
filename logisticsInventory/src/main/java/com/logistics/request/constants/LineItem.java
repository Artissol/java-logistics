package com.logistics.request.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LineItem {

	@JsonProperty("item_id")
	private long itemId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("rate")
	private Integer rate;
	@JsonProperty("quantity")
	private Integer quantity;
	@JsonProperty("unit")
	private String unit;
	@JsonProperty("warehouse_id")
	private long warehouseId;
	private Integer quantity_transfer;
	 @JsonProperty("date")
	 private String date;
	    @JsonProperty("from_warehouse_id")
	    private String fromWarehouseId;
	    @JsonProperty("to_warehouse_id")
	    private String toWarehouseId;

	@JsonProperty("item_id")
	public long getItemId() {
		return itemId;
	}

	@JsonProperty("item_id")
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("rate")
	public Integer getRate() {
		return rate;
	}

	@JsonProperty("rate")
	public void setRate(Integer rate) {
		this.rate = rate;
	}

	@JsonProperty("quantity")
	public Integer getQuantity() {
		return quantity;
	}

	@JsonProperty("quantity")
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@JsonProperty("unit")
	public String getUnit() {
		return unit;
	}

	@JsonProperty("unit")
	public void setUnit(String unit) {
		this.unit = unit;
	}

	@JsonProperty("warehouse_id")
	public long getWarehouseId() {
		return warehouseId;
	}

	@JsonProperty("warehouse_id")
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}

	@Override
	public String toString() {
		return "LineItem [itemId=" + itemId + ", name=" + name + ", rate=" + rate + ", quantity=" + quantity + ", unit="
				+ unit + ", warehouseId=" + warehouseId + "]";
	}

	public Integer getQuantity_transfer() {
		return quantity_transfer;
	}

	public void setQuantity_transfer(Integer quantity_transfer) {
		this.quantity_transfer = quantity_transfer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFromWarehouseId() {
		return fromWarehouseId;
	}

	public void setFromWarehouseId(String fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}

	public String getToWarehouseId() {
		return toWarehouseId;
	}

	public void setToWarehouseId(String toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}

//	public Integer getPurchaseRate() {
//		// TODO Auto-generated method stub
//		return purc;
//	}

//	public Object getPurchaseRate() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}