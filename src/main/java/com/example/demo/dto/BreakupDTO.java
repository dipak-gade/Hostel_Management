package com.example.demo.dto;

public class BreakupDTO {

	private String userName;
	private int duration;

	private String orgName;
	private String hostelName;
	private String hostelAdd;
	private int floorNo;

	private int roomNo;
	private int sharing;
	private String roomType;

	private int bedId;
	private int bedPrice;
	
	private int finalPrice;

	public int getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public int getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public String getHostelAdd() {
		return hostelAdd;
	}

	public void setHostelAdd(String hostelAdd) {
		this.hostelAdd = hostelAdd;
	}

	public int getSharing() {
		return sharing;
	}

	public void setSharing(int sharing) {
		this.sharing = sharing;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getBedId() {
		return bedId;
	}

	public void setBedId(int bedId) {
		this.bedId = bedId;
	}

	public int getBedPrice() {
		return bedPrice;
	}

	public void setBedPrice(int bedPrice) {
		this.bedPrice = bedPrice;
	}

}
