package com.firea.voa.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fireae.voa.adapter.ContentListItem;
import com.fireae.voa.data.VOAData;

public class CategoryParser {

	private String categoryBaseUrl;
	private int categoryPage;
	
	public CategoryParser() {
		super();
		this.categoryBaseUrl = "";
		this.categoryPage = 1;
	}
	
	public CategoryParser(String categoryBaseUrl) {
		super();
		this.categoryBaseUrl = categoryBaseUrl;
		this.categoryPage = 1;
	}

	public String getCategoryBaseUrl() {
		return categoryBaseUrl;
	}

	public void setCategoryBaseUrl(String categoryBaseUrl) {
		this.categoryBaseUrl = categoryBaseUrl;
	}
	
	public int getCategoryPage() {
		return categoryPage;
	}

	public void setCategoryPage(int categoryPage) {
		this.categoryPage = categoryPage;
	}

	public List<ContentListItem> getContentListItem(){
		List<ContentListItem> itemLists = new ArrayList<ContentListItem>();
		
		/* 要解析的格式
		 * <div id="list">
		 * <ul>
		 * <li><img src=/images/lrc.gif> 
		 * <img src=/images/yi.gif> 
		 * <a href="/VOA_Special_English/study-suggests-video-games-might-help-dyslexics-55103.html" target=_blank>
		 * Study Suggests Video Games Might Help Dyslexics</a> (14-3-3)
		 * </li>
		 * </ul>
		 * </div>
		 */
		
		// 组装url
		String categoryUrl = VOAData.voaBaseUrl + categoryBaseUrl + Integer.toString(categoryPage) + ".html";
		try {
			
			// 取document
			Document doc = Jsoup.connect(categoryUrl).get();
			
			// 解析 <div id="list">
			Element list = doc.getElementById("list");
			// 解析 <ul> </ul>
			Elements uls = list.getElementsByTag("ul");
			for (Element ul : uls ) {
				Elements lis = ul.getElementsByTag("li");
				for (Element li : lis) {
					ContentListItem contentItem = new ContentListItem();
					//取时间
					String liText = li.text();
					String date = (String) liText.subSequence(liText.indexOf('('), liText.indexOf(')')+1);
					contentItem.setDate(date);
					
					// 取href 和标题
					Elements as = li.getElementsByTag("a");
					for (Element a : as) {
						System.out.println(a.absUrl("href"));
						System.out.println(a.text());
						contentItem.setTitle(a.text());
						contentItem.setContentBaseUrl(a.absUrl("href"));
					}
					
					// 判断是不是有字幕和翻译
					Elements imgs = li.getElementsByTag("img");
					for (Element img : imgs) {
						System.out.println(img.absUrl("src"));
						String srcContent = img.absUrl("src");
						if (srcContent.contains("lrc")) {
							contentItem.setLrc(true);
						}
						if (srcContent.contains("yi")) {
							contentItem.setTanslate(true);
						}
					}
					
					itemLists.add(contentItem);
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return itemLists;
	}
	
}
