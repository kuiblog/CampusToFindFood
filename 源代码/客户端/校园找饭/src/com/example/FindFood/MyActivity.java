package com.example.FindFood;

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

import com.example.FindFood.SellerHome.bt_seller_exit;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity{
	private Button bt_exit;
	private MyApplication app ;
	private RelativeLayout re_myitem;
	private RelativeLayout re_adress;
	private RelativeLayout re_pingjia;
	private Object detail;
	private String[] cId = new String[50];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("my","1");
		app = (MyApplication)getApplication();
		setContentView(R.layout.activity_my);
		TextView accountname = (TextView)findViewById(R.id.accountname);
		accountname.setText(app.getAccountName());
		bt_exit = (Button)findViewById(R.id.bt_exit);		
		bt_exit.setOnClickListener(new bt_exit());
		re_myitem = (RelativeLayout)findViewById(R.id.myitem1);		
		re_myitem.setOnClickListener(new re_myitem());
		re_adress= (RelativeLayout)findViewById(R.id.myadress1);		
		re_adress.setOnClickListener(new re_adress());
		//re_pingjia= (RelativeLayout)findViewById(R.id.pingjia1);		
		//re_pingjia.setOnClickListener(new re_pingjia());
		
		int i = app.getLoginFlag();
		Log.i("my","2");
		Log.i("LoginFlag",""+i);
		
		
		RelativeLayout mimadizhi = (RelativeLayout)findViewById(R.id.mimadizhi1);
		mimadizhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
					    
				AlertDialog.Builder build=new AlertDialog.Builder(MyActivity.this); 
				build.setCancelable(false);
	            build.setTitle("饭小二提示")  
	                    .setMessage("请问您要？")  
	                    .setPositiveButton("修改密码", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                            Intent intent = new Intent(MyActivity.this,ConPasswordChange.class);
	                            startActivity(intent);
	                              
	                        }  
	                    })  
	                    .setNegativeButton("修改默认地址", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                        	Intent intent = new Intent(MyActivity.this,ConAddressChange.class);
	                        	
	                        	startActivity(intent);
	                        	
	                        }  
	                    })  
	                    .show();  
					  
					
			
				
			}

		});
		
		
		if(app.getLoginFlag() == 0)
		{	Log.i("my","4");
			AlertDialog.Builder build=new AlertDialog.Builder(this); 
			build.setCancelable(false);
            build.setTitle("找饭提示")  
                    .setMessage("您还没有登录，现在去登陆？")  
                    .setPositiveButton("登陆", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                            Intent intent = new Intent(MyActivity.this,denglu_Activity.class);
                            startActivity(intent);
                            finish();
                              
                        }  
                    })  
                    .setNegativeButton("先看看我附近有什么", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                        	Intent intent = new Intent(MyActivity.this,MainActivity.class);
                        	intent.putExtra("toWhichTab", "1");
                        	startActivity(intent);
                        	finish();
                        }  
                    })  
                    .show();  
		}
		else
		{
			TextView accountName = (TextView)findViewById(R.id.accountname);
			if(app.getAccountName()!= null )
			accountName.setText(app.getAccountName());
			
			
			
		}
		
		
		
		
	}
	class bt_exit implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			app.setAccountName(null);
			app.setLoginFlag(0);
			intent.setClass(MyActivity.this, denglu_Activity.class);
			MyActivity.this.startActivity(intent);
			app.setConService(0);
			Log.i("exit","exit");
			
			finish();
		}
	}
	class re_myitem implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			Log.i("myitem","pre1");
			app =(MyApplication)getApplication();
			try {
				getAccounter(app.getAccountName(),app.getPassword());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			intent.setClass(MyActivity.this, MyItem.class);
			Log.i("myitem","pre2");
			MyActivity.this.startActivity(intent);
			Log.i("myitem","pre3");
			
		}
		
	}
	class re_adress implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(MyActivity.this, querenItemActivity.class);
			MyActivity.this.startActivity(intent);
			
		}
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        // TODO Auto-generated method stub  
        switch (keyCode) {  
        case KeyEvent.KEYCODE_BACK:  
            AlertDialog.Builder build=new AlertDialog.Builder(this);  
            build.setTitle("找饭提示")  
                    .setMessage("确定要退出校园找饭吗？")  
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                            finish();  
                              
                        }  
                    })  
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                              
                        }  
                    })  
                    .show();  
            break;  
  
        default:  
            break;  
        }  
        return false;
}
	
	public void getAccounter(final String phoneSec,final String password) throws InterruptedException {  
		
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			

		
	    // 命名空间  
		String nameSpace = app.getNameSpace(); 
		
	  // 调用的方法名称  

	    String methodName = "get";  
	   
	    // EndPoint  

	    String endPoint = app.getEndPoint();  
	    
	    // SOAP Action  

	   //String soapAction = "http://10.1.99.188/get";  
	   


	    // 指定WebService的命名空间和调用的方法名  

	   SoapObject rpc = new SoapObject(nameSpace, methodName);  
	 


	    // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
	    rpc.addProperty("Conname", phoneSec);  
	    
	    rpc.addProperty("Conpassword", password);  
	   


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
				Toast.makeText(MyActivity.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
				
			}
		
	    Log.i("14",detail.toString());
	    if(detail.toString().equals("NO"))
		{
	    	Looper.prepare();
	    	Toast.makeText(MyActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
	    	return;
	    	
		    
		    			}
	    else ;
	    {	//Looper.prepare();
	    	//Toast.makeText(denglu_Activity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
	    	//Looper.loop(); 
	    	
	    	app = (MyApplication)getApplication();
	    	
	    	
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
	        app.setCIdCount(0);
	        app.setCId(null);
	        app.setCId(cId);
	        app.setCIdCount(Integer.parseInt(""+dataJson.get("count")));
	        
	        Log.i("reset",""+app.getCIdCount());
	        
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
		
		

	}
}
