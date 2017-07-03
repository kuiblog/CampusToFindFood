package com.example.FindFood;



import java.util.HashMap;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class denglu_Activity extends Activity{
	private Object detail;
	private String phonenumberS = "test";
	private String passwordS = "test";
	private MyApplication app;
	private String [] cId = new String[20];
	Handler handler;
	Thread webserviceThread;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		SharedPreferences  share = denglu_Activity.this.getSharedPreferences("perference", MODE_PRIVATE);
		String oldaccountname = share.getString("accountName", "");//根据key寻找值 参数1 key 参数2 如果没有value显示的内容  
		String oldpassword  = share.getString("accountPassword", "");  
		EditText phonenumber = (EditText)findViewById(R.id.shoujihao);	        	
    	EditText password = (EditText)findViewById(R.id.mima1);	        	
    	phonenumber.setText(oldaccountname) ;	        	
    	password.setText(oldpassword);
    	
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(denglu_Activity.this, "网络连接错误，请检查您的网络", Toast.LENGTH_LONG).show();
			   
			else if(msg.what == 1)
				{
				Toast.makeText(denglu_Activity.this, "用户名或密码错误，请检查后再次输入。", Toast.LENGTH_LONG).show();
				
				}
			else if(msg.what == 2)
			{
			Toast.makeText(denglu_Activity.this, "用户["+phonenumberS+"]您已登陆成功", Toast.LENGTH_LONG).show();
			app.setAutoCity(0);
			CheckBox conService = (CheckBox)findViewById(R.id.conservice);
			
				if(conService.isChecked())
				{
					app = (MyApplication)getApplication();
					
					
					
					app.setConService(1);
					Log.i("service","prepare");
					
					Intent intent2 = new Intent(denglu_Activity.this, MessageService.class);
					
		        	intent2.putExtra("type", "con");
		        	startService(intent2);
					
		        	Log.i("service","start");
				}
				else
				{
					app.setConService(0);
					
				}
				CheckBox autologin = (CheckBox)findViewById(R.id.autologin);
				if(autologin.isChecked())
				{
					SharedPreferences  share = denglu_Activity.this.getSharedPreferences("perference", MODE_PRIVATE);  
	                 Editor editor = share.edit();//取得编辑器  
	                 editor.putString("autologin","1");//存储配置 参数1 是key 参数2 是值  
	                 Log.i("atuologin","autologin");
	                 editor.commit();//提交刷新数据 
					
				}
				CheckBox jizhumima = (CheckBox)findViewById(R.id.jizhumima);
				if(jizhumima.isChecked())
				{
					Log.i("jizhu","jizhu"); 
					SharedPreferences  share = denglu_Activity.this.getSharedPreferences("perference", MODE_PRIVATE);  
					                 Editor editor = share.edit();//取得编辑器  
					                 editor.putString("accountName", app.getAccountName());//存储配置 参数1 是key 参数2 是值  
					                 editor.putString("accountPassword", app.getPassword());  
					                 editor.commit();//提交刷新数据 
					                 Log.i("jizhu","jizhu2");                 		
					
				}
				else
				{
					SharedPreferences  share = denglu_Activity.this.getSharedPreferences("perference", MODE_PRIVATE);  
	                 Editor editor = share.edit();//取得编辑器  
	                 editor.putString("accountName","");//存储配置 参数1 是key 参数2 是值  
	                 editor.putString("accountPassword","");  
	                 editor.commit();//提交刷新数据 
	                 Log.i("jizhu","jizhu2");          
					
				}
			}
			  //根据message中的信息对主线程的UI进行改动

			  //……                                                      }

			}

			 };
			 
			 
				
		
		
		ImageButton denglu = (ImageButton)findViewById(R.id.denglu);
        Log.i("ii","ii");
	    denglu.setOnClickListener(new android.view.View.OnClickListener()  { 
	        public void onClick(View v) { 
	        	// TODO Auto-generated method stub
	        	
	        	
	        	
	        	EditText phonenumber = (EditText)findViewById(R.id.shoujihao);	        	
	        	EditText password = (EditText)findViewById(R.id.mima1);	        	
	        	phonenumberS = phonenumber.getText().toString(); 	        	
	        	passwordS = password.getText().toString(); 
	        	Log.i("1","1");
	        	try {
					getAccounter(phonenumberS,passwordS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	 Log.i("Conname", phonenumberS);
	             Log.i("Conpassword", passwordS);
	        	
	             
	            
	        }});
	    TextView zuce= (TextView)findViewById(R.id.denglu_zuce);
	    zuce.setOnClickListener(new android.view.View.OnClickListener()  { 
	        public void onClick(View v) { 
	        	// TODO Auto-generated method stub	        		        	 	        	
	            Intent intent = new Intent(denglu_Activity.this, register.class); 	            
	            startActivity(intent); 
	            finish();
	        }});
	    
	    
	}
	
	public void getAccounter(String phoneSec,String password) throws InterruptedException {  
		
		webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			Log.i("endpoint",app.getEndPoint());
			Log.i("namespace",app.getNameSpace());
			 

		
        // 命名空间  
		String nameSpace = app.getNameSpace(); 
		
      // 调用的方法名称  

        String methodName = "get";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("Conname", phonenumberS);  
        
        rpc.addProperty("Conpassword", passwordS);  
       
  

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
        Log.i("Conpassword",passwordS);
        System.out.println(passwordS);

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
				msg.what = 0;
				handler.sendMessage(msg);
				Thread.interrupted();
				
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
        	
		    
		    			}
        else 
        {	Message msg = new Message();
			msg.what = 2;
			handler.sendMessage(msg);
        	Intent intent = getIntent();
        	app = (MyApplication)getApplication();
        	app.setAccountName(phonenumberS);
        	app.setPassword(passwordS);
        	Log.i("app.getAccountName()",app.getAccountName());
    		Log.i("app.getPassword()",app.getPassword());
        	app.setLoginFlag(1);
        	if(intent.getIntExtra("backflag", 1) == 1)
        	{
        		Log.i("true","true");
        		
        	}
        	else{
        	Intent intent1 = new Intent(denglu_Activity.this, MainActivity.class); 
        	
        	intent1.putExtra("toWhichTab", "0");
        	}
	        //Looper.loop(); 
            JSONObject dataJson;
			try {
			
			dataJson = new JSONObject(detail.toString());
			app = (MyApplication)getApplication();
            JSONArray data=dataJson.getJSONArray("oidObject");
            app.setCAddress((String) dataJson.get("address"));
          
            for(int i = 0;i < Integer.parseInt(""+dataJson.get("count"));i++ )
            {
            JSONObject info=data.getJSONObject(i);
            String id=info.getString(""+i);
            Log.i("id,denglu",id);
            cId[i] = id;
            }
            app.setCId(cId);
            app.setCIdCount(Integer.parseInt(""+dataJson.get("count")));
            Log.i("countset!!!!!!!!!!!!!",""+app.getCIdCount());
            
        	 
        	
        	
            if(intent.getIntExtra("backflag", 1) != 1)
        	{
        		
        		
            startActivity(intent); 
        	}
            else
            {
            	Intent intent1 = new Intent(denglu_Activity.this, MainActivity.class); 
            	
            	intent1.putExtra("toWhichTab", "0");
            	startActivity(intent1);
            	
            }
            finish();
            //Looper.loop(); 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        	
        }
        

        // 获取返回的结果  

        

        Log.i("15","15");

			}
			};
		webserviceThread.start();
		//webserviceThread.join();
	
	}
	

	
	
	
}
