package com.logistics.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.logistics.Response;
import com.logistics.common.DBConnectionRest;
import com.logistics.email.notification.SendEmail;
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

/*@Repository*/
public class RequestDAO {

	public List<CustomerInfo> getAllRequests(String userId) throws IOException {
		List<CustomerInfo> customerInfo = new ArrayList<CustomerInfo>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
//		int roleid = check(vendorId);
//		if (roleid != 0) {
		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();
			System.out.println("userId:: " + userId);
			String query = "select FIRSTNAME,LASTNAME,PASSWORD,PHONENUMBER,ROLEID,USERID,customerid,vendorid,companyname from customerinfo where userid='"
					+ userId + "'";

			pstmtPjctRsn = conn.prepareStatement(query);
			rs = pstmtPjctRsn.executeQuery();

			CustomerInfo customerData;
			while (rs.next()) {
				customerData = new CustomerInfo();
				customerData.setFirstName(rs.getString("FIRSTNAME"));
				customerData.setLastName(rs.getString("LASTNAME"));
				customerData.setPassWord(rs.getString("PASSWORD"));
				customerData.setPhoneNumber(rs.getString("PHONENUMBER"));
				customerData.setRoleId(rs.getInt("ROLEID"));
				customerData.setUserId(rs.getString("USERID"));
				customerData.setContact_id(rs.getString("customerid"));
				customerData.setVendor_id(rs.getString("vendorid"));
				customerData.setCompanyName(rs.getString("companyName"));
				customerInfo.add(customerData);
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionRest.closeQuietly(rs);
			DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
//		} else {
//			System.out.println("--------------------############--------");
//			CustomerInfo customerData = new CustomerInfo();
//			customerData.setMessage("You have Insufficient permissions.");
//			customerData.setCode(400);
//			customerInfo.add(customerData);
//		}
		return customerInfo;
	}

	public Response updateRegion(Region region, int regionId, long vendorId) throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Update Region in DB was failed!");
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		int insertedRecords = 0;
		int roleid = check(vendorId);
		if (roleid == 1) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();
				String insertQuery = "update region set regionname=?,locationname=?,min_lang=?,max_lang=?,min_lat=?,max_lat=?,createdby=?,createdon=? where regionid="
						+ regionId;
				System.out.println(insertQuery);
				pstmtInsert = conn.prepareStatement(insertQuery);

				pstmtInsert.setString(1, region.getRegionName());
				pstmtInsert.setString(2, region.getLocationname());
				pstmtInsert.setDouble(3, region.getMin_lang());
				pstmtInsert.setDouble(4, region.getMax_lang());
				pstmtInsert.setDouble(5, region.getMin_lat());
				pstmtInsert.setDouble(6, region.getMax_lat());
				pstmtInsert.setString(7, region.getCreatedby());
//	            pstmtInsert.setDate(8, (Date) region.getCreatedon());
				pstmtInsert.setString(8, region.getCreatedon());
				insertedRecords = pstmtInsert.executeUpdate();
				System.out.println(insertedRecords);
				if (insertedRecords > 0) {
					response.setResponseCode("200");
					response.setResponseMessage("Region updated successfully");
				}
			} catch (ClassNotFoundException | SQLException e) {
				response.setMessage(e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(pstmtInsert);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			response.setResponseCode("400");
			response.setResponseMessage("You have insufficient permissions to use.");
		}
		return response;

	}

	private int getRegionRecords(int regionId) {
		int count = 0;
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet row;
		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();
			String query = "select count(regionId) from warehouseinfo where regionid=" + regionId;
			System.out.println(query);

			pstmtPjctRsn = conn.prepareStatement(query);
			row = pstmtPjctRsn.executeQuery();
			System.out.println("--------------------------------");
			System.out.println(row);
			while (row.next()) {
				count = row.getInt("count(regionid)");
				System.out.println("count:" + count);
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
		return count;
	}

	public Response deleteRegion(int regionId, long vendorId) throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Deleting Region was failed!");
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		int roleid = check(vendorId);
		int row;
		if (roleid == 1) {
			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();
				int count = getRegionRecords(regionId);
				System.out.println(count);
				if (count == 0) {
					String query = "delete from region where regionid=" + regionId;
					System.out.println(query);

					pstmtPjctRsn = conn.prepareStatement(query);
					row = pstmtPjctRsn.executeUpdate();
					System.out.println(row);
					response.setResponseCode("200");
					response.setResponseMessage("Region deleted successfully.");
				} else {
					System.out.println("Region " + regionId + " is assosciated with other warehouses");
					response.setResponseCode("400");
					response.setResponseMessage("Region " + regionId + " is assosciated with other warehouses");
				}

			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				response.setResponseMessage(e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			response.setResponseCode("400");
			response.setResponseMessage("You have insufficient permissions to use.");
		}
		return response;
	}

	public Response addRegion(Region region, long vendorId) throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add Region in DB was failed!");
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		int insertedRecords = 0;
		int roleid = check(vendorId);
		if (roleid == 1) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();
				String insertQuery = "insert into region (regionname,locationname,min_lang,max_lang,min_lat,max_lat,createdby,createdon) values (?,?,?,?,?,?,?,?)";
				System.out.println(insertQuery);
				pstmtInsert = conn.prepareStatement(insertQuery);

				pstmtInsert.setString(1, region.getRegionName());
				pstmtInsert.setString(2, region.getLocationname());
				pstmtInsert.setDouble(3, region.getMin_lang());
				pstmtInsert.setDouble(4, region.getMax_lang());
				pstmtInsert.setDouble(5, region.getMin_lat());
				pstmtInsert.setDouble(6, region.getMax_lat());
				pstmtInsert.setString(7, region.getCreatedby());
				pstmtInsert.setString(8, region.getCreatedon());
				insertedRecords = pstmtInsert.executeUpdate();
				System.out.println(insertedRecords);
				if (insertedRecords > 0) {
					response.setResponseCode("200");
					response.setResponseMessage("Region added successfully");
				}
			} catch (ClassNotFoundException | SQLException e) {
				response.setMessage(e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(pstmtInsert);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			response.setResponseCode("400");
			response.setResponseMessage("You have insufficient permissions to use.");
		}
		return response;

	}

