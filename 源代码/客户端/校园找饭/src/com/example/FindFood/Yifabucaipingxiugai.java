package com.example.FindFood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Yifabucaipingxiugai extends Activity{
	private Object detail;
	String title = new String();
	private Handler handler;
	String address = new String();
	String idDis = new String();
	String phone = new String();
	private String[] info = new String [50];
	private String[] goodsName = new String [50];
	private String[] price = new String [50];
	private String[] id = new String [50];
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private ListView listView;
	private SimpleAdapter simpleAdapter=null;  
	private MyApplication app;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caipingxiugai);
		app = (MyApplication)getApplication();
		idDis = app.getSellerName();
		try {
			getInfo(idDis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listView.setOnItemClickListener(new OnItemClickListener(){
			public void  onItemClick(AdapterView<?>listView,View view,int position,long id)
			{
					
				Map<String, String>  map = (Map<String, String>) listView.getItemAtPosition(position);
				String titleItem = map.get("title");
				String price = map.get("price");
				String info = map.get("info");
				String ids = map.get("id");
				
				Intent intent = new Intent(Yifabucaipingxiugai.this,Changedetail.class);
				Bundle bundle =  new Bundle();
				bundle.putString("titleItem", titleItem);
				bundle.putString("price", price);
				bundle.putString("info", info);
				bundle.putString("address", address);
				bundle.putString("title", title);
				bundle.putString("phone", phone);
				bundle.putString("id", ids);
				intent.putExtras(bundle);
				startActivity(intent);
				}
				
			
			});
		
		
	}
	
	public void getInfo(final Object s) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
				app = (MyApplication)getApplication();
			 
				Log.i("SELLER",(String) s);
				
        // �����ռ�  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // ���õķ�������  

        String methodName = "resinfo";  
       
        // EndPoint  

        
        
        
       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
        rpc.addProperty("Seller", s);  
        
        
       
  

        // ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾  

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        
  

        envelope.bodyOut = rpc;  
        Log.i("9","9");
        // �����Ƿ���õ���dotNet������WebService  

        envelope.dotNet = true;  
        Log.i("10","10");
        // �ȼ���envelope.bodyOut = rpc;  

        envelope.setOutputSoapObject(rpc);  
        
        
        

        HttpTransportSE transport = new HttpTransportSE(endPoint);  
       


        try {  

           // ����WebService  
        	

            transport.call(null, envelope);  

        } catch (Exception e) {  

            e.printStackTrace();  
      }  

  
        
      // ��ȡ���ص�����  

        
		
			try {
				detail = (Object) envelope.getResponse();
			} catch (SoapFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				finish();
			}
		
        Log.i("14",detail.toString());
        
	
        try {
			JSONObject  dataJson=new JSONObject(detail.toString());
			
			
			for(int i = 0;i < Integer.parseInt(""+dataJson.get("count")) ;i++)
			{	
				id[i] = (String) dataJson.getJSONObject(""+i).get("id");
				goodsName[i] = (String) dataJson.getJSONObject(""+i).get("ac");
				Log.i("title",(String) dataJson.getJSONObject(""+i).get("ac"));
				if(dataJson.getJSONObject(""+i).get("price") != null)
					{price[i] = (String) dataJson.getJSONObject(""+i).get("price");
				Log.i("price",(String) dataJson.getJSONObject(""+i).get("price"));}
				if(dataJson.getJSONObject(""+i).getString("disc") != null)
					{info[i] = (String) dataJson.getJSONObject(""+i).get("disc");
				Log.i("address",(String) dataJson.getJSONObject(""+i).get("disc"));}
				//image[i] = R.drawable.qianyue;
			}
			
			for (int x = 0; x < Integer.parseInt(""+dataJson.get("count")) ; x++) {
				Map<String, String> map = new HashMap<String, String>();// ����map����
				//map.put("imageItem", String.valueOf(this.pic[x]));
				map.put("id", id[x]);
				map.put("title",  goodsName[x]);
				
				map.put("price", price[x]);
				
				//map.put("image", String.valueOf(image[x]));

				map.put("info", info[x]);
				
				list.add(map);
				
				}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    			
        
  

         

        

        Log.i("15","15");

			}
			};
		webserviceThread.start();               
		webserviceThread.join();
		listView = (ListView)findViewById(R.id.xiugaiitem);
		this.simpleAdapter = new SimpleAdapter(this,
				list,
				R.layout.list_item,
				new String[]{"image","title","info","price","distance"},
				new int[]{R.id.imageItem,R.id.titleItem,R.id.infoItem,R.id.priceItem,R.id.shouchuItem});
		Log.i("16","16");
		listView.post(new Runnable() {//����һ�ָ����ķ�����Ϣ��ui�̵߳ķ�����
			           
			             @Override
			               public void run() {//run()��������ui�߳�ִ��
			            	 simpleAdapter.notifyDataSetChanged();   //����֪ͨui�Ѹı�
			            	 listView.setAdapter(simpleAdapter);
			            	 
			              }
			           });
		
		//Message msg = new Message();
		//msg.what = 1;
		//handler.sendMessage(msg);
		
		
		
	}
}
