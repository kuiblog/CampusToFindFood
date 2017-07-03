package com.example.FindFood;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class SearchList extends Activity{
	String address = new String();
	String idDis = new String();
	String phone = new String();
	private String[] info = new String [50];
	private String[] goodsName = new String [50];
	private String[] price = new String [50];
	private String[] ids = new String [50];
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private ListView listView;
	private SimpleAdapter simpleAdapter=null;  
	private MyApplication app;
	private Bitmap[] bitmap = new Bitmap[50];
	private String[] selphone = new String[50];
	private String[] sellerName = new String[50];
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myitem);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String detail = bundle.getString("detail");
		TextView title = (TextView)findViewById(R.id.searchtext);
		title.setText("搜索结果");
		
		try {
			JSONObject dataJson = new JSONObject(detail);
			bitmap = new Bitmap[50];
			
			for(int i = 0;i < Integer.parseInt(""+dataJson.get("count")) ;i++)
			{	
				selphone[i] = (String) dataJson.getJSONObject(""+i).get("selphone");
				sellerName[i] = (String) dataJson.getJSONObject(""+i).get("seller");
				ids[i] = (String) dataJson.getJSONObject(""+i).get("id");
				goodsName[i] = (String) dataJson.getJSONObject(""+i).get("ac");
				Log.i("title",(String) dataJson.getJSONObject(""+i).get("ac"));
				if(dataJson.getJSONObject(""+i).get("price") != null)
					{price[i] = "价格"+(String) dataJson.getJSONObject(""+i).get("price");
				Log.i("price",(String) dataJson.getJSONObject(""+i).get("price"));}
				if(dataJson.getJSONObject(""+i).getString("disc") != null)
					{info[i] = (String) dataJson.getJSONObject(""+i).get("disc");
				Log.i("address",(String) dataJson.getJSONObject(""+i).get("disc"));}
				byte[] buffer = new BASE64Decoder(null).decodeReturnByte(( String) dataJson.getJSONObject(""+i).get("disphoto"));
		         //对android传过来的图片字符串进行解码   
		        bitmap[i]=BitmapFactory.decodeByteArray(buffer,0,buffer.length);
				//image[i] = R.drawable.qianyue;
			}
			
			for (int x = 0; x < Integer.parseInt(""+dataJson.get("count")) ; x++) {
				Map<String, Object> map = new HashMap<String,Object>();// 定义map集合
				//map.put("imageItem", String.valueOf(this.pic[x]));
				
				map.put("title",  goodsName[x]);
				map.put("sellerName", sellerName[x]);
				map.put("phone",selphone[x]);
				map.put("price", price[x]);
				map.put("id", ids[x]);
				//map.put("image", String.valueOf(image[x]));

				map.put("info", info[x]);
				map.put("image",bitmap[x]);
				list.add(map);
				
				}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView = (ListView)findViewById(R.id.myitem);
		this.simpleAdapter = new SimpleAdapter(this,
				list,
				R.layout.list_item,
				new String[]{"image","title","info","price","distance"},
				new int[]{R.id.imageItem,R.id.titleItem,R.id.infoItem,R.id.priceItem,R.id.shouchuItem});
		this.simpleAdapter.setViewBinder(new ViewBinder() 
		{   public boolean setViewValue(View view, Object data,   String textRepresentation) 
		{   //判断是否为我们要处理的对象   
			if(view instanceof ImageView && data instanceof Bitmap)
			{   ImageView iv = (ImageView) view;   
			iv.setImageBitmap((Bitmap) data);   
			return true;   
			}
			else   
				return false;   
			}   
		});
		listView.post(new Runnable() {//另外一种更简洁的发送消息给ui线程的方法。
			           
			             @Override
			               public void run() {//run()方法会在ui线程执行
			            	 simpleAdapter.notifyDataSetChanged();   //必须通知ui已改变
			            	 listView.setAdapter(simpleAdapter);
			            	 
			              }
			           });
		listView.setOnItemClickListener(new OnItemClickListener(){
			public void  onItemClick(AdapterView<?>listView,View view,int position,long id)
			{
				
					
				
				Map<String, Object>  map = (Map<String, Object>) listView.getItemAtPosition(position);
				String titleItem = (String) map.get("title");
				String price = (String) map.get("price");
				String info = (String) map.get("info");
				String idss = (String) map.get("id");
				String phone = (String)map.get("phone");
				String sellerNameL = (String)map.get("sellerName");
				Bitmap bitmap = (Bitmap) map.get("image"); 
				byte[] by = Bitmap2Bytes(bitmap);
	            String imagebase64 =Base64.encode(by);
				Intent intent = new Intent(SearchList.this,BuyActivity.class);
				Bundle bundle =  new Bundle();
				bundle.putString("titleItem", titleItem);
				bundle.putString("price", price);
				bundle.putString("info", info);
				bundle.putString("address",sellerNameL);  
				bundle.putString("phone", phone);
				
				
				bundle.putString("id", idss);
				bundle.putString("base64", imagebase64);
				
				intent.putExtras(bundle);
				startActivity(intent);
				
				
			}
			});
		
		
		
		
	}
	//bitmap-->byte[]
		public byte[] Bitmap2Bytes(Bitmap bm) {
			         ByteArrayOutputStream baos = new ByteArrayOutputStream();
			         bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			         return baos.toByteArray();
			     }
	
}
