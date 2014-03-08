package com.fireae.voa.activity;

import java.util.ArrayList;
import java.util.List;

import com.fireae.voa.R;
import com.fireae.voa.R.layout;
import com.fireae.voa.R.menu;
import com.fireae.voa.adapter.CategoryGridItem;
import com.fireae.voa.adapter.CategoryItemAdapter;
import com.fireae.voa.adapter.ContentListItem;
import com.fireae.voa.adapter.ContentListItemAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class ContentListActivity extends Activity {

	private ListView list_content;
	//private ContentListItemAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_list);
		
		list_content = (ListView)findViewById(R.id.list_content);
		
		List<ContentListItem> contentList = new ArrayList<ContentListItem>();
		for (int i = 0; i < 3; i++) {
			ContentListItem item = new ContentListItem();
			item.setTitle("this is a test");
			contentList.add(item);
		}
		List<ContentListItem> l = new ArrayList<ContentListItem>();
		ContentListItemAdapter adapter = new ContentListItemAdapter(this, l);
		list_content.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content_list, menu);
		return true;
	}

}
