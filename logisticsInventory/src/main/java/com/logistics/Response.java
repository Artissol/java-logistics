package com.logistics;

import java.util.ArrayList;

public class Response {
	private int status;
	private String message;
	private Object object;
	private String responseCode;
	private String responseMessage;
	private ArrayList<String> responseMessages = new ArrayList<String>();
	
	public Response() {
		super();
	}
	
	public Response(String responseCode,String responseMessage){
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * @return the responseMessages
	 */
	public ArrayList<String> getResponseMessages() {
		return responseMessages;
	}

	/**
	 * @param responseMessages the responseMessages to set
	 */
	public void setResponseMessages(ArrayList<String> responseMessages) {
		this.responseMessages = responseMessages;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + ", object=" + object + "]";
	}

}
