package com.fireae.voa.activity;

import java.util.ArrayList;

import com.fireae.voa.R;
import com.fireae.voa.R.layout;
import com.fireae.voa.R.menu;
import com.fireae.voa.adapter.CategoryGridItem;
import com.fireae.voa.adapter.CategoryItemAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CategoryActivity extends Activity {

	private GridView gridView;
	ArrayList<CategoryGridItem> gridItemList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		gridView = (GridView)findViewById(R.id.category_grid);
		setData();
		
		CategoryItemAdapter adapter = new CategoryItemAdapter(this, gridItemList);
		
		gridView.setAdapter(adapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				//Toast.makeText(CategoryActivity.this, "hello world", Toast.LENGTH_LONG).show();
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}
	
	public void setData() {
		String titles[] = {
				"Technology Report", "This is America", "Agriculture Report",
				"Science in news", "Health Report", "Explorations", "Education Report"
				
		};
		gridItemList = new ArrayList<CategoryGridItem>();
		
		for (int i = 0; i < titles.length; i++) {
			CategoryGridItem item = new CategoryGridItem();
			item.setTitle(titles[i]);
			item.setIndex(i);
			gridItemList.add(item);
		} 
	}

}
