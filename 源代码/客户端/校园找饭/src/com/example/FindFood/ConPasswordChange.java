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
				Toast.makeText(ConPasswordChange.this, "原密码填写错误，请检查后再试。", Toast.LENGTH_LONG).show();
			if(msg.what == 1)
				{
				Toast.makeText(ConPasswordChange.this, "密码修改成功！", Toast.LENGTH_LONG).show();
				finish();
				
				}
			if(msg.what == 2)
			{
			Toast.makeText(ConPasswordChange.this, "网络错误，请稍后在试。", Toast.LENGTH_LONG).show();
			
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
					conoldpasswordedit.setError("必须填写旧密码");
					conoldpasswordedit.requestFocus(); 
					conoldpasswordedit.setText("");
					connewpasswordedit.setText("");
					conquerennewpasswordedit.setText("");
					return;
				}
				else if(newpassword == null||newpassword.length() < 7)
				{
					connewpasswordedit.setError("新密码长度必须大于7位");
					connewpasswordedit.requestFocus(); 
					connewpasswordedit.setText("");
					conquerennewpasswordedit.setText("");
					return;
				}
				else if(!newpasswordcheck.equals(newpassword))
				{
					conquerennewpasswordedit.setError("两次密码输入不一致,请重新输入");
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
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			
			 

		
        // 命名空间  
		String nameSpace = app.getNameSpace(); 
		
      // 调用的方法名称  

        String methodName = "upConpass";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
       
       rpc.addProperty("Conname", app.getAccountName());  
        
        rpc.addProperty("oldPassword", oldPassword);  
        rpc.addProperty("newPassword", newPassword);  
       
  

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
        

        // 获取返回的结果  

        

      

			}
			};
		webserviceThread.start();
		//webserviceThread.join();
	
	}
	
	
	
	
}

