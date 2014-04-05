package com.fireae.voa.adapter;

public class ContentListItem {

	private String title; 				//类别标题
	private String contentBaseUrl;		//类别对应url
	private boolean isLrc;
	private boolean isTanslate;
	private String date;
	
	public ContentListItem() {
		super();
		this.title = "voa";
		this.isLrc = false;
		this.isTanslate = false;
	}

	public ContentListItem(String title) {
		super();
		this.title = title;
		this.isLrc = false;
		this.isTanslate = false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContentBaseUrl() {
		return contentBaseUrl;
	}

	public void setContentBaseUrl(String contentBaseUrl) {
		this.contentBaseUrl = contentBaseUrl;
	}

	public boolean isLrc() {
		return isLrc;
	}

	public void setLrc(boolean isLrc) {
		this.isLrc = isLrc;
	}

	public boolean isTanslate() {
		return isTanslate;
	}

	public void setTanslate(boolean isTanslate) {
		this.isTanslate = isTanslate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
}
