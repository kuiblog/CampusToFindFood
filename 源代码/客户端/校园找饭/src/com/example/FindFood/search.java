package com.example.FindFood;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class search extends Activity{
	private ImageButton backButton =null;
	private ImageButton mCancel =null;
	private MyApplication app;
	private Object detail;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(search.this, "对不起哦主人，没有搜索到您想要的菜品，我们会尽快收录~~", Toast.LENGTH_LONG).show();
			if(msg.what == 1)
				{
				Toast.makeText(search.this, "搜索成功，正在转向列表页。。", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(search.this,SearchList.class);
				Bundle bundle = new Bundle();
				bundle.putString("detail", detail.toString());
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
				}
			
			                                                     

			}

			 };
			 app = (MyApplication)getApplication();
		Button search = (Button)findViewById(R.id.search1);
		search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText searchText = (EditText)findViewById(R.id.editText1);
				
				String st = searchText.getText().toString();
				
					search(st);
					
				
					
				
				}
				});		
		//返回键
		
		
		mCancel = (ImageButton)findViewById(R.id.back_search);
		mCancel.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		finish();
		}
		});		
	}
	
	void search(final String st)
	{
Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			
			 

		
        // 命名空间  
		String nameSpace = app.getNameSpace(); 
		
      // 调用的方法名称  

        String methodName = "search";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
       
       rpc.addProperty("disName", st);  
        
        
       
  

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
			try {
				JSONObject dataJson = new JSONObject(detail.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			
			
			
        }
        

        // 获取返回的结果  

        

      

			}
			};
		webserviceThread.start();
		
	}
	

}
