package com.example.FindFood;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private boolean[] hasChecked; // 记录商品选中状态

	public final class ListItemView { // 自定义控件集合
		private ImageView image;
		private TextView titleItem;
		private TextView infoItem;
		private TextView priceItem;
		private TextView shouchuItem;
	}

	public ListViewAdapter(Context context, List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		hasChecked = new boolean[getCount()];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void checkedChange(int checkedID) {
		hasChecked[checkedID] = !hasChecked[checkedID];
	}

	public boolean hasChecked(int checkedID) {
		return hasChecked[checkedID];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int selectID = position;
		//自定义视图
		ListItemView listItemView =null;
		if(convertView ==null){
			listItemView= new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.list_item, null);
			//获取控件对象
			listItemView.image=(ImageView) convertView
					.findViewById(R.id.imageItem);
			listItemView.titleItem=(TextView) convertView
					.findViewById(R.id.titleItem);
			listItemView.infoItem=(TextView) convertView
					.findViewById(R.id.infoItem);
			listItemView.priceItem=(TextView) convertView
					.findViewById(R.id.priceItem);
			listItemView.shouchuItem=(TextView) convertView
					.findViewById(R.id.shouchuItem);
			// 设置控件集到convertView
						convertView.setTag(listItemView);
		}
		else {
			listItemView = (ListItemView) convertView.getTag();
		}
		return convertView;
	}

}
