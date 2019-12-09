package com.logistics.request.constants;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalesOrder {

	private String customer_id;
	private String salesorder_number;
	private String date;
	private String shipment_date;
	private String attention;
	@JsonProperty("custom_fields")
	private List<CustomFields> customFields = null;
	@JsonProperty("line_items")
	private List<LineItem> lineItems = null;
//	@JsonProperty("billing_address_id",re)
	private long billing_address_id;
//	@JsonProperty("shipping_address_id")
	private long shipping_address_id;	

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getSalesorder_number() {
		return salesorder_number;
	}

	public void setSalesorder_number(String salesorder_number) {
		this.salesorder_number = salesorder_number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShipment_date() {
		return shipment_date;
	}

	public void setShipment_date(String shipment_date) {
		this.shipment_date = shipment_date;
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public List<CustomFields> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(List<CustomFields> customFields) {
		this.customFields = customFields;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public long getBilling_address_id() {
		return billing_address_id;
	}

	public void setBilling_address_id(long billing_address_id) {
		this.billing_address_id = billing_address_id;
	}

	public long getShipping_address_id() {
		return shipping_address_id;
	}

	public void setShipping_address_id(long shipping_address_id) {
		this.shipping_address_id = shipping_address_id;
	}

	@Override
	public String toString() {
		return "SalesOrder [customer_id=" + customer_id + ", salesorder_number=" + salesorder_number + ", date=" + date
				+ ", shipment_date=" + shipment_date + ", attention=" + attention + ", customFields=" + customFields
				+ ", lineItems=" + lineItems + ", billing_address_id=" + billing_address_id + ", shipping_address_id="
				+ shipping_address_id + "]";
	}

}
