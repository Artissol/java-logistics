package com.logistics.request.constants;

import org.springframework.beans.factory.annotation.Required;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	private String contact_name;
	private String company_name;
	// private int payment_terms;
	// private long currency_id;
	// private String website;
	// @JsonIgnore
	// private BillingAddress billing_address;
	// @JsonIgnore
	// private ShippingAddress shipping_address;
	// private String language_code;
	// private String notes;
	// private long tax_exemption_id;
	// private long tax_authority_id;
	// private long tax_id;
	// private boolean is_taxable;
	// private String facebook;
	// private String twitter;
	// private String place_of_contact;
	// private String gst_no;
	// private String gst_treatment;
	private String first_name;
	private String email;
	private String phone;

//	public ContactDetails getContactDetails() {
//		return contact_persons;
//	}
//
//	public void setContactDetails(ContactDetails contactDetails) {
//		this.contact_persons = contactDetails;
//	}

	public String getContact_name() {
		return contact_name;
	}

	public String getFirst_name() {
		return first_name;
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

	@Required
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	@Override
	public String toString() {
		return "User [contact_name=" + contact_name + ", company_name=" + company_name + ", first_name=" + first_name
				+ ", email=" + email + ", phone=" + phone + "]";
	}

	

	/*
	 * public int getPayment_terms() { return payment_terms; }
	 * 
	 * public void setPayment_terms(int payment_terms) { this.payment_terms =
	 * payment_terms; }
	 * 
	 * public long getCurrency_id() { return currency_id; }
	 * 
	 * public void setCurrency_id(int currency_id) { this.currency_id = currency_id;
	 * }
	 * 
	 * public String getWebsite() { return website; }
	 * 
	 * public void setWebsite(String website) { this.website = website; }
	 */

	/*
	 * public BillingAddress getBilling_address() { return billing_address; }
	 * 
	 * @JsonIgnore public void setBilling_address(BillingAddress billing_address) {
	 * this.billing_address = billing_address; }
	 * 
	 * public ShippingAddress getShipping_address() { return shipping_address; }
	 * 
	 * @JsonIgnore public void setShipping_address(ShippingAddress shipping_address)
	 * { this.shipping_address = shipping_address; }
	 * 
	 * public String getLanguage_code() { return language_code; }
	 * 
	 * public void setLanguage_code(String language_code) { this.language_code =
	 * language_code; }
	 * 
	 * public String getNotes() { return notes; }
	 * 
	 * public void setNotes(String notes) { this.notes = notes; }
	 * 
	 * public long getTax_exemption_id() { return tax_exemption_id; }
	 * 
	 * public void setTax_exemption_id(long tax_exemption_id) {
	 * this.tax_exemption_id = tax_exemption_id; }
	 * 
	 * public long getTax_authority_id() { return tax_authority_id; }
	 * 
	 * public void setTax_authority_id(long tax_authority_id) {
	 * this.tax_authority_id = tax_authority_id; }
	 * 
	 * public long getTax_id() { return tax_id; }
	 * 
	 * public void setTax_id(long tax_id) { this.tax_id = tax_id; }
	 * 
	 * public boolean isIs_taxable() { return is_taxable; }
	 * 
	 * public void setIs_taxable(boolean is_taxable) { this.is_taxable = is_taxable;
	 * }
	 * 
	 * public String getFacebook() { return facebook; }
	 * 
	 * public void setFacebook(String facebook) { this.facebook = facebook; }
	 * 
	 * public String getTwitter() { return twitter; }
	 * 
	 * public void setTwitter(String twitter) { this.twitter = twitter; }
	 * 
	 * public String getPlace_of_contact() { return place_of_contact; }
	 * 
	 * public void setPlace_of_contact(String place_of_contact) {
	 * this.place_of_contact = place_of_contact; }
	 * 
	 * public String getGst_no() { return gst_no; }
	 * 
	 * public void setGst_no(String gst_no) { this.gst_no = gst_no; }
	 * 
	 * public String getGst_treatment() { return gst_treatment; }
	 * 
	 * public void setGst_treatment(String gst_treatment) { this.gst_treatment =
	 * gst_treatment; }
	 * 
	 * @Override public String toString() { return "User [contact_name=" +
	 * contact_name + ", company_name=" + company_name + ", payment_terms=" +
	 * payment_terms + ", currency_id=" + currency_id + ", website=" + website +
	 * ", billing_address=" + billing_address + ", shipping_address=" +
	 * shipping_address + ", language_code=" + language_code + ", notes=" + notes +
	 * ", tax_exemption_id=" + tax_exemption_id + ", tax_authority_id=" +
	 * tax_authority_id + ", tax_id=" + tax_id + ", is_taxable=" + is_taxable +
	 * ", facebook=" + facebook + ", twitter=" + twitter + ", place_of_contact=" +
	 * place_of_contact + ", gst_no=" + gst_no + ", gst_treatment=" + gst_treatment
	 * + "]"; }
	 */

}
