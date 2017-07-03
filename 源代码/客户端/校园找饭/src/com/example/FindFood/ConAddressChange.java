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
				Toast.makeText(ConAddressChange.this, "�޸�ʧ�ܣ�����ϵ���ǡ�", Toast.LENGTH_LONG).show();
			if(msg.what == 1)
				{
				Toast.makeText(ConAddressChange.this, "Ĭ�ϵ�ַ�޸ĳɹ���", Toast.LENGTH_LONG).show();
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
					conaddresschangeedit.setError("��ַ����Ϊ�հ�");
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
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			
			 

		
        // �����ռ�  
		String nameSpace = app.getNameSpace(); 
		
      // ���õķ�������  

        String methodName = "upConadd";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
       
       rpc.addProperty("Conname", app.getAccountName());  
        
        rpc.addProperty("conaddress", address);  
       
  

        // ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾  

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        
  

        envelope.bodyOut = rpc;  
        Log.i("9","9");
        // �����Ƿ���õ���dotNet������WebService  

        envelope.dotNet = true;  
        Log.i("10","10");
        // �ȼ���envelope.bodyOut = rpc;  

        envelope.setOutputSoapObject(rpc);  
       

        HttpTransportSE transport = new HttpTransportSE(endPoint,5000);
        


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
        

        // ��ȡ���صĽ��  

        

      

			}
			};
		webserviceThread.start();
		//webserviceThread.join();
	
	}
	
	
	
	
}
