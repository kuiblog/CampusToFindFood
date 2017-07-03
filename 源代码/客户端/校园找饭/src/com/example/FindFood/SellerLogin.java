package com.example.FindFood;

import org.json.JSONArray;
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
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SellerLogin extends Activity{
	private String sellerNumberS = "test";
	private  String sellerPasswordS = "test";
	private Object detail;
	private MyApplication app;
	private String[] sId = new String[50];
	Thread webserviceThread;
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_shangjia);
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(SellerLogin.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
			if(msg.what == 1)
				{
				Toast.makeText(SellerLogin.this, "商户号或密码错误，请检查后再次输入。", Toast.LENGTH_SHORT).show();
				EditText sellerPassword = (EditText)findViewById(R.id.mima2);
				sellerPassword.setError("用户名或密码错误");
				sellerPassword.requestFocus();
				sellerPassword.setText("");
				webserviceThread.interrupt();
				}
			  //根据message中的信息对主线程的UI进行改动

			  //……                                                      }

			}

			 };
		
		ImageView loginSeller = (ImageView)findViewById(R.id.denglu_shanghu);
		
		loginSeller.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EditText sellerNumber = (EditText)findViewById(R.id.shanghuhao);
				sellerNumberS = sellerNumber.getText().toString().trim();
				EditText sellerPassword = (EditText)findViewById(R.id.mima2);
				sellerPasswordS = sellerPassword.getText().toString().trim();
				
				CheckBox sellService = (CheckBox)findViewById(R.id.sellerservice);
				if(sellService.isChecked())
				{
					app = (MyApplication)getApplication();
					Log.i("service","prepare");
		        	Intent intent2 = new Intent(SellerLogin.this, MessageService.class);
		        	intent2.putExtra("type", "seller");
		        	startService(intent2);
		        	Log.i("service","start");
		        	app.setSellerService(1);
				}
				else
				{
					
				}
				if(true)
					{
					getAccounter();
					}
				
			}

		});
		
		TextView zuce = (TextView)findViewById(R.id.zuce_shanghu);
		zuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(SellerLogin.this,SellerRegister.class);
				startActivity(intent);
				finish();
				
			}

		});
		
		
		
	}
public void getAccounter() {  
		
		webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 

			 
				app = (MyApplication)getApplication();
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "selget";  
       
        // EndPoint  

          
        
        // SOAP Action  

       String soapAction = "http://211.87.147.177/selget";  
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("Selname", sellerNumberS);  
        
        rpc.addProperty("Selpassword", sellerPasswordS);  
       
  

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
				Log.i("detail",detail.toString());
			} catch (SoapFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				
			}
		
       
        if(detail.toString().equals("NO"))
		{
        	Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		    			}
        else if(detail.toString().equals("empty"))
        {
        	app = (MyApplication)getApplication();
        	app.setSellerName(sellerNumberS);
        	app.setSellerPassword(sellerPasswordS);
        	app.setSIdCount(0);
        	Log.i("get","get");
        	Intent intent = new Intent(SellerLogin.this, SellerHome.class);
        	startActivity(intent); 
            finish();
        }
        else 
        {	
        	
        	//Toast.makeText(getApplicationContext(),"商户["+sellerNumberS+"]您好，您已成功登陆商户后台管理系统", Toast.LENGTH_LONG).show();
        	app = (MyApplication)getApplication();
        	app.setSellerName(sellerNumberS);
        	app.setSellerPassword(sellerPasswordS);
        	
        	
        	JSONObject dataJson = null;
		    try {
				dataJson = new JSONObject(detail.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            JSONArray data = null;
			try {
				data = dataJson.getJSONArray("oidObject");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          
        
            try {
				for(int i = 0;i < Integer.parseInt(""+dataJson.get("count"));i++ )
				{
				JSONObject info=data.getJSONObject(i);
				String id=info.getString(""+i);
				//Log.i("id,denglu",id);
				
				sId[i] = id;
				}
				app.setSIdCount(Integer.parseInt(""+dataJson.get("count")) );
				
				if(Integer.parseInt(""+dataJson.get("count")) >0)
		           app.setSId(sId);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	Intent intent = new Intent(SellerLogin.this, SellerHome.class);
        
        	
        	
        	
        	
        	
        	
        	
	        startActivity(intent); 
            finish();
            
        	
        	
        }
  

        // 获取返回的结果  

        

        

			}
			};
		webserviceThread.start();
		
	
	}

}
