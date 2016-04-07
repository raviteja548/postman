package com.tmo.postman.model;

public class RowData {
	private String minOcc;
	private String maxOcc;
	private String typ;
	private String xpath;
	private String description;
	private String enumString;
	private String lastElement;
	private String previousElement;
	
	public RowData() {
	
	}
	public RowData(String minOcc, String maxOcc, String typ, String xpath) {
		super();
		this.minOcc = minOcc;
		this.maxOcc = maxOcc;
		this.typ = typ;
		this.xpath = xpath;
		
		
		
		
		
	}
	public RowData(String minOcc, String maxOcc, String typ, String xpath,
			String discription, String enumString) {
		super();
		this.minOcc = minOcc;
		this.maxOcc = maxOcc;
		this.typ = typ;
		this.xpath = xpath;
		this.description = discription;
		this.enumString = enumString;
	}
	
	public String getMinOcc() {
		return minOcc;
	}
	public void setMinOcc(String minOcc) {
		this.minOcc = minOcc;
	}
	public String getMaxOcc() {
		return maxOcc;
	}
	public void setMaxOcc(String maxOcc) {
		this.maxOcc = maxOcc;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public String getDiscription() {
		return description;
	}
	public void setDiscription(String discription) {
		this.description = discription;
	}
	public String getEnumString() {
		return enumString;
	}
	public void setEnumString(String enumString) {
		this.enumString = enumString;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLastElement() {
		return lastElement;
	}
	public void setLastElement(String lastElement) {
		this.lastElement = lastElement;
	}
	public String getPreviousElement() {
		return previousElement;
	}
	public void setPreviousElement(String previousElement) {
		this.previousElement = previousElement;
	}

	
	public String toString(){
		return xpath;
	}
}