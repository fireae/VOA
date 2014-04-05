package com.fireae.voa.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fireae.voa.*;
import com.fireae.voa.activity.ContentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ContentListItemAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<ContentListItem> contentList;
	private LayoutInflater inflater;
	
	public ContentListItemAdapter(Context context) {
		super();
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);	
	} 
	
	public ContentListItemAdapter(Context context, List<ContentListItem>  contentList) {
		super();
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.contentList = new ArrayList<ContentListItem>();
		
		this.contentList.addAll(contentList);
		
	}
	
	
	
	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public List<ContentListItem> getContentList() {
		return contentList;
	}

	public void setContentList(List<ContentListItem> contentList) {
		this.contentList = new ArrayList<ContentListItem>();
		this.contentList.addAll(contentList);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("content list size Is : ");
		System.out.println(contentList.size());
		return this.contentList.size();
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return contentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderContentListItem viewHolder;
		
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.content_list_item, null);
			
			viewHolder = new ViewHolderContentListItem();
			viewHolder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitle);
			viewHolder.imgViewLrc = (ImageView)convertView.findViewById(R.id.imgViewLrc);
			viewHolder.imgViewTranslate = (ImageView)convertView.findViewById(R.id.imgViewTranslate);
			viewHolder.txtDate = (TextView)convertView.findViewById(R.id.txtDate);
			
			viewHolder.imgViewLrc.setVisibility(View.INVISIBLE);
			viewHolder.imgViewTranslate.setVisibility(View.INVISIBLE);
			
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolderContentListItem) convertView.getTag();
		}
		
		// 设置控件内容
		final ContentListItem item = contentList.get(position);
		viewHolder.txtTitle.setText(item.getTitle());
		viewHolder.txtDate.setText(item.getDate());
		if (item.isLrc()) {
			viewHolder.imgViewLrc.setVisibility(View.VISIBLE);
		}
		if (item.isTanslate()) {
			viewHolder.imgViewTranslate.setVisibility(View.VISIBLE);
		}
		
		viewHolder.txtTitle.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, ContentActivity.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("contentUrl", item.getContentBaseUrl());
				bundle.putString("title", item.getTitle());
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}});
		
		
		return convertView;
	}

	
	static class ViewHolderContentListItem {
		TextView txtTitle;
		ImageView imgViewLrc;
		ImageView imgViewTranslate;
		TextView txtDate;
	}
}
