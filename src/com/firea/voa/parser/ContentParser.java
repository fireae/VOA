package com.firea.voa.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ContentParser {

	private String contentUrl;

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	
	public ContentData getContentData() {
		ContentData contentData  = new ContentData();
		
		try {
			Document doc = Jsoup.connect(contentUrl).get();
			Element mp3 = doc.getElementById("mp3");
			System.out.println(mp3.absUrl("href"));
			
			Element content = doc.getElementById("content");
			System.out.println(content.text());
			//contentData.setContent(content.text());
			contentData.setContent(content.html());
			contentData.setVoiceUrl(mp3.absUrl("href"));
			
			Element lrc = doc.getElementById("lrc");
			if (lrc != null) {
				System.out.println(lrc.absUrl("href"));
				contentData.setLrcUrl(lrc.absUrl("href"));
			}
			
			Element enPage = doc.getElementById("EnPage");
			if (enPage != null) {
				System.out.println(enPage.absUrl("href"));
				contentData.setTranslateUrl(enPage.absUrl("href"));
			}
			


			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contentData;
	}
	
}
