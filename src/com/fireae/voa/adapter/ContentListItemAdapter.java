package com.fireae.voa.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fireae.voa.*;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ContentListItemAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<ContentListItem> contentList;
	private LayoutInflater inflater;
	
	public ContentListItemAdapter(Context context, List<ContentListItem>  contentList) {
		super();
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
		this.contentList = new ArrayList<ContentListItem>();
		for (int i = 0; i < 3; i++) {
			ContentListItem item = new ContentListItem();
			item.setTitle("this is a test");
			this.contentList.add(item);
		}
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("content list size Is : ");
		System.out.println(contentList.size());
		return 3;
		
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
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolderContentListItem) convertView.getTag();
		}
		
		viewHolder.txtTitle.setText(contentList.get(position).getTitle());
		return convertView;
	}

	
	static class ViewHolderContentListItem {
		TextView txtTitle;
		ImageView imgViewLrc;
		ImageView imgViewTranslate;
	}
}
