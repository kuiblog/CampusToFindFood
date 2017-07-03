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

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShouyitongjiActivity extends Activity{
	
	private MyApplication app;
	private Object detail;
	private int count = 0;
	private int price = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (MyApplication)getApplication();
		Log.i("seller","3");
		setContentView(R.layout.shouyitongji);
		Log.i("seller","4");
		String[] s = app.getSId();
		Log.i("seller","5");
		try {
			getSellerinfo(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView money = (TextView)findViewById(R.id.shouyicount);
		money.setText(""+price);
		
	}
	
	
	public void getSellerinfo(final String[] info) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
				Log.i("seller","6");
				app = (MyApplication)getApplication();
				Log.i("run","run1");
				JSONObject jSId = new JSONObject();
				Log.i("run","run2");
				
				
				JSONArray ar = new JSONArray();
				int i = 0;
				for(i = 0; i < app.getSIdCount(); i++) {
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
					
					jSId.put("Count",i+"");
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					jSId.put("id", ar);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Log.i("jSId",jSId.toString());
				
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "getfoodinfo2";  
       
        // EndPoint  

          
        
        // SOAP Action  

       String soapAction = "http://211.87.147.177/selget";  
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
       rpc.addProperty("OrderId",jSId.toString());   
       
  

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        
  

        envelope.bodyOut = rpc;  
        Log.i("9","9");
        // 设置是否调用的是dotNet开发的WebService  

        envelope.dotNet = true;  
        Log.i("10","10");
        // 等价于envelope.bodyOut = rpc;  

        envelope.setOutputSoapObject(rpc);  
        Log.i("11","11");
        Log.i("Conname","1");
       

        HttpTransportSE transport = new HttpTransportSE(endPoint,5000);
        


        try {  

           // 调用WebService  
        	

            transport.call(null, envelope);  

        } catch (Exception e) {  

            e.printStackTrace();  
      }  

  
        
      // 获取返回的数据  

        
		
			try {
				detail = (Object) envelope.getResponse();
			} catch (SoapFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(ShouyitongjiActivity.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
				
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Looper.prepare();
        	Toast.makeText(ShouyitongjiActivity.this, "主人，出现问题了，稍微再试哦~", Toast.LENGTH_SHORT).show();
		    Looper.loop(); 
		    			}
        else 
        {	
        	Log.i("detail",detail.toString());
        	JSONObject dataJson = null;
			try {
				dataJson = new JSONObject(detail.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int i1 = 0;
			for(int a = 0;a < app.getSIdCount()  ;a++)
			{  		Log.i("a",""+a);
    			String status = null;
				try {
					status = ""+dataJson.getJSONObject(""+a).getJSONObject(info[a]).get("status");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(status.equals("2")||status.equals("3"))
				{	
					
					String loop = ""+a;
					
					try {
						price = price + (Integer) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("price");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
					count++;
					i1++;
				}
				
			}
        }
  

        // 获取返回的结果  
        	
        

        

			}
			};
		webserviceThread.start();
		webserviceThread.join();
		
		 
		}


			
		
		
	}