	public Response addCustomer(long contactId, long vendorId, String company, CustomerInfo customerInfo, long authCode)
			throws IOException {

		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add customer was failed!");
		PreparedStatement pstmtInsert = null;
		int insertedRecords = 0;
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		int roleid = check(authCode);
		if (roleid == 1) {
			String subject = "You're invited to join Logistics";
			String body = "You're invited to join Logistics. Here is your system generated password <b>welcome123</b>. <br/> <br/>If you're having problems, please feel free to contact us at logistics@artissol.com. We'll be glad to help.<br/> <br/>Regards,<br/>Team Logistics.";
			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();
				String insertQuery = "insert into customerinfo (FIRSTNAME, LASTNAME, PASSWORD,PHONENUMBER, userid, roleid, customerid,vendorid,companyname) values (?,?,?,?,?,?,?,?,?)";
				System.out.println(insertQuery);
				System.out.println(customerInfo.getFirstName() + customerInfo.getLastName() + customerInfo.getUserId()
						+ customerInfo.getRoleId());
				pstmtInsert = conn.prepareStatement(insertQuery);

				pstmtInsert.setString(1, customerInfo.getFirstName());
				pstmtInsert.setString(2, customerInfo.getLastName());
				pstmtInsert.setString(3, "welcome123");
				pstmtInsert.setString(4, customerInfo.getPhoneNumber());
				pstmtInsert.setString(5, customerInfo.getUserId());
				pstmtInsert.setInt(6, customerInfo.getRoleId());
				pstmtInsert.setLong(7, contactId);
				pstmtInsert.setLong(8, vendorId);
				pstmtInsert.setString(9, company);
				insertedRecords = pstmtInsert.executeUpdate();
				System.out.println(insertedRecords);
				if (insertedRecords > 0) {
					response.setResponseCode("200");

					if (response.getResponseCode().equals("200")) {
						SendEmail mail = new SendEmail();
						mail.sendEmail(customerInfo.getUserId(), "", "", subject, body, null);
					}

					response.setResponseMessage("Customer added successfully");
					System.out.println(response.getResponseMessage() + "##############");
					// conn.commit();
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(pstmtInsert);
				// DBConnectionRest.closeQuietly(rs);
				// DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			response.setResponseCode("400");
			response.setResponseMessage("You have insufficient permissions to use.");
		}
		return response;
	}

	public List<Roles> getRoles(long vendorId) throws IOException {
		List<Roles> optionroles = new ArrayList<Roles>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		int roleid = check(vendorId);
		if (roleid != 0) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "select * from optionrole";

				pstmtPjctRsn = conn.prepareStatement(query);
				rs = pstmtPjctRsn.executeQuery();

				Roles rolesData;
				while (rs.next()) {
					rolesData = new Roles();
					rolesData.setRoleId(rs.getInt("ROLEID"));
					rolesData.setRoleName(rs.getString("ROLENAME"));
					optionroles.add(rolesData);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(rs);
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			System.out.println("--------------------############--------");
			Roles rolesData = new Roles();
			rolesData.setMessage("You have Insufficient permissions.");
			rolesData.setCode(400);
			optionroles.add(rolesData);
		}
		return optionroles;
	}

	public Response forgotPassword(String UserName, String Password, int reason) {
		Response response = new Response();
		PreparedStatement pstmtInsert = null;
		int insertedRecords = 0;
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		String subject = "Password has been changed.";
		String body = "";
		if (reason == 1) {
			body = "Password has been changed <b>welcome123</b>";
		} else {
			body = "Password changed successfully for the user " + UserName;
		}
		if (reason == 1) {
			Password = "welcome123";
		}
		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();
			String insertQuery = "update customerinfo set password =? where userid =?";
			pstmtInsert = conn.prepareStatement(insertQuery);

			pstmtInsert.setString(1, Password);
			pstmtInsert.setString(2, UserName);

			insertedRecords = pstmtInsert.executeUpdate();
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println(UserName + Password + reason);
			System.out.println(insertedRecords);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			if (insertedRecords > 0) {
				response.setResponseCode("200");
				if (response.getResponseCode().equals("200")) {
					SendEmail mail = new SendEmail();
					mail.sendEmail(UserName, "", "", subject, body, null);
				}
				response.setResponseMessage(" Updated New Password successfully");

				// conn.commit();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnectionRest.closeQuietly(pstmtInsert);
			// DBConnectionRest.closeQuietly(rs);
			// DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public Response addZohoUser(boolean isVendor, User user) throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add customer in Zoho  was failed!");
		String url = "https://inventory.zoho.in/api/v1/contacts?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		JSONObject zohoInvoiceRequest = new JSONObject();
		if (isVendor) {
			zohoInvoiceRequest.put("contact_type", "vendor");
		} else {
			zohoInvoiceRequest.put("contact_type", "customer");
		}
		zohoInvoiceRequest.put("contact_name", user.getContact_name());
		zohoInvoiceRequest.put("company_name", user.getCompany_name());
		// innerobj
		JSONObject contacts = new JSONObject();
		System.out.println("Initializing contacts" + user);
		System.out.println(user.getFirst_name());
		contacts.put("first_name", user.getFirst_name());
		contacts.put("email", user.getEmail());
		contacts.put("phone", user.getPhone());
		System.out.println(contacts);
		JSONArray arr = new JSONArray();
		arr.put(contacts);
		zohoInvoiceRequest.put("contact_persons", arr);

		System.out.println(zohoInvoiceRequest);

		url += "&JSONString=";
		url += URLEncoder.encode(zohoInvoiceRequest.toJSONString(), "UTF-8");
		System.out.println(url);

		HttpPost post = new HttpPost(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);

		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		JsonElement jelement = new JsonParser().parse(result.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		if (zohoresponse.getStatusLine().getStatusCode() == 201) {
			response.setResponseCode("201");
			response.setResponseMessage("The contact has been added.");
			JSONObject contact_id = new JSONObject();
			contact_id.put("contact_id", jobject.get("contact").getAsJsonObject().get("contact_id"));
			response.setObject(contact_id.toString());

		} else if (zohoresponse.getStatusLine().getStatusCode() == 400) {
			response.setResponseCode("400");
			response.setResponseMessage(jobject.get("message").getAsString());
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public Response addInventory(PurchaseOrder purchaseOrder) throws IOException {
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		String query;
		System.out.println(purchaseOrder.getAttention());
		String regionName = "";
		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();

			query = "select region.regionname FROM region RIGHT JOIN warehouseinfo ON warehouseinfo.regionid=region.regionid where warehousename='"
					+ purchaseOrder.getAttention() + "'";
			System.out.println(query);
			pstmtPjctRsn = conn.prepareStatement(query);
			rs = pstmtPjctRsn.executeQuery();

			System.out.println(rs);
			while (rs.next()) {
				System.out.println("----------------------");
				System.out.println(rs.getString("regionname"));
				regionName = rs.getString("regionname");
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionRest.closeQuietly(rs);
			DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add Inventory was failed!");
		System.out.println(purchaseOrder.getLineItems());
		String url = "https://inventory.zoho.in/api/v1/purchaseorders?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		JSONObject purchaseOrderRequest = new JSONObject();
		JSONArray arr = new JSONArray();
		JSONArray custom_arr = new JSONArray();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		purchaseOrderRequest.put("purchaseorder_number", purchaseOrder.getPurchaseorder_number());
		purchaseOrderRequest.put("date", df.format(new Date()));
		purchaseOrderRequest.put("delivery_date", purchaseOrder.getDelivery_date());
		purchaseOrderRequest.put("ship_via", purchaseOrder.getShip_via());
		purchaseOrderRequest.put("vendor_id", purchaseOrder.getVendor_id());
		purchaseOrderRequest.put("attention", purchaseOrder.getAttention());
		purchaseOrderRequest.put("delivery_org_address_id", purchaseOrder.getDelivery_org_address_id());
		for (int i = 0; i < purchaseOrder.getLineItems().size(); i++) {
			JSONObject line_items = new JSONObject();
			line_items.put("item_id", purchaseOrder.getLineItems().get(i).getItemId());
			line_items.put("name", purchaseOrder.getLineItems().get(i).getName());
			line_items.put("rate", purchaseOrder.getLineItems().get(i).getRate());
			line_items.put("quantity", purchaseOrder.getLineItems().get(i).getQuantity());
			line_items.put("unit", purchaseOrder.getLineItems().get(i).getUnit());
			line_items.put("warehouse_id", purchaseOrder.getLineItems().get(i).getWarehouseId());
			arr.put(line_items);
		}
		purchaseOrderRequest.put("line_items", arr);
		for (int i = 0; i < purchaseOrder.getCustomFields().size(); i++) {
			JSONObject custom_obj = new JSONObject();
			custom_obj.put("label", purchaseOrder.getCustomFields().get(i).getLabel());
			custom_obj.put("value", purchaseOrder.getCustomFields().get(i).getValue());
			custom_arr.put(custom_obj);
		}
		JSONObject regionObj = new JSONObject();
		regionObj.put("label", "Region");
		regionObj.put("value", regionName);
		custom_arr.put(regionObj);
		purchaseOrderRequest.put("custom_fields", custom_arr);
		System.out.println(purchaseOrderRequest);
		url += "&JSONString=";
		url += URLEncoder.encode(purchaseOrderRequest.toJSONString(), "UTF-8");
		System.out.println(url);

		HttpPost post = new HttpPost(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);

		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		JsonElement jelement = new JsonParser().parse(result.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		if (zohoresponse.getStatusLine().getStatusCode() == 201) {
			response.setResponseCode("201");
			response.setResponseMessage("The Inventory has been created.");
			JSONObject purchaseorder_id = new JSONObject();
			purchaseorder_id.put("purchaseorder_id",
					jobject.get("purchaseorder" + "").getAsJsonObject().get("purchaseorder_id"));
			purchaseorder_id.put("purchaseorder_number",
					jobject.get("purchaseorder" + "").getAsJsonObject().get("purchaseorder_number"));
			response.setObject(purchaseorder_id.toString());
		} else if (zohoresponse.getStatusLine().getStatusCode() == 400) {
			response.setResponseCode("400");
			response.setResponseMessage(jobject.get("message").getAsString());
		}

		return response;
	}

	public StringBuffer getAllWarehouses() throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Get all warehouses was failed!");
		String url = "https://inventory.zoho.in/api/v1/settings/warehouses?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	public StringBuffer getItems() throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("getItems was failed!");
		String url = "https://inventory.zoho.in/api/v1/items?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	public StringBuffer getAllInventories() throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("get all inventories was failed!");
		String url = "https://inventory.zoho.in/api/v1/purchaseorders?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	public StringBuffer getSinglePurchase(long purchaseorder_id) throws UnsupportedOperationException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("get single purchase was failed!");
		String url = "https://inventory.zoho.in/api/v1/purchaseorders/" + purchaseorder_id
				+ "?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	public StringBuffer deleteInventory(String purchaseorder_id) throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("deleteInventory was failed!");
		String url = "https://inventory.zoho.in/api/v1/purchaseorders/" + purchaseorder_id
				+ "/status/cancelled?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		System.out.println(url + "_______________________________________");
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Response addWarehouse(Warehouse warehouse) throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("addWarehouse in zoho was failed!");
		String url = "https://inventory.zoho.in/api/v1/settings/warehouses?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		JSONObject warehouseRequest = new JSONObject();
		warehouseRequest.put("warehouse_name", warehouse.getWarehouseName());
		warehouseRequest.put("address", warehouse.getAddress());
		warehouseRequest.put("city", warehouse.getCity());
		warehouseRequest.put("state", warehouse.getState());
		warehouseRequest.put("country", warehouse.getCountry());
		warehouseRequest.put("zip", warehouse.getZip());
		warehouseRequest.put("phone", warehouse.getPhone());
		warehouseRequest.put("email", warehouse.getEmail());
		System.out.println(warehouseRequest);

		url += "&JSONString=";
		url += URLEncoder.encode(warehouseRequest.toJSONString(), "UTF-8");
		System.out.println(url);

		HttpPost post = new HttpPost(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);

		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		JsonElement jelement = new JsonParser().parse(result.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		if (zohoresponse.getStatusLine().getStatusCode() == 201) {
			response.setResponseCode("201");
			response.setResponseMessage("The Warehouse has been created.");
			JSONObject warehouse_name = new JSONObject();
			warehouse_name.put("warehouse_name", jobject.get("warehouse" + "").getAsJsonObject().get("warehouse_name"));
			response.setObject(warehouse_name.toString());
		} else if (zohoresponse.getStatusLine().getStatusCode() == 400) {
			response.setResponseCode("400");
			response.setResponseMessage(jobject.get("message").getAsString());
		}
		return response;
	}

	public Response addWarehouseinDb(Warehouse warehouse, long vendorId) throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add warehouse in DB was failed!");
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		int insertedRecords = 0;
		int roleid = check(vendorId);
		if (roleid == 1) {
			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();
				String insertQuery = "insert into warehouseinfo (warehousename, address, city,state, country, zip, phone,email,warehousetype,warehouseid,regionid,warehouse_lat,warehouse_lang,type,status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				System.out.println(insertQuery);
				pstmtInsert = conn.prepareStatement(insertQuery);

				pstmtInsert.setString(1, warehouse.getWarehouseName());
				pstmtInsert.setString(2, warehouse.getAddress());
				pstmtInsert.setString(3, warehouse.getCity());
				pstmtInsert.setString(4, warehouse.getState());
				pstmtInsert.setString(5, warehouse.getCountry());
				pstmtInsert.setString(6, warehouse.getZip());
				pstmtInsert.setString(7, warehouse.getPhone());
				pstmtInsert.setString(8, warehouse.getEmail());
				pstmtInsert.setString(9, warehouse.getWarehouse_type());
				pstmtInsert.setString(10, warehouse.getWarehouseid());
				pstmtInsert.setInt(11, warehouse.getRegionId());
				pstmtInsert.setDouble(12, warehouse.getWarehouse_lat());
				pstmtInsert.setDouble(13, warehouse.getWarehouse_lang());
				pstmtInsert.setInt(14, warehouse.getType());
				pstmtInsert.setInt(15, 0);
				insertedRecords = pstmtInsert.executeUpdate();
				System.out.println(insertedRecords);
				if (insertedRecords > 0) {
					response.setResponseCode("200");
					response.setResponseMessage("Warehouse added successfully");
				}
			} catch (ClassNotFoundException | SQLException e) {
				response.setMessage(e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(pstmtInsert);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			response.setResponseCode("400");
			response.setResponseMessage("You have insufficient permissions to use.");
		}

		return response;
	}

	public List<WarehouseType> getWarehousetypes(long vendorId) throws IOException {
		List<WarehouseType> types = new ArrayList<WarehouseType>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		int roleid = check(vendorId);
		if (roleid != 0) {
			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "select * from warehousetype";

				pstmtPjctRsn = conn.prepareStatement(query);
				rs = pstmtPjctRsn.executeQuery();

				WarehouseType warehouseData;
				while (rs.next()) {
					warehouseData = new WarehouseType();
					warehouseData.setWarehouseId(rs.getInt("warehouseid"));
					warehouseData.setWarehouseName(rs.getString("warehousename"));
					types.add(warehouseData);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(rs);
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			System.out.println("--------------------############--------");
			WarehouseType warehouseData = new WarehouseType();
			warehouseData.setMessage("You have Insufficient permissions.");
			warehouseData.setCode(400);
			types.add(warehouseData);
		}
		return types;
	}

	public List<Warehouse> getWarehouseDb(long vendorId) throws IOException {
		List<Warehouse> warehousesInfo = new ArrayList<Warehouse>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		int roleid = check(vendorId);
		if (roleid != 0) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "select * from warehouseinfo";

				pstmtPjctRsn = conn.prepareStatement(query);
				rs = pstmtPjctRsn.executeQuery();
				Warehouse warehouseData;
				while (rs.next()) {
					warehouseData = new Warehouse();
					warehouseData.setWarehouseName(rs.getString("warehousename"));
					warehouseData.setAddress(rs.getString("address"));
					warehouseData.setCity(rs.getString("city"));
					warehouseData.setState(rs.getString("state"));
					warehouseData.setCountry(rs.getString("country"));
					warehouseData.setZip(rs.getString("zip"));
					warehouseData.setPhone(rs.getString("phone"));
					warehouseData.setEmail(rs.getString("email"));
					warehouseData.setWarehouse_type(rs.getString("warehousetype"));
					warehouseData.setWarehouseid(rs.getString("warehouseid"));
					warehouseData.setWarehouse_lang(rs.getDouble("warehouse_lang"));
					warehouseData.setWarehouse_lat(rs.getDouble("warehouse_lat"));
					warehouseData.setRegionId(rs.getInt("regionId"));
					warehouseData.setStatus(rs.getInt("status"));
					warehouseData.setType(rs.getInt("type"));
					warehousesInfo.add(warehouseData);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(rs);
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			System.out.println("--------------------############--------");
			Warehouse warehouseData = new Warehouse();
			warehouseData.setMessage("You have Insufficient permissions.");
			warehouseData.setCode(400);
			warehousesInfo.add(warehouseData);
		}
		return warehousesInfo;
	}

	public StringBuffer deleteWarehouse(String warehouse_id) throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("inactive a warehouse  was failed!");
		String url = "https://inventory.zoho.in/api/v1/warehouses/" + warehouse_id
				+ "/inactive?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost delete = new HttpPost(url);
		delete.addHeader("Accept", "application/json");
		delete.addHeader("charset", "UTF-8");
		delete.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(delete);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	public Response deleteWarehouseinDb(String warehouse_id, long vendorId) throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Deleting warehouse in db was failed!");
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		int row;
		int roleid = check(vendorId);
		if (roleid == 1) {
			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "update warehouseinfo set status=1 where warehouseid=" + warehouse_id;
				System.out.println(query);

				pstmtPjctRsn = conn.prepareStatement(query);
				row = pstmtPjctRsn.executeUpdate();
				System.out.println(row);
				response.setResponseCode("200");
				response.setResponseMessage("Warehouse deleted successfully.");
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				response.setResponseMessage(e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			response.setResponseCode("400");
			response.setResponseMessage("You have insufficient permissions to use.");
		}
		return response;
	}

	public StringBuffer getCustomFieldOptions(String entity) throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Getting customfields was failed!");
		String url = "https://inventory.zoho.in/api/v1/settings/preferences/customfields?entity=" + entity
				+ "&authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		System.out.println(url);
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	public List<Warehouse> getWarehousesByFilter(String type, long vendorId) throws IOException {
		List<Warehouse> warehousesInfo = new ArrayList<Warehouse>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		int roleid = check(vendorId);
		if (roleid != 0) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "select * from warehouseinfo where warehousetype=" + type;

				pstmtPjctRsn = conn.prepareStatement(query);
				rs = pstmtPjctRsn.executeQuery();
				Warehouse warehouseData;
				while (rs.next()) {
					warehouseData = new Warehouse();
					warehouseData.setWarehouseName(rs.getString("warehousename"));
					warehouseData.setAddress(rs.getString("address"));
					warehouseData.setCity(rs.getString("city"));
					warehouseData.setState(rs.getString("state"));
					warehouseData.setCountry(rs.getString("country"));
					warehouseData.setZip(rs.getString("zip"));
					warehouseData.setPhone(rs.getString("phone"));
					warehouseData.setEmail(rs.getString("email"));
					warehouseData.setWarehouse_type(rs.getString("warehousetype"));
					warehouseData.setWarehouseid(rs.getString("warehouseid"));
					warehouseData.setRegionId(rs.getInt("regionId"));
					warehouseData.setStatus(rs.getInt("status"));
					warehouseData.setWarehouse_lang(rs.getDouble("warehouse_lang"));
					warehouseData.setWarehouse_lat(rs.getDouble("warehouse_lat"));
					warehouseData.setType(rs.getInt("type"));
					warehousesInfo.add(warehouseData);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(rs);
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			System.out.println("--------------------############--------");
			Warehouse warehouseData = new Warehouse();
			warehouseData.setMessage("You have Insufficient permissions.");
			warehouseData.setCode(400);
			warehousesInfo.add(warehouseData);
		}
		return warehousesInfo;
	}

	public StringBuffer getAllSalesOrders() throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Getting salesorders was failed!");
		String url = "https://inventory.zoho.in/api/v1/salesorders?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	public StringBuffer getSalesOrder(long salesorder_id) throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("getSalesOrder was failed!");
		String url = "https://inventory.zoho.in/api/v1/salesorders/" + salesorder_id
				+ "?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;

	}

	public StringBuffer deleteSalesOrder(long salesorder_id) throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("delete salesorder was failed!");
		String url = "https://inventory.zoho.in/api/v1/salesorders/" + salesorder_id
				+ "/status/void?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		System.out.println(url + "_______________________________________");
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;

	}

	public StringBuffer getSingleItem(long item_id) throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("getSingleItem was failed!");
		String url = "https://inventory.zoho.in/api/v1/items/" + item_id
				+ "?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Response addSalesOrder(boolean isClone, boolean isDropAt, SalesOrder salesOrder)
			throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add salesorder was failed!");
		String url = "https://inventory.zoho.in/api/v1/salesorders?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		JSONObject salesOrderRequest = new JSONObject();
		JSONArray arr = new JSONArray();
		JSONArray custom_arr = new JSONArray();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		salesOrderRequest.put("customer_id", salesOrder.getCustomer_id());
		salesOrderRequest.put("salesorder_number", salesOrder.getSalesorder_number());
		if (!isClone) {
			if (salesOrder.getShipping_address_id() != 0)
				salesOrderRequest.put("shipping_address_id", salesOrder.getShipping_address_id());
			salesOrderRequest.put("billing_address_id", salesOrder.getBilling_address_id());
		}

//		salesOrderRequest.put("customer_id", salesOrder.getCustomer_id());
//		salesOrderRequest.put("salesorder_number", salesOrder.getSalesorder_number());
//		salesOrderRequest.put("shipping_address_id", salesOrder.getShipping_address_id());
//		salesOrderRequest.put("billing_address_id", salesOrder.getBilling_address_id());
		salesOrderRequest.put("date", df.format(new Date()));
		salesOrderRequest.put("shipment_date", salesOrder.getShipment_date());

		for (int i = 0; i < salesOrder.getLineItems().size(); i++) {
			JSONObject line_items = new JSONObject();
			line_items.put("item_id", salesOrder.getLineItems().get(i).getItemId());
			line_items.put("name", salesOrder.getLineItems().get(i).getName());
			line_items.put("quantity", salesOrder.getLineItems().get(i).getQuantity());
			line_items.put("unit", salesOrder.getLineItems().get(i).getUnit());
			if (!isDropAt)
				line_items.put("warehouse_id", salesOrder.getLineItems().get(i).getWarehouseId());
			arr.put(line_items);
		}
		salesOrderRequest.put("line_items", arr);
		for (int i = 0; i < salesOrder.getCustomFields().size(); i++) {
			JSONObject custom_obj = new JSONObject();
			custom_obj.put("label", salesOrder.getCustomFields().get(i).getLabel());
			custom_obj.put("value", salesOrder.getCustomFields().get(i).getValue());
			custom_arr.put(custom_obj);
		}
		salesOrderRequest.put("custom_fields", custom_arr);
		System.out.println(salesOrderRequest);
		url += "&JSONString=";
		url += URLEncoder.encode(salesOrderRequest.toJSONString(), "UTF-8");
		System.out.println(url);

		HttpPost post = new HttpPost(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);

		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		JsonElement jelement = new JsonParser().parse(result.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		if (zohoresponse.getStatusLine().getStatusCode() == 201) {
			response.setResponseCode("201");
			response.setResponseMessage("The SalesOrder has been created.");
			JSONObject salesorder_id = new JSONObject();
			salesorder_id.put("salesorder_id", jobject.get("salesorder" + "").getAsJsonObject().get("salesorder_id"));
			salesorder_id.put("salesorder_number",
					jobject.get("salesorder" + "").getAsJsonObject().get("salesorder_number"));
			response.setObject(salesorder_id.toString());

		} else if (zohoresponse.getStatusLine().getStatusCode() == 400) {
			response.setResponseCode("400");
			response.setResponseMessage(jobject.get("message").getAsString());
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	public Response updateWarehouseZoho(Warehouse warehouse, long warehouseId)
			throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Update Warehouse in zoho was failed!");
		String url = "https://inventory.zoho.in/api/v1/settings/warehouses/" + warehouseId
				+ "?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		System.out.println(url + "------------------------");
		HttpClient client = HttpClientBuilder.create().build();
		JSONObject warehouseRequest = new JSONObject();
		warehouseRequest.put("warehouse_name", warehouse.getWarehouseName());
		warehouseRequest.put("address", warehouse.getAddress());
		warehouseRequest.put("city", warehouse.getCity());
		warehouseRequest.put("state", warehouse.getState());
		warehouseRequest.put("country", warehouse.getCountry());
		warehouseRequest.put("zip", warehouse.getZip());
		warehouseRequest.put("phone", warehouse.getPhone());
		warehouseRequest.put("email", warehouse.getEmail());
		System.out.println(warehouseRequest);

		url += "&JSONString=";
		url += URLEncoder.encode(warehouseRequest.toJSONString(), "UTF-8");
		System.out.println(url);

		HttpPut post = new HttpPut(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);

		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		JsonElement jelement = new JsonParser().parse(result.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		if (zohoresponse.getStatusLine().getStatusCode() == 200) {
			response.setResponseCode("200");
			response.setResponseMessage("The Warehouse has been updated.");
			JSONObject warehouse_name = new JSONObject();
			warehouse_name.put("warehouse_name", jobject.get("warehouse" + "").getAsJsonObject().get("warehouse_name"));
			response.setObject(warehouse_name.toString());
		} else if (zohoresponse.getStatusLine().getStatusCode() == 400) {
			response.setResponseCode("400");
			response.setResponseMessage(jobject.get("message").getAsString());
		}
		return response;
	}

	public List<CustomerInfo> getAllCustomers(long vendorId) throws IOException {
		List<CustomerInfo> customers = new ArrayList<CustomerInfo>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		int roleid = check(vendorId);
		if (roleid != 0) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "select * from customerinfo";

				pstmtPjctRsn = conn.prepareStatement(query);
				rs = pstmtPjctRsn.executeQuery();

				CustomerInfo customerInfo;
				while (rs.next()) {
					customerInfo = new CustomerInfo();
					customerInfo.setFirstName(rs.getString("firstname"));
					customerInfo.setLastName(rs.getString("lastname"));
					customerInfo.setPassWord(rs.getString("password"));
					customerInfo.setPhoneNumber(rs.getString("phonenumber"));
					customerInfo.setContact_id(rs.getString("customerid"));
					customerInfo.setVendor_id(rs.getString("vendorid"));
					customerInfo.setRoleId(rs.getInt("roleid"));
					customerInfo.setUserId(rs.getString("userid"));
					customerInfo.setCompanyName(rs.getString("companyName"));
					customers.add(customerInfo);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(rs);
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			System.out.println("--------------------############--------");
			CustomerInfo customerInfo = new CustomerInfo();
			customerInfo.setMessage("You have Insufficient permissions.");
			customerInfo.setCode(400);
			customers.add(customerInfo);
		}
		return customers;

	}

	@SuppressWarnings("unchecked")
	public Response addAddress(BillingAddress billingAddress, long customer_id)
			throws ClientProtocolException, IOException {

		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add address was failed!");
		String url = "https://inventory.zoho.in/api/v1/contacts/" + customer_id
				+ "/address?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		JSONObject addressRequest = new JSONObject();
		addressRequest.put("attention", billingAddress.getAttention());
		addressRequest.put("address", billingAddress.getAddress());
		addressRequest.put("street2", billingAddress.getStreet2());
		addressRequest.put("city", billingAddress.getCity());
		addressRequest.put("state", billingAddress.getState());
		addressRequest.put("zip", billingAddress.getZip());
		addressRequest.put("country", billingAddress.getCountry());
		addressRequest.put("phone", billingAddress.getPhone());
		url += "&JSONString=";
		url += URLEncoder.encode(addressRequest.toJSONString(), "UTF-8");
		System.out.println(url);

		HttpPost post = new HttpPost(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);

		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		JsonElement jelement = new JsonParser().parse(result.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		if (zohoresponse.getStatusLine().getStatusCode() == 201) {
			response.setResponseCode("201");
			response.setResponseMessage("Address has been created.");
			JSONObject address_id = new JSONObject();
			address_id.put("address_id", jobject.get("address_info" + "").getAsJsonObject().get("address_id"));
			response.setObject(address_id.toString());

		} else if (zohoresponse.getStatusLine().getStatusCode() == 400) {
			response.setResponseCode("400");
			response.setResponseMessage(jobject.get("message").getAsString());
		}

		return response;

	}

	public StringBuffer getAddress(long customer_id) throws ClientProtocolException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Getting address was failed!");
		String url = "https://inventory.zoho.in/api/v1/contacts/" + customer_id
				+ "/address?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Response addItem(Item item) throws UnsupportedOperationException, IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Add Item was failed!");
		String url = "https://inventory.zoho.in/api/v1/items?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		JSONObject itemRequest = new JSONObject();
		JSONArray custom_arr = new JSONArray();
		itemRequest.put("name", item.getName());
		itemRequest.put("unit", item.getUnit());
		itemRequest.put("product_type", "goods");
		itemRequest.put("rate", item.getRate());
		itemRequest.put("sku", item.getSku());
		itemRequest.put("purchase_rate", item.getPurchase_rate());
		itemRequest.put("purchase_description", item.getPurchase_description());
		itemRequest.put("item_type", "inventory");
		for (int i = 0; i < item.getCustomFields().size(); i++) {
			JSONObject custom_obj = new JSONObject();
			custom_obj.put("label", item.getCustomFields().get(i).getLabel());
			custom_obj.put("value", item.getCustomFields().get(i).getValue());
			custom_arr.put(custom_obj);
		}
		itemRequest.put("custom_fields", custom_arr);
		System.out.println(itemRequest);
		url += "&JSONString=";
		url += URLEncoder.encode(itemRequest.toJSONString(), "UTF-8");
		System.out.println(url);

		HttpPost post = new HttpPost(url);
		post.addHeader("Accept", "application/json");
		post.addHeader("charset", "UTF-8");
		post.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(post);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);

		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		JsonElement jelement = new JsonParser().parse(result.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		if (zohoresponse.getStatusLine().getStatusCode() == 201) {
			response.setResponseCode("201");
			response.setResponseMessage("The Item has been created.");
			JSONObject purchaseorder_id = new JSONObject();
			purchaseorder_id.put("item_id", jobject.get("item" + "").getAsJsonObject().get("item_id"));
			purchaseorder_id.put("item_name", jobject.get("item" + "").getAsJsonObject().get("name"));
			response.setObject(purchaseorder_id.toString());

		} else if (zohoresponse.getStatusLine().getStatusCode() == 400) {
			response.setResponseCode("400");
			response.setResponseMessage(jobject.get("message").getAsString());
		}

		return response;
	}

	public List<Region> getAllRegions(long vendorId) throws IOException {
		List<Region> regions = new ArrayList<Region>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		int roleid = check(vendorId);
		if (roleid != 0) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "select * from region";

				pstmtPjctRsn = conn.prepareStatement(query);
				rs = pstmtPjctRsn.executeQuery();

				Region regionData;
				while (rs.next()) {
					regionData = new Region();
					regionData.setRegionid(rs.getInt("regionid"));
					regionData.setRegionName(rs.getString("regionname"));
					regionData.setLocationname(rs.getString("locationname"));
					regionData.setMin_lang(rs.getDouble("min_lang"));
					regionData.setMax_lang(rs.getDouble("max_lang"));
					regionData.setMin_lat(rs.getDouble("min_lat"));
					regionData.setMax_lat(rs.getDouble("max_lat"));
					regionData.setCreatedby(rs.getString("createdby"));
					regionData.setCreatedon(rs.getString("createdon"));
					regions.add(regionData);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(rs);
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			System.out.println("--------------------############--------");
			Region regionData = new Region();
			regionData.setMessage("You have Insufficient permissions.");
			regionData.setCode(400);
			regions.add(regionData);
		}
		return regions;
	}

	public Response updateWarehouse(Warehouse warehouse, String warehouseId, long vendorId) throws IOException {
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Update warehouse in DB was failed!");
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		int insertedRecords = 0;
		int roleid = check(vendorId);
		if (roleid == 1) {
			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();
				String insertQuery = "update warehouseinfo set warehousename=?,address=?,city=?,state=?,country=?,zip=?,phone=?,email=?,warehousetype=?,warehouseid=?,regionid=?,warehouse_lat=?,warehouse_lang=?,type=? where warehouseid="
						+ warehouseId;
				System.out.println(insertQuery);
				pstmtInsert = conn.prepareStatement(insertQuery);

				pstmtInsert.setString(1, warehouse.getWarehouseName());
				pstmtInsert.setString(2, warehouse.getAddress());
				pstmtInsert.setString(3, warehouse.getCity());
				pstmtInsert.setString(4, warehouse.getState());
				pstmtInsert.setString(5, warehouse.getCountry());
				pstmtInsert.setString(6, warehouse.getZip());
				pstmtInsert.setString(7, warehouse.getPhone());
				pstmtInsert.setString(8, warehouse.getEmail());
				pstmtInsert.setString(9, warehouse.getWarehouse_type());
				pstmtInsert.setString(10, warehouse.getWarehouseid());
				pstmtInsert.setInt(11, warehouse.getRegionId());
				pstmtInsert.setDouble(12, warehouse.getWarehouse_lat());
				pstmtInsert.setDouble(13, warehouse.getWarehouse_lang());
				pstmtInsert.setInt(14, warehouse.getType());
				insertedRecords = pstmtInsert.executeUpdate();
				System.out.println(insertedRecords);
				if (insertedRecords > 0) {
					response.setResponseCode("200");
					response.setResponseMessage("Warehouse updated successfully");
				}
			} catch (ClassNotFoundException | SQLException e) {
				response.setMessage(e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(pstmtInsert);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			response.setResponseCode("400");
			response.setResponseMessage("You have insufficient permissions to use.");
		}
		return response;

	}

	public List<Region> getSingleRegion(int regionId, long vendorId) throws IOException {
		List<Region> regionsInfo = new ArrayList<Region>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		int roleid = check(vendorId);
		if (roleid != 0) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "select * from region where regionid=" + regionId;

				pstmtPjctRsn = conn.prepareStatement(query);
				rs = pstmtPjctRsn.executeQuery();
				Region regionData;
				while (rs.next()) {
					regionData = new Region();
					regionData.setRegionid(rs.getInt("regionid"));
					regionData.setRegionName(rs.getString("regionname"));
					regionData.setLocationname(rs.getString("locationname"));
					regionData.setMin_lang(rs.getDouble("min_lang"));
					regionData.setMax_lang(rs.getDouble("max_lang"));
					regionData.setMin_lat(rs.getDouble("min_lat"));
					regionData.setMax_lat(rs.getDouble("max_lat"));
					regionData.setCreatedby(rs.getString("createdby"));
					regionData.setCreatedon(rs.getString("createdon"));
					regionsInfo.add(regionData);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(rs);
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			System.out.println("--------------------############--------");
			Region regionData = new Region();
			regionData.setMessage("You have Insufficient permissions.");
			regionData.setCode(400);
			regionsInfo.add(regionData);
		}
		return regionsInfo;
	}

	public List<Warehouse> getSingleWarehouse(String warehouseId, long vendorId) throws IOException {
		List<Warehouse> warehousesInfo = new ArrayList<Warehouse>();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet rs = null;
		int roleid = check(vendorId);
		if (roleid != 0) {

			try {
				dbConnection = new DBConnectionRest();
				conn = dbConnection.getConnect();

				String query = "select * from warehouseinfo where warehouseid=" + warehouseId;

				pstmtPjctRsn = conn.prepareStatement(query);
				rs = pstmtPjctRsn.executeQuery();
				Warehouse warehouseData;
				while (rs.next()) {
					warehouseData = new Warehouse();
					warehouseData.setWarehouseName(rs.getString("warehousename"));
					warehouseData.setAddress(rs.getString("address"));
					warehouseData.setCity(rs.getString("city"));
					warehouseData.setState(rs.getString("state"));
					warehouseData.setCountry(rs.getString("country"));
					warehouseData.setZip(rs.getString("zip"));
					warehouseData.setPhone(rs.getString("phone"));
					warehouseData.setEmail(rs.getString("email"));
					warehouseData.setRegionId(rs.getInt("regionId"));
					warehouseData.setWarehouse_type(rs.getString("warehousetype"));
					warehouseData.setWarehouseid(rs.getString("warehouseid"));
					warehouseData.setWarehouse_lang(rs.getDouble("warehouse_lang"));
					warehouseData.setWarehouse_lat(rs.getDouble("warehouse_lat"));
					warehouseData.setType(rs.getInt("type"));
					warehouseData.setStatus(rs.getInt("status"));
					warehousesInfo.add(warehouseData);
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBConnectionRest.closeQuietly(rs);
				DBConnectionRest.closeQuietly(pstmtPjctRsn);
				DBConnectionRest.closeQuietly(conn);
			}
		} else {
			System.out.println("--------------------############--------");
			Warehouse warehouseData = new Warehouse();
			warehouseData.setMessage("You have Insufficient permissions.");
			warehouseData.setCode(400);
			warehousesInfo.add(warehouseData);
		}
		return warehousesInfo;
	}

	@SuppressWarnings("unchecked")
	public StringBuffer transferOrder(List<TransferOrder> transferOrder123) throws IOException {

		System.out.println("Transferorder Dataa :::: " + transferOrder123);
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Transfer Order was failed!");
		StringBuffer result = new StringBuffer();
		for (TransferOrder transferOrder : transferOrder123) {
			for (int i = 0; i < transferOrder.getLineItems().size(); i++) {
				// for (int i = 0; i < transferOrder..getLineItems().size(); i++) {
				String transferOrderNumber = getNumber();
				System.out.println(transferOrderNumber);
				System.out.println("-----------------------------------------------");
				String[] arrOfStr = transferOrderNumber.split("\"");
				String final_arr = Arrays.toString(arrOfStr);
				String final_string = final_arr.substring(2, final_arr.length() - 1);
				String order_number = final_string.substring(1, 4) + final_string.substring(9, final_string.length());
				System.out.println(order_number);
				String url = "https://inventory.zoho.in/api/v1/transferorders?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
				System.out.println(url);
				System.out.println("------------------------------------------------");
				HttpClient client = HttpClientBuilder.create().build();
				JSONObject transferOrderRequest = new JSONObject();
				JSONArray arr = new JSONArray();
				transferOrderRequest.put("transfer_order_number", order_number);
				transferOrderRequest.put("date", transferOrder.getLineItems().get(i).getDate());
				transferOrderRequest.put("from_warehouse_id", transferOrder.getLineItems().get(i).getFromWarehouseId());
				transferOrderRequest.put("to_warehouse_id", transferOrder.getLineItems().get(i).getToWarehouseId());
				transferOrderRequest.put("is_intransit_order", true);

				JSONObject line_items = new JSONObject();
				line_items.put("item_id", transferOrder.getLineItems().get(i).getItemId());
				line_items.put("name", transferOrder.getLineItems().get(i).getName());
				line_items.put("quantity_transfer", transferOrder.getLineItems().get(i).getQuantity_transfer());
				line_items.put("unit", transferOrder.getLineItems().get(i).getUnit());
				arr.put(line_items);

				System.out.println(arr);
				transferOrderRequest.put("line_items", arr);
				System.out.println(transferOrderRequest);
				url += "&JSONString=";
				url += URLEncoder.encode(transferOrderRequest.toJSONString(), "UTF-8");
				System.out.println(url);

				HttpPost post = new HttpPost(url);
				post.addHeader("Accept", "application/json");
				post.addHeader("charset", "UTF-8");
				post.addHeader("Content-Type", "application/json");

				HttpResponse zohoresponse = client.execute(post);
				System.out.println(zohoresponse.getStatusLine());
				response.setResponseCode("201");
				response.setResponseMessage("Transfer Order added successfully.");
				System.out.println("Response Code : " + zohoresponse);

				BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));

				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				System.out.println("========");
				System.out.println(result);
				System.out.println("========");

				// return result;
			}
		}
		return result;

	}

	private String getNumber() throws IOException {
		System.out.println("GET NUMBER METHODDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
		String number = null;
		Response response = new Response();
		response.setResponseCode("401");
		response.setResponseMessage("Getting transfer number was failed!");
		String url = "https://inventory.zoho.in/api/v1/settings/transferorders?authtoken=989ceabb048543fba0b801dd182839ce&organization_id=60001846249";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept", "application/json");
		get.addHeader("charset", "UTF-8");
		get.addHeader("Content-Type", "application/json");

		HttpResponse zohoresponse = client.execute(get);
		System.out.println(zohoresponse.getStatusLine());
		System.out.println("Response Code : " + zohoresponse);
		BufferedReader rd = new BufferedReader(new InputStreamReader(zohoresponse.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		System.out.println(result);
		System.out.println("TTTTTTTTTTTTOOOOOOOOOOOOOOSSSSSSSSTTTTTTTTTTTTTTTTTTTTTTTTTTT");
		System.out.println(result.toString());
		JsonElement jelement = new JsonParser().parse(result.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		if (zohoresponse.getStatusLine().getStatusCode() == 200) {
			response.setResponseCode("200");
			JsonElement prefix = jobject.get("transferorder_settings" + "").getAsJsonObject().get("prefix_string");
			JsonElement next_number = jobject.get("transferorder_settings" + "").getAsJsonObject().get("next_number");
			number = prefix + " " + next_number;
			System.out.println(number);
		} else if (zohoresponse.getStatusLine().getStatusCode() == 400) {
			response.setResponseCode("400");
			response.setResponseMessage(jobject.get("message").getAsString());
		}
		System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
		System.out.println(number);
		return number;
	}

	@SuppressWarnings("unused")
	private int check(long vendorId) throws IOException {
		System.out.println("in check function............................................");
		String number = null;
		Response response = new Response();
		DBConnectionRest dbConnection = null;
		Connection conn = null;
		PreparedStatement pstmtPjctRsn = null;
		ResultSet row;
		int roleid = 0;
		try {
			dbConnection = new DBConnectionRest();
			conn = dbConnection.getConnect();
			String query = "select roleid from customerinfo where vendorid=" + vendorId;
			System.out.println(query);

			pstmtPjctRsn = conn.prepareStatement(query);
			row = pstmtPjctRsn.executeQuery();
			while (row.next()) {
				System.out.println("----------------------");
				System.out.println(row.getInt("roleid"));
				roleid = row.getInt("roleid");
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			response.setResponseMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionRest.closeQuietly(pstmtPjctRsn);
			DBConnectionRest.closeQuietly(conn);
		}
		System.out.println(roleid);
		return roleid;
	}
}
