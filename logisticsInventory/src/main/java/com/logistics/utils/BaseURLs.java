package com.logistics.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class BaseURLs {
	private Properties props;
	private static BaseURLs instance; 
	private static final String PROPERTIES_FILE = System.getProperty(APIConstants.LOGISTIC_PROPERTIES);
	private Map<String,String> blPropertyMap = new HashMap<String,String>();
	 

	public Map<String, String> getBlPropertyMap() {
		return blPropertyMap;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public static String getPropertiesFile() {
		return PROPERTIES_FILE;
	}

	public static void setInstance(BaseURLs instance) {
		BaseURLs.instance = instance;
	}

	static synchronized public BaseURLs getInstance() {
		if (instance == null) {
			instance = new BaseURLs();
		}

		return instance;
	}

	
	private BaseURLs() {
		reloadProperties();
	}

	
	private void reloadProperties() {
		System.out.println("[BaseURLs.reloadProperties()] - Enter.");

		props = new Properties();
		InputStream in;

		try {
			in = new FileInputStream(PROPERTIES_FILE);
			if (null != in) {
				props.load(in);
			}
			in.close();
			System.out.println("[BaseURLs.reloadProperties()] - Past reading Properties file.");

			getBlPropertyMap().put(APIConstants.ENV_KEY, props.getProperty(APIConstants.ENV_KEY));
			getBlPropertyMap().put(APIConstants.SCHEDULE_JOB, props.getProperty(APIConstants.SCHEDULE_JOB));
			getBlPropertyMap().put(APIConstants.CANCEL_REQUEST, props.getProperty(APIConstants.CANCEL_REQUEST));
			
			 
			System.out.println("[BaseURLs.reloadProperties()] - Past setting class URLs.");

		} catch (IOException e) {
			System.err.println("[BaseURLs.reloadProperties()] - Error: '" + e + "'");
			System.err.println("wic_prop.properties configuration is missing. Please verify "
					+ "whether you have configured properties file name : wic_prop.properties in JBoss");
		}
	}

}
