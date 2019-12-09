package com.logistics.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;

import com.logistics.dao.RequestDAO;
import com.logistics.email.notification.SendEmail;
import com.logistics.request.constants.User;

public class Test {

	public static void main(String[] args) throws IOException {
		// SendEmail sendEmail = new SendEmail();
		// sendEmail.sendEmail("snakirikanti@artissol.com",
		// "svallurupalli@artissol.com", "snakirikanti@artissol.com", "Test", "Test",
		// null);
		RequestDAO requestDAO = new RequestDAO();
		JSONArray arr = new JSONArray();
//		List<User> user = new ArrayList<User>();
		User user = new User();
		
		arr.put("{\n" + "		    \"contact_name\": \"Soujanya N\",\n" + "		    \"company_name\":\"hghh\",\n"
				+ "		    \"contact_persons\":{\n" + "		    	\"first_name\":\"Soujanya\",\n"
				+ "		    	\"email\":\"snakdfh@jdncx.dc\",\n" + "		    	\"phone\":\"1234567890\"\n"
				+ "		    }\n" + "		}");
		

//		requestDAO.addZohoUser(user);
	}

}
