package com.example.FindFood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyItem extends Activity{
	private ListView listView;
	private SimpleAdapter simpleAdapter=null;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private int num = 0;
	private Object detail;
	private String infoR ="test";
	private TextView tx;
	private MyApplication app;
	private List<Map<String, Object>> listItems;
	private String[] date = new String [50];
	private String[] goodsName = new String [50];
	private String[] price = new String [50];
	private String[] sellerName = new String[50];
	private String[] cId = new String[50];
	private int[] status = new int[50];
	private int[] image = new int[50];
	private int [] imageDingdan = new int [50];
	private String []dingdanstatus = new String[50];
	private int successFlag = 0;
	private int trueCount = 0; //实际显示出的条目
	private Bitmap[] bitmap; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myitem);
		Log.i("myitem","af1");
		app = (MyApplication)getApplication();
		
			
		
	
				
	        	// TODO Auto-generated method stub
	        	Log.i("run","pre1");
	        	app = (MyApplication)getApplication();
	        	Log.i("run","pre2");
	        	String[] s = app.getCId();
	        	Log.i("run","pre3");
	        	try {
					getInfo(s);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	
	        	//15265618739
	        	
	             
	            
	       
	
	}
	
	
@SuppressLint("NewApi")
public void getInfo(final String[] info) throws InterruptedException {  
		
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 

				Log.i("run","run1");
				JSONObject jCId = new JSONObject();
				Log.i("run","run2");
				
				
				JSONArray ar = new JSONArray();
				int i = 0;
				for(i = 0; i < app.getCIdCount(); i++) {
					// 声明一个JSON对象
					
					Log.i("run","run5");
					JSONObject temp = new JSONObject();
					
					try {
						temp.put(i+"",info[i]);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ar.put(temp);
					
				}
				try {
					i--;
					jCId.put("Count",i+"");
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					jCId.put("id", ar);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Log.i("jCId",jCId.toString());
				app = (MyApplication)getApplication();
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "getfoodinfo1";  
       
        // EndPoint  

        
        
        // SOAP Action  

       String soapAction = "http://10.1.99.188/getfoodinfo1";  
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("OrderId",jCId.toString());  
        
         
       
  

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        
        

        envelope.bodyOut = rpc;  
        Log.i("9","9");
        // 设置是否调用的是dotNet开发的WebService  

        envelope.dotNet = true;  
        Log.i("10","10");
        // 等价于envelope.bodyOut = rpc;  

        envelope.setOutputSoapObject(rpc);  
        

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        

        Log.i("12","12");
        try {  

           // 调用WebService  
        	

            transport.call(null, envelope);  
            Log.i("13","13");

        } catch (Exception e) {  

            e.printStackTrace();  
      }  

  
        
      // 获取返回的数据  

        
		
			try {
				detail = (Object) envelope.getResponse();
				 Log.i("14","14");
			} catch (SoapFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Looper.prepare();
				Toast.makeText(MyItem.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
				Looper.loop(); 
				
			}
		
        Log.i("1411111111",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Looper.prepare();
        	Toast.makeText(MyItem.this, "还没有定单噢 ", Toast.LENGTH_SHORT).show();
		    Looper.loop(); 
		    			}
        else 
        {	
        	//Looper.prepare();
        	//Toast.makeText(MyItem.this, "成功获取数据", Toast.LENGTH_SHORT).show();
        	//Looper.loop();
        	
        	
        //获取返回的结果  
        	JSONObject dataJson = null;
			try {
				dataJson = new JSONObject(detail.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int s = 0;
        	try {
        		bitmap = new Bitmap[app.getCIdCount()];
        		for(int a = 0;a < app.getCIdCount()  ;a++)
				{   String loop = ""+a;
					
					 byte[] buffer = new BASE64Decoder(null).decodeReturnByte(( String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("photo"));
	   		         //对android传过来的图片字符串进行解码   
	   		        bitmap[a]=BitmapFactory.decodeByteArray(buffer,0,buffer.length);
				}
        		
        		for(int a = 0;a < app.getCIdCount()  ;a++)
				{   String loop = ""+a;
					String status =""+dataJson.getJSONObject(""+a).getJSONObject(info[a]).get("status");
					if(status.equals("0")||status.equals("1"))
					{
					sellerName[trueCount] =( String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("Rsename");
					goodsName[trueCount] = (String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("name");
					price[trueCount] ="价格:"+ dataJson.getJSONObject(loop).getJSONObject(info[a]).get("price");
				    date[trueCount] ="下单时间:"+ (String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("date");
				   
					if(status.equals("0"))
					{
						dingdanstatus[trueCount] =  "状态：卖家未确认的订单。在商家确认之前可能您无法按时收到美食，如果商家长时间未确认，请重新订购或联系卖家";
						image[trueCount] = R.drawable.maijiaweiqueren;
					}
					else if(status.equals("1"))
						{
						image[trueCount] = R.drawable.maijiayiqueren;
							dingdanstatus[trueCount] = "状态：卖家已确认的订单。美食正在细心制作，请稍等。美食发出后将会有提示。";
						}
					
					
					trueCount++;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        
        

        Log.i("15",""+s);

        }	}
			};
	webserviceThread.start();
    webserviceThread.join();
    try {
		
		for (int x = 0; x < trueCount; x++) {
			Map<String, Object> map = new HashMap<String, Object>();// 定义map集合
			map.put("imageItem",null);
			
			map.put("title",  goodsName[x]);
			
			map.put("price", price[x]);
			map.put("status", dingdanstatus[x]);
			if(image[x] > 0)
			{
				Log.i("image","image1");
				map.put("image", String.valueOf(image[x]));
				
			}
			map.put("trueimage",bitmap[x]);
			map.put("date", date[x]);
			
			this.list.add(map);
			
			}
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
    
    Log.i("add","add1");
    listView = (ListView)this.findViewById(R.id.myitem);
    
	
	this.simpleAdapter = new SimpleAdapter(this,
			list,
			R.layout.list_itemdingdan,
			new String[]{"trueimage","image","title","status","price","date"},
			new int[]{R.id.imageDingdan,R.id.querenDingdan,R.id.titleDingdan,R.id.infoDingdan,R.id.priceDingdan,R.id.dingdandate});
	Log.i("add","add2");
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
	
	
	//listView.post(new Runnable() {//另外一种更简洁的发送消息给ui线程的方法。
		           
		             //@Override
		               //public void run() {//run()方法会在ui线程执行
		            	 //simpleAdapter.notifyDataSetChanged();   //必须通知ui已改变
		            	 listView.setAdapter(simpleAdapter);
		            	 Log.i("add","add3");
		            	 
		              //}
		           //});

}





    
}
