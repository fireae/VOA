package com.fireae.voa.activity;

import java.util.ArrayList;
import java.util.List;

import com.firea.voa.parser.CategoryParser;
import com.fireae.voa.R;
import com.fireae.voa.R.layout;
import com.fireae.voa.R.menu;
import com.fireae.voa.adapter.CategoryGridItem;
import com.fireae.voa.adapter.CategoryItemAdapter;
import com.fireae.voa.adapter.ContentListItem;
import com.fireae.voa.adapter.ContentListItemAdapter;
import com.fireae.voa.ui.RefreshableView;
import com.fireae.voa.ui.RefreshableView.PullToRefreshListener;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;

public class ContentListActivity extends Activity {

	private ListView list_content;
	private Handler handler;
	private List<ContentListItem> contentList;
	private String baseUrl;
	private ContentListItemAdapter adapter;
	private RefreshableView refreshableView;
	
	//private ContentListItemAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_content_list);
		refreshableView = (RefreshableView)findViewById(R.id.refreshable_view);
		
		list_content = (ListView)findViewById(R.id.list_content);
		refreshableView.setOnRefreshListener(new PullToRefreshListener(){

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}, 0);
		
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		baseUrl = bundle.getString("baseUrl");
		handler = new Handler();
		adapter = new ContentListItemAdapter(this);
		
		new Thread(new ThreadLoadList()).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content_list, menu);
		return true;
	}

	
	public class ThreadLoadList implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			CategoryParser categoryParser = new CategoryParser(baseUrl);
			categoryParser.setCategoryPage(1);
			contentList = categoryParser.getContentListItem();
			handler.post(new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					adapter.setContentList(contentList);
					list_content.setAdapter(adapter);
				}
				
			}));
			
		}
		
	}
	
}
