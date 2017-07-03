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
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConAddressChange extends Activity{
	
	private MyApplication app;
	private Object detail;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conaddresschange);
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(ConAddressChange.this, "修改失败，请联系我们。", Toast.LENGTH_LONG).show();
			if(msg.what == 1)
				{
				Toast.makeText(ConAddressChange.this, "默认地址修改成功！", Toast.LENGTH_LONG).show();
				finish();
				}
			
			                                                     

			}

			 };
		
		Button connewaddresstijiao = (Button)findViewById(R.id.connewaddresstijiao);
		connewaddresstijiao.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText conaddresschangeedit = (EditText)findViewById(R.id.conaddresschangeedit);
				String s = conaddresschangeedit.getText().toString();
				if(s == null)
				{
					conaddresschangeedit.setError("地址不能为空白");
					conaddresschangeedit.setText("");
		
				}
				else
				{
				
				changeAddress(s);
				}
				
			}

		});
		
		
		
	}
	void changeAddress(final String address) {
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			
			 

		
        // 命名空间  
		String nameSpace = app.getNameSpace(); 
		
      // 调用的方法名称  

        String methodName = "upConadd";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
       
       rpc.addProperty("Conname", app.getAccountName());  
        
        rpc.addProperty("conaddress", address);  
       
  

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        
  

        envelope.bodyOut = rpc;  
        Log.i("9","9");
        // 设置是否调用的是dotNet开发的WebService  

        envelope.dotNet = true;  
        Log.i("10","10");
        // 等价于envelope.bodyOut = rpc;  

        envelope.setOutputSoapObject(rpc);  
       

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
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
        	
		    
		    			}
        else ;
        {	Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
        }
        

        // 获取返回的结果  

        

      

			}
			};
		webserviceThread.start();
		//webserviceThread.join();
	
	}
	
	
	
	
}
