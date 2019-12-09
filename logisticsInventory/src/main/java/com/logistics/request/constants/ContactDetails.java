package com.logistics.request.constants;

public class ContactDetails {
	private String first_name;
	private String email;
	private String phone;

	public String getFirst_name() {
		return first_name;
	}

	@Override
	public String toString() {
		return "{first_name:" + first_name + ", email:" + email + ", phone:" + phone + "}";
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
