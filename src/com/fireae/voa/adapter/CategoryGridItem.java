package com.fireae.voa.adapter;

public class CategoryGridItem {
	private String categoryTitle;
	private int index;
	private String categoryBaseUrl;

	public CategoryGridItem() {
		this.categoryTitle = "voa";
	}
	
	public CategoryGridItem(String title) {
		super();
		this.categoryTitle = title;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCategoryBaseUrl() {
		return categoryBaseUrl;
	}

	public void setCategoryBaseUrl(String categoryBaseUrl) {
		this.categoryBaseUrl = categoryBaseUrl;
	}

	
}
