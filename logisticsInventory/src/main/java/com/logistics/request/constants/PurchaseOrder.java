package com.logistics.request.constants;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseOrder {

	private String purchaseorder_number;
	private Date date;
	private Date delivery_date;
	private String ship_via;
	private String vendor_id;
	@JsonProperty("custom_fields")
	private List<CustomFields> customFields = null;
	private String attention;
	private String delivery_org_address_id;
	@JsonProperty("line_items")
	private List<LineItem> lineItems = null;

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public String getPurchaseorder_number() {
		return purchaseorder_number;
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getDelivery_org_address_id() {
		return delivery_org_address_id;
	}

	public void setDelivery_org_address_id(String delivery_org_address_id) {
		this.delivery_org_address_id = delivery_org_address_id;
	}

	public void setPurchaseorder_number(String purchaseorder_number) {
		this.purchaseorder_number = purchaseorder_number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public List<CustomFields> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<CustomFields> customFields) {
		this.customFields = customFields;
	}

	public String getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	private String warehouse_id;

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getShip_via() {
		return ship_via;
	}

	public void setShip_via(String ship_via) {
		this.ship_via = ship_via;
	}

}
