package com.logistics.request.constants;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferOrder {
	@JsonProperty("transfer_order_number")
	private String transferOrderNumber;
	private String date;
	@JsonProperty("from_warehouse_id")
	private String fromWarehouseId;
	@JsonProperty("to_warehouse_id")
	private String toWarehouseId;
	@JsonProperty("line_items")
	private List<LineItem> lineItems = null;
	@JsonProperty("is_intransit_order")
	Boolean isIntransitOrder;

	public String getTransferOrderNumber() {
		return transferOrderNumber;
	}

	public void setTransferOrderNumber(String transferOrderNumber) {
		this.transferOrderNumber = transferOrderNumber;
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

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public Boolean getIsIntransitOrder() {
		return isIntransitOrder;
	}

	public void setIsIntransitOrder(Boolean isIntransitOrder) {
		this.isIntransitOrder = isIntransitOrder;
	}

	@Override
	public String toString() {
		return "TransferOrder [transferOrderNumber=" + transferOrderNumber + ", date=" + date + ", fromWarehouseId="
				+ fromWarehouseId + ", toWarehouseId=" + toWarehouseId + ", lineItems=" + lineItems
				+ ", isIntransitOrder=" + isIntransitOrder + "]";
	}

}
