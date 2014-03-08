package com.fireae.voa.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
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
			viewHolder.txtCategoryTitle = (TextView)convertView.findViewById(R.id.txtCategoryTitle);
			viewHolder.imgViewBG = (ImageView)convertView.findViewById(R.id.imgViewBG);
			
			//convertView.setBackgroundResource(R.drawable.bg_01);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.txtCategoryTitle.setText(gridItemList.get(position).getTitle());
		viewHolder.txtCategoryTitle.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "voa1", Toast.LENGTH_LONG).show();
			}
			
		});
		
		return convertView;
	}

}
