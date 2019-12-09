package com.logistics.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.Response;
import com.logistics.dao.RequestDAO;
import com.logistics.request.constants.BillingAddress;
import com.logistics.request.constants.CustomerInfo;
import com.logistics.request.constants.Item;
import com.logistics.request.constants.PurchaseOrder;
import com.logistics.request.constants.Region;
import com.logistics.request.constants.Roles;
import com.logistics.request.constants.SalesOrder;
import com.logistics.request.constants.TransferOrder;
import com.logistics.request.constants.User;
import com.logistics.request.constants.Warehouse;
import com.logistics.request.constants.WarehouseType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("logistics")
@Api(value = "logistics", tags = "Logistics Customent Login APIS")
public class UserController {

	@RequestMapping(value = "/getAllRequests", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get User profile details from Logistics", notes = "Get user profile details based on the email.",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<CustomerInfo> getAllRequests(
			@ApiParam(value = "userId", required = false) @RequestParam(name = "userId", required = true) String userId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getAllRequests");
		RequestDAO requestLayer = new RequestDAO();
		List<CustomerInfo> customerInfo = requestLayer.getAllRequests(userId);
		System.out.println("Ended getAllRequests");
		return customerInfo;
	}

	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add a Customer", notes = "Add new Customer.", response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Add Customer", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response addCustomer(@RequestParam(name = "contactId", required = true) long contactId,
			@RequestParam(name = "authcode", required = true) long authCode,
			@RequestParam(name = "vendorId", required = true) long vendorId,
			@RequestParam(name = "company", required = true) String company, @RequestBody CustomerInfo customerInfo)
			throws IOException, ClassNotFoundException {
		System.out.println("Started addCustomer");
		Response response = null;
		try {
			RequestDAO requestLayer = new RequestDAO();
			response = requestLayer.addCustomer(contactId, vendorId, company, customerInfo,authCode);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Ended addCustomer");
		return response;
	}

	@RequestMapping(value = "/getRoles", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get all user roles", notes = "Get available roles and associated ID's.",

			response = Roles.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = Roles.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<Roles> getRoles(@RequestParam(name = "authcode", required = true) long vendorId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getRoles");
		RequestDAO requestLayer = new RequestDAO();
		List<Roles> rolesInfo = requestLayer.getRoles(vendorId);
		System.out.println("Ended getRoles");
		return rolesInfo;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Reset Password", notes = "Reset/change password.",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Add Customer", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response forgotPassword(@RequestParam(name = "authcode", required = true) String vendorId,
			@RequestParam(name = "userName", required = true) String userName,
			@RequestParam(name = "password", required = true) String Password,
			@RequestParam(name = "reason", required = true) int reason) throws IOException, ClassNotFoundException {
		System.out.println("Started forgotPassword");
		Response response = null;
		try {
			RequestDAO requestLayer = new RequestDAO();
			response = requestLayer.forgotPassword(userName, Password, reason);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Ended forgotPassword");
		return response;
	}

	@RequestMapping(value = "/getSingleRegion", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get a single Region", notes = "Get a single Region.",

			response = CustomerInfo.class, responseContainer = "List")

	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<Region> getSingleRegion(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "regionId", required = true) int regionId) throws IOException, ClassNotFoundException {
		System.out.println("Started get Single resgion");
		RequestDAO requestLayer = new RequestDAO();
		List<Region> items = requestLayer.getSingleRegion(regionId, vendorId);
		System.out.println("Ended get single region");
		return items;
	}

	@RequestMapping(value = "/addZohoUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add Customer to the Zoho", notes = "Add Zoho Customers", response = User.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Add Zoho Customer", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response createUser(@RequestParam(name = "isVendor", required = true) boolean isVendor,
			@RequestParam(name = "authcode", required = true) long vendorId, @RequestBody User user) {
		System.out.println("Started add zoho user");
		Response response = null;
		try {
			RequestDAO requestLayer = new RequestDAO();
			response = requestLayer.addZohoUser(isVendor, user);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Ended add zoho user");
		return response;
	}

	@RequestMapping(value = "/addInventory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add Inventory to the Zoho", notes = "Add Inventory", response = User.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Add Inventory", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response createInventory(@RequestBody PurchaseOrder purchaseOrder,
			@RequestParam(name = "authcode", required = true) long vendorId) {
		System.out.println("Started add Inventory");
		System.out.println(purchaseOrder);
		Response response = null;
		try {
			RequestDAO requestLayer = new RequestDAO();
			response = requestLayer.addInventory(purchaseOrder);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Ended add Inventory");
		return response;
	}

	@RequestMapping(value = "/addRegion", method = RequestMethod.POST, headers = "Accept=application/json")
	@ApiOperation(value = "Add a Region", notes = "Add a Region.",

			response = CustomerInfo.class, responseContainer = "List")

	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response addRegion(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestBody Region region) throws IOException, ClassNotFoundException {
		System.out.println("Started Add Region");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.addRegion(region, vendorId);
		System.out.println("Ended Add Region");
		return items;
	}

	@RequestMapping(value = "/updateRegion", method = RequestMethod.PUT, headers = "Accept=application/json")
	@ApiOperation(value = "Update a Region", notes = "Update a Region.",

			response = CustomerInfo.class, responseContainer = "List")

	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response updateRegion(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "regionId", required = true) int regionId, @RequestBody Region region)
			throws IOException, ClassNotFoundException {
		System.out.println("Started update Region");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.updateRegion(region, regionId, vendorId);
		System.out.println("Ended update Region");
		return items;
	}

	@RequestMapping(value = "/deleteRegion", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@ApiOperation(value = "Delete a Region", notes = "Delete a Region.",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response deleteRegion(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "regionId", required = true) int regionId) throws IOException, ClassNotFoundException {
		System.out.println("Started delete Region");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.deleteRegion(regionId, vendorId);
		System.out.println("Ended delete Region");
		return items;
	}

	@RequestMapping(value = "/getAllWarehouses", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get all warehouses", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getAllRequests() throws IOException, ClassNotFoundException {
		System.out.println("Started getAllWarehouses");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer warehouse = requestLayer.getAllWarehouses();
		System.out.println("Ended getAllWarehouses");
		return warehouse;
	}

	@RequestMapping(value = "/getItems", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get Items", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getItems(@RequestParam(name = "authcode", required = true) long vendorId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getItems");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.getItems();
		System.out.println("Ended getItems");
		return items;
	}

	@RequestMapping(value = "/getAllInventories", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get Inventories", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getAllInventories(@RequestParam(name = "authcode", required = true) long vendorId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getAllInventories");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.getAllInventories();
		System.out.println("Ended getAllInventories");
		return items;
	}

	@RequestMapping(value = "/getInventory", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get Inventory", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getInventory(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "purchaseorder_id", required = true) long purchaseorder_id)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getInventory");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.getSinglePurchase(purchaseorder_id);
		System.out.println("Ended getInventory");
		return items;
	}

	@RequestMapping(value = "/getAllRegions", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get all Regions", notes = "Get all Regions.",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<Region> getAllRegions(@RequestParam(name = "authcode", required = true) long vendorId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getAllRegions");
		RequestDAO requestLayer = new RequestDAO();
		List<Region> items = requestLayer.getAllRegions(vendorId);
		System.out.println("Ended getAllRegions");
		return items;
	}

	@RequestMapping(value = "/deleteInventory", method = RequestMethod.POST, headers = "Accept=application/json")
	@ApiOperation(value = "Delete Inventory", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer deleteInventory(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "purchaseorder_id", required = true) String purchaseorder_id)
			throws IOException, ClassNotFoundException {
		System.out.println("Started deleteInventory");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.deleteInventory(purchaseorder_id);
		System.out.println("Ended deleteInventory");
		return items;
	}

	@RequestMapping(value = "/addWarehouse", method = RequestMethod.POST, headers = "Accept=application/json")
	@ApiOperation(value = "Add Warehouse", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response addWarehouse(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestBody Warehouse warehouse) throws IOException, ClassNotFoundException {
		System.out.println("Started addWarehouse");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.addWarehouse(warehouse);
		System.out.println("Ended addWarehouse");
		return items;
	}

	@RequestMapping(value = "/addWarehouseinDb", method = RequestMethod.POST, headers = "Accept=application/json")
	@ApiOperation(value = "Create a Warehouse", notes = "Create a Warehouse.",

			response = CustomerInfo.class, responseContainer = "List")

	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response addWarehouseinDb(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestBody Warehouse warehouse) throws IOException, ClassNotFoundException {
		System.out.println("Started addWarehouseinDb");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.addWarehouseinDb(warehouse, vendorId);
		System.out.println("Ended addWarehouseinDb");
		return items;
	}

	@RequestMapping(value = "/getWarehousetypes", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get Warehouse types", notes = "Get available Warehouse types.",

			response = Roles.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = Roles.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<WarehouseType> getWarehousetypes(@RequestParam(name = "authcode", required = true) long vendorId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getWarehousetypes");
		RequestDAO requestLayer = new RequestDAO();
		List<WarehouseType> warehousetypesInfo = requestLayer.getWarehousetypes(vendorId);
		System.out.println("Ended getWarehousetypes");
		return warehousetypesInfo;
	}

	@RequestMapping(value = "/getWarehousesDb", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get all Warehouses", notes = "Get all Warehouses.",

			response = Roles.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = Roles.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<Warehouse> getWarehouseDb(@RequestParam(name = "authcode", required = true) long vendorId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getWarehouseDb");
		RequestDAO requestLayer = new RequestDAO();
		List<Warehouse> warehousessInfo = requestLayer.getWarehouseDb(vendorId);
		System.out.println("Ended getWarehouseDb");
		return warehousessInfo;
	}

	@RequestMapping(value = "/deleteWarehouse", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@ApiOperation(value = "Delete Warehouse", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer deleteWarehouse(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "warehouse_id", required = true) String warehouse_id)
			throws IOException, ClassNotFoundException {
		System.out.println("Started deleteWarehouse");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.deleteWarehouse(warehouse_id);
		System.out.println("Ended deleteWarehouse");
		return items;
	}

	@RequestMapping(value = "/deleteWarehouseinDb", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@ApiOperation(value = "Delete a Warehouse", notes = "Delete a Warehouse.",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response deleteWarehouseinDb(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "warehouse_id", required = true) String warehouse_id)
			throws IOException, ClassNotFoundException {
		System.out.println("Started deleteWarehouseinDb");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.deleteWarehouseinDb(warehouse_id, vendorId);
		System.out.println("Ended deleteWarehouseinDb");
		return items;
	}

	@RequestMapping(value = "/getCustomFieldOptions", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get CustomField Options", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getCustomFieldOptions(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "entity", required = true) String entity) throws IOException, ClassNotFoundException {
		System.out.println("Started getCustomFieldOptions");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.getCustomFieldOptions(entity);
		System.out.println("Ended getCustomFieldOptions");
		return items;
	}

	@RequestMapping(value = "/getWarehousesByFilter", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Filter warehouses based on type", notes = "Filter warehouses based on type.",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<Warehouse> getWarehousesByFilter(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "warehousetype", required = true) String type)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getWarehousesByFilter");
		RequestDAO requestLayer = new RequestDAO();
		List<Warehouse> items = requestLayer.getWarehousesByFilter(type, vendorId);
		System.out.println("Ended getWarehousesByFilter");
		return items;
	}

	@RequestMapping(value = "/getAllSalesOrders", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get SalesOrders", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getAllSalesOrders(@RequestParam(name = "authcode", required = true) long vendorId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getAllSalesOrders");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.getAllSalesOrders();
		System.out.println("Ended getAllSalesOrders");
		return items;
	}

	@RequestMapping(value = "/getSalesOrder", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get SalesOrder", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getSalesOrder(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "salesorder_id", required = true) long salesorder_id)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getSalesOrder");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.getSalesOrder(salesorder_id);
		System.out.println("Ended getSalesOrder");
		return items;
	}

	@RequestMapping(value = "/deleteSalesOrder", method = RequestMethod.POST, headers = "Accept=application/json")
	@ApiOperation(value = "Delete deleteSalesOrder", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer deleteSalesOrder(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "salesorder_id", required = true) long salesorder_id)
			throws IOException, ClassNotFoundException {
		System.out.println("Started deleteSalesOrder");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.deleteSalesOrder(salesorder_id);
		System.out.println("Ended deleteSalesOrder");
		return items;
	}

	@RequestMapping(value = "/getSingleItem", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get SingleItem", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getSingleItem(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "item_id", required = true) long item_id) throws IOException, ClassNotFoundException {
		System.out.println("Started getSingleItem");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.getSingleItem(item_id);
		System.out.println("Ended getSingleItem");
		return items;
	}

	@RequestMapping(value = "/addSalesOrder", method = RequestMethod.POST, headers = "Accept=application/json")
	@ApiOperation(value = "Add Sales Order", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response addSalesOrder(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "isClone", required = true) boolean isClone,
			@RequestParam(name = "isDropAt", required = true) boolean isDropAt, @RequestBody SalesOrder salesOrder)
			throws IOException, ClassNotFoundException {
		System.out.println("Started Add salesOrder");
		System.out.println(salesOrder);
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.addSalesOrder(isClone, isDropAt, salesOrder);
		System.out.println("Ended Add salesOrder");
		return items;
	}

	@RequestMapping(value = "/updateWarehouseZoho", method = RequestMethod.PUT, headers = "Accept=application/json")
	@ApiOperation(value = "Add Warehouse", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response updateWarehouseZoho(@RequestParam(name = "authcode", required = true) String vendorId,
			@RequestParam(name = "warehouseId", required = true) long warehouseId, @RequestBody Warehouse warehouse)
			throws IOException, ClassNotFoundException {
		System.out.println("Started updateWarehouseZoho");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.updateWarehouseZoho(warehouse, warehouseId);
		System.out.println("Ended updateWarehouseZoho");
		return items;
	}

	@RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get all customers details from Logistics", notes = "Retrieves profile details of all customers.",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<CustomerInfo> getAllCustomers(@RequestParam(name = "authcode", required = true) long vendorId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getAllCustomers");
		RequestDAO requestLayer = new RequestDAO();
		List<CustomerInfo> customerInfo = requestLayer.getAllCustomers(vendorId);
		System.out.println("Ended getAllCustomers");
		return customerInfo;
	}

	@RequestMapping(value = "/addAddress", method = RequestMethod.POST, headers = "Accept=application/json")
	@ApiOperation(value = "Add address", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response addAddress(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "customer_id", required = true) long customer_id,
			@RequestBody BillingAddress billingAddress) throws IOException, ClassNotFoundException {
		System.out.println("Started Add address");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.addAddress(billingAddress, customer_id);
		System.out.println("Ended Add address");
		return items;
	}

	@RequestMapping(value = "/getAddress", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get address", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer getAddress(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "customer_id", required = true) long customer_id)
			throws IOException, ClassNotFoundException {
		System.out.println("Started Get address");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.getAddress(customer_id);
		System.out.println("Ended Get address");
		return items;
	}

	@RequestMapping(value = "/updateWarehouse", method = RequestMethod.PUT, headers = "Accept=application/json")
	@ApiOperation(value = "Update a Warehouse", notes = "Update a warehouse.",

			response = CustomerInfo.class, responseContainer = "List")

	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response updateWarehouse(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "warehouseId", required = true) String warehouseId, @RequestBody Warehouse warehouse)
			throws IOException, ClassNotFoundException {
		System.out.println("Started update warehouse..");
		RequestDAO requestLayer = new RequestDAO();
		Response items = requestLayer.updateWarehouse(warehouse, warehouseId, vendorId);
		System.out.println("Ended update warehouse..");
		return items;
	}

	@RequestMapping(value = "/transferOrder", method = RequestMethod.POST, headers = "Accept=application/json")
	@ApiOperation(value = "Transfer Order", notes = "get all Spread Model details or specific spread model detail by providing id as input",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public StringBuffer transferOrder(@RequestParam(name = "authcode", required = true) String vendorId,
			@RequestBody List<TransferOrder> transferOrder) throws IOException, ClassNotFoundException {
		System.out.println("Started Transfer Order.....");
		RequestDAO requestLayer = new RequestDAO();
		StringBuffer items = requestLayer.transferOrder(transferOrder);
		System.out.println("Ended Transfer Order");
		return items;
	}

	@RequestMapping(value = "/getSingleWarehouse", method = RequestMethod.GET, headers = "Accept=application/json")
	@ApiOperation(value = "Get a Warehouse details", notes = "Get Warehouse details.",

			response = CustomerInfo.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "List of Spread Model", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public List<Warehouse> getSingleWarehouse(@RequestParam(name = "authcode", required = true) long vendorId,
			@RequestParam(name = "warehouseId", required = true) String warehouseId)
			throws IOException, ClassNotFoundException {
		System.out.println("Started getSingleWarehouse");
		RequestDAO requestLayer = new RequestDAO();
		List<Warehouse> items = requestLayer.getSingleWarehouse(warehouseId, vendorId);
		System.out.println("Ended getSingleWarehouse");
		return items;
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add Item to the Zoho", notes = "Add Inventory", response = User.class, responseContainer = "List")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Add Inventory", response = CustomerInfo.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class) })
	public Response addItem(@RequestParam(name = "authcode", required = true) long vendorId, @RequestBody Item item) {
		System.out.println("Started add Item");
		System.out.println(item);
		Response response = null;
		try {
			RequestDAO requestLayer = new RequestDAO();
			response = requestLayer.addItem(item);
			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Ended add Item");
		return response;
	}

}