package com.firea.voa.parser;

public class ContentData {
	private String title;
	private String content;
	private String voiceUrl;
	private String lrcUrl;
	private String translateUrl;
	
	
	public ContentData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContentData(String title, String content, String voiceUrl,
			String lrcUrl, String translateUrl) {
		super();
		this.title = title;
		this.content = content;
		this.voiceUrl = voiceUrl;
		this.lrcUrl = lrcUrl;
		this.translateUrl = translateUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public String getLrcUrl() {
		return lrcUrl;
	}

	public void setLrcUrl(String lrcUrl) {
		this.lrcUrl = lrcUrl;
	}

	public String getTranslateUrl() {
		return translateUrl;
	}

	public void setTranslateUrl(String translateUrl) {
		this.translateUrl = translateUrl;
	}
	
	
}
