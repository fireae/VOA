package com.fireae.voa.adapter;

public class CategoryGridItem {
	private String title;
	private int index;

	public CategoryGridItem() {
		this.title = "voa";
	}
	
	public CategoryGridItem(String title) {
		super();
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
		
}
