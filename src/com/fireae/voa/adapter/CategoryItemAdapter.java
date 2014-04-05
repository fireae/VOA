package com.fireae.voa.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fireae.*;
import com.fireae.voa.R;
import com.fireae.voa.activity.CategoryActivity;
import com.fireae.voa.activity.ContentListActivity;
import com.fireae.voa.data.ResData;

public class CategoryItemAdapter extends BaseAdapter {

	static class ViewHolder {
		TextView txtCategoryTitle;
		ImageView imgViewBG;
	};
	
	// variables
	private Context mContext;

	private List<CategoryGridItem> gridItemList;
	private LayoutInflater inflater;
	
	public CategoryItemAdapter(Context context, List<CategoryGridItem> list) {
		super();
		mContext = context;
		gridItemList = new ArrayList<CategoryGridItem>();
		inflater = LayoutInflater.from(context);
	
		gridItemList.addAll(list);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != gridItemList) {
			return gridItemList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return gridItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.category_grid_item, null);
			
			viewHolder = new ViewHolder();
			//viewHolder.imgViewBG = (ImageView)convertView.findViewById(R.id.imgViewBG);
			viewHolder.txtCategoryTitle = (TextView)convertView.findViewById(R.id.txtCategoryTitle);
			
			Random rand = new Random(5);
			int iBgColor = rand.nextInt(5);
			System.out.println(iBgColor);
			convertView.setBackgroundResource(ResData.iBgRes[iBgColor]);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.txtCategoryTitle.setText(gridItemList.get(position).getCategoryTitle());
		final String baseUrl = gridItemList.get(position).getCategoryBaseUrl();
		viewHolder.txtCategoryTitle.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, ContentListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("baseUrl", baseUrl);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
			
		});
		
		return convertView;
	}

}
