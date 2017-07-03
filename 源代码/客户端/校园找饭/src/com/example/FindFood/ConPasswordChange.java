package com.example.FindFood;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConPasswordChange extends Activity{
	
	private MyApplication app;
	private Object detail;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conpasswordchange);
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(ConPasswordChange.this, "ԭ������д������������ԡ�", Toast.LENGTH_LONG).show();
			if(msg.what == 1)
				{
				Toast.makeText(ConPasswordChange.this, "�����޸ĳɹ���", Toast.LENGTH_LONG).show();
				finish();
				
				}
			if(msg.what == 2)
			{
			Toast.makeText(ConPasswordChange.this, "����������Ժ����ԡ�", Toast.LENGTH_LONG).show();
			
			}
			
			                                                     

			}

			 };
		Button conpasswordchongtian = (Button)findViewById(R.id.conpasswordchongtian);
		conpasswordchongtian.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				EditText conoldpasswordedit = (EditText)findViewById(R.id.conoldpasswordedit);
				EditText connewpasswordedit = (EditText)findViewById(R.id.connewpasswordedit);
				EditText conquerennewpasswordedit = (EditText)findViewById(R.id.conquerennewpasswordedit);
				conoldpasswordedit.setText("");
				connewpasswordedit.setText("");
				conquerennewpasswordedit.setText("");
				conoldpasswordedit.requestFocus(); 
			}
		});
		Button passwordchange = (Button)findViewById(R.id.passwordchange);
		passwordchange.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText conoldpasswordedit = (EditText)findViewById(R.id.conoldpasswordedit);
				String oldPassword = conoldpasswordedit.getText().toString();
				EditText connewpasswordedit = (EditText)findViewById(R.id.connewpasswordedit);
				String newpassword = connewpasswordedit.getText().toString();
				EditText conquerennewpasswordedit = (EditText)findViewById(R.id.conquerennewpasswordedit);
				String newpasswordcheck = conquerennewpasswordedit.getText().toString();
				if(oldPassword == null)
				{
					conoldpasswordedit.setError("������д������");
					conoldpasswordedit.requestFocus(); 
					conoldpasswordedit.setText("");
					connewpasswordedit.setText("");
					conquerennewpasswordedit.setText("");
					return;
				}
				else if(newpassword == null||newpassword.length() < 7)
				{
					connewpasswordedit.setError("�����볤�ȱ������7λ");
					connewpasswordedit.requestFocus(); 
					connewpasswordedit.setText("");
					conquerennewpasswordedit.setText("");
					return;
				}
				else if(!newpasswordcheck.equals(newpassword))
				{
					conquerennewpasswordedit.setError("�����������벻һ��,����������");
					connewpasswordedit.setText("");
					connewpasswordedit.requestFocus(); 
					conquerennewpasswordedit.setText("");
					return;
				}
				else
				{
				
				changePassword(oldPassword,newpassword);
				}
				
			}

		});
		
		
		
	}
	void changePassword(final String oldPassword,final String newPassword) {
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			
			 

		
        // �����ռ�  
		String nameSpace = app.getNameSpace(); 
		
      // ���õķ�������  

        String methodName = "upConpass";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
       
       rpc.addProperty("Conname", app.getAccountName());  
        
        rpc.addProperty("oldPassword", oldPassword);  
        rpc.addProperty("newPassword", newPassword);  
       
  

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

