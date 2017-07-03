package com.example.FindFood;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.string;
import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.AlertDialog;

public class register extends Activity {
	private ImageButton mCancel =null;
	private String accountString = "test";
	private String address = "test";
	private String mima = "test";
	private Object detail ;
	private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button signUp = (Button)findViewById(R.id.ok);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
               
        //注册监听
        signUp.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		EditText account = (EditText)findViewById(R.id.entry1);
        	    accountString = account.getText().toString().trim();
        	    EditText addressE = (EditText)findViewById(R.id.addressyonghu);
        	    address = addressE.getText().toString().trim();
        	     
        	    EditText mimaE = (EditText)findViewById(R.id.mima);
        	    mima = mimaE.getText().toString().trim();
        	    if ("".equals(accountString) || accountString.length() < 7) {  
        	    	account .setError("您输入的手机号码（段）有误！");  
        	    	account .requestFocus();  
            		account .setText("");  
            		return;    
            	    } 
        	    if ("".equals(mima) || mima.length() < 7) {  
        	    	mimaE .setError("密码长度需要7位以上的字母与数字");  
        	    	mimaE .requestFocus();  
        	    	mimaE .setText("");  
            		return;    
            	    } 
        	    
        	    
        	    doCommunicate();
        	    
        	   
        		
    }
 
    
        	
    });
    }
    
    public void doCommunicate()
    {
Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 

				app = (MyApplication)getApplication();

		
        // 命名空间  
		//String nameSpace = "http://211.87.147.177:8080/";  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "register";  
       
        // EndPoint  

        
        
        // SOAP Action  

       String soapAction = "http://10.1.99.235:8080/register";  
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("Conname", accountString);  
        
        rpc.addProperty("Conpassword", mima);  
        
        rpc.addProperty("Conaddress",address);
       
  

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
        
        

        HttpTransportSE transport = new HttpTransportSE(endPoint);  
       

        Log.i("i","i");
        try {  

           // 调用WebService  
        	

            transport.call(null, envelope);  

        } catch (Exception e) {  

            e.printStackTrace();  
      }  

   
        
      // 获取返回的数据  

        
		
			try {
				Log.i("i","i");
				detail = (Object) envelope.getResponse();
			} catch (SoapFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Looper.prepare();
        	Toast.makeText(register.this, "用户名已存在，请重新输入", Toast.LENGTH_SHORT).show();
		    Looper.loop(); 
		    			}
        else if(detail.toString().substring(0, 3).equals("YES"));
        {	Looper.prepare();
        	Toast.makeText(register.this, "注册成功,请重新登录。", Toast.LENGTH_SHORT).show();
        	
        	app = (MyApplication)getApplication();
        	
        	Intent intent = new Intent(register.this, denglu_Activity.class); 
	        
            startActivity(intent); 
            finish();
            Looper.loop();
        	Log.i("denglu",""+app.getLoginFlag());
        	
        }
  

         

        

        Log.i("15","15");

			}
			};
		webserviceThread.start();               
		
	
    }
    

   
}
