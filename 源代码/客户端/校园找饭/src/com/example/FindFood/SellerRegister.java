package com.example.FindFood;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.amap.wmap.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SellerRegister extends Activity{
	String sellerNumberS = "test";
	String sellerPasswordS = "test";
	String sellerPhoneS = "test";
	String sellerAddressS = "test";
	String dianpumingS = "test";
	String nearschoolS = "test";
	
	Object detail;
	private Handler handler;
	MyApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_shanghu);
		 handler = new Handler() {
				public void handleMessage(Message msg) {
					if(msg.what == 1)
					{
						Toast.makeText(getApplicationContext(),"注册成功，请重新登陆。", Toast.LENGTH_LONG).show();
					}
					
					
				}
			};
		Button loginSeller = (Button)findViewById(R.id.shanghuok);
		loginSeller.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EditText sellerNumber = (EditText)findViewById(R.id.yonghuming_shanghu_zuce);
				sellerNumberS = sellerNumber.getText().toString().trim();
				EditText sellerPassword = (EditText)findViewById(R.id.mima3);
				sellerPasswordS = sellerPassword.getText().toString().trim();
				EditText sellerPhone = (EditText)findViewById(R.id.shanghushouji);
				sellerPhoneS = sellerPhone.getText().toString().trim();
				EditText sellerAddress = (EditText)findViewById(R.id.dizhishanghu);
				sellerAddressS = sellerAddress.getText().toString().trim();
				EditText dianpuming = (EditText)findViewById(R.id.dianpuming);
				dianpumingS= dianpuming.getText().toString().trim();
				EditText nearschool = (EditText)findViewById(R.id.nearschool);
				nearschoolS= nearschool.getText().toString().trim();
			
				
				try {
					register();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		
	}
	
public void register() throws InterruptedException {  
		
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 

			 
				app = (MyApplication)getApplication();
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "selregister";  
       
        // EndPoint  

       
        
        // SOAP Action  

       String soapAction = "http://10.1.99.188/selregister";  
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("Selname", sellerNumberS);  
        
        rpc.addProperty("Selpassword", sellerPasswordS);  
        
        rpc.addProperty("Selphone",sellerPhoneS);
       
        rpc.addProperty("Seladdress",sellerAddressS);
        rpc.addProperty("resName",dianpumingS);
        rpc.addProperty("nearSchool",nearschoolS);
  

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
				Toast.makeText(SellerRegister.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
				
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Looper.prepare();
        	Toast.makeText(SellerRegister.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
		    Looper.loop(); 
		    			}
        else if(detail.toString().substring(0, 3).equals("YES"));
        {	
        	
            
		    
        	
        	
        	Intent intent = new Intent(SellerRegister.this, SellerLogin.class);
	        startActivity(intent); 
            finish();
            
        	
        	
        }
  


			}
			};
		webserviceThread.start();
		webserviceThread.join();
	
	}
}