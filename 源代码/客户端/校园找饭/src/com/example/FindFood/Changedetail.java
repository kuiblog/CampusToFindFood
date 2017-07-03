package com.example.FindFood;

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

public class Changedetail extends Activity{
	private Object detail;
	private MyApplication app;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changedetail);
		
		
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String titleItem = (String) bundle.get("titleItem");
		String price = bundle.getString("price");
		String info = bundle.getString("info");
		
		EditText dishname = (EditText)findViewById(R.id.dishname);
		if(titleItem != null)
		{
			dishname.setText(titleItem);
		}
		EditText dishprice = (EditText)findViewById(R.id.dishprice);
		if(price != null)
		{
			dishprice.setText(price);
		}
		
		EditText dishdec = (EditText)findViewById(R.id.dishdec);
		if(info != null)
		{
			dishdec.setText(info);
		}
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(Changedetail.this, "�޸�ʧ�ܣ��������������Ƿ���ȷ", Toast.LENGTH_LONG).show();
			if(msg.what == 1)
				{
				Toast.makeText(Changedetail.this, "��Ʒ��Ϣ�޸ĳɹ��޸ĳɹ���", Toast.LENGTH_LONG).show();
				finish();
				}
			
			                                                     

			}

			 };
		
		Button queren = (Button)findViewById(R.id.xiugaiqueren);
		queren.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText dishname = (EditText)findViewById(R.id.dishname);
				String dishnameS = dishname.getText().toString();
				EditText dishprice = (EditText)findViewById(R.id.dishprice);
				String dishpriceS = dishprice.getText().toString();
				EditText dishdec = (EditText)findViewById(R.id.dishdec);
				String dishdecS = dishdec.getText().toString();
				Intent intent = getIntent();
				Bundle bundle = intent.getExtras();
				
				String id = bundle.getString("id");
				Log.i("idddddd",id);
				if(dishnameS == null)
				{
					dishname.setError("��Ʒ������Ϊ�հ�");
					dishname.setText("");
					return;
				}
				else if(dishpriceS == null)
				{
					dishprice.setError("�۸���Ϊ�հ�");
					dishprice.setText("");
					return;
				}
				else if(dishdecS == null)
				{
					dishdec.setError("�۸���Ϊ�հ�");
					dishdec.setText("");
					return;
				}
				else
				{
				
				changeDish(dishnameS,dishpriceS,dishdecS,id);
				}
				
			}

		});
		
		
		
		
	}
	void changeDish(final String dishname,final String dishprice,final String dishdec,final String id)
	{
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			int idi = Integer.parseInt(id);
			int dishpricei = Integer.parseInt(dishprice);

		
        // �����ռ�  
		String nameSpace = app.getNameSpace(); 
		
      // ���õķ�������  

        String methodName = "upDishInfo";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     
       int i = 1;

        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
      
       rpc.addProperty("disname", dishname);  
        rpc.addProperty("disprice", dishpricei);  
        rpc.addProperty("disdesc", dishdec); 
        rpc.addProperty("stop", i); 
        rpc.addProperty("disid", idi); 
       
  

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
				msg.what = 2;
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