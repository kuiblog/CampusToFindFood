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
	private List<Map<String, Object>> listItems; // ��Ʒ��Ϣ����
	private LayoutInflater listContainer; // ��ͼ����
	private boolean[] hasChecked; // ��¼��Ʒѡ��״̬

	public final class ListItemView { // �Զ���ؼ�����
		private ImageView image;
		private TextView titleItem;
		private TextView infoItem;
		private TextView priceItem;
		private TextView shouchuItem;
	}

	public ListViewAdapter(Context context, List<Map<String, Object>> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // ������ͼ����������������
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
		//�Զ�����ͼ
		ListItemView listItemView =null;
		if(convertView ==null){
			listItemView= new ListItemView();
			// ��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(R.layout.list_item, null);
			//��ȡ�ؼ�����
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
			// ���ÿؼ�����convertView
						convertView.setTag(listItemView);
		}
		else {
			listItemView = (ListItemView) convertView.getTag();
		}
		return convertView;
	}

}
