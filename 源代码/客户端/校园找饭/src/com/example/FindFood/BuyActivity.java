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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BuyActivity extends Activity{
	MyApplication app;
	Object detail;
	private Handler handler;
	private Context ctx;
	private String []cId = new String [50];
	private String id = new String();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy);
		app = (MyApplication)getApplication();
		Log.i("buy","1");
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			int message = (Integer) msg.obj;//Apogee不一定是String类，可以是别的类，看用户具体的应用
			if(message == 0)
		    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

			  //根据message中的信息对主线程的UI进行改动

			  //……                                                      }

			}

			 };

		
			 Log.i("buy","2");
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getString("id");
		String phone = bundle.getString("phone");
		String base64 = bundle.getString("base64");
		byte[] buffer = new BASE64Decoder(null).decodeReturnByte(base64);
        //对android传过来的图片字符串进行解码   
        Bitmap bitmap = BitmapFactory.decodeByteArray(buffer,0,buffer.length);
		TextView title = (TextView)findViewById(R.id.itemdetailtitle);
		title.setText(bundle.getString("titleItem"));
		TextView info = (TextView)findViewById(R.id.itemdetailinfo);
		info.setText(bundle.getString("info"));
		TextView sellerAddress = (TextView)findViewById(R.id.addressBuyDetail);
		sellerAddress.setText(bundle.getString("address"));
		Log.i("buy","3");
		TextView sellerPhone = (TextView)findViewById(R.id.buyDetailSellerPhone);
		Log.i("buy","4");
		sellerPhone.setText(phone);
		Log.i("buy","5");
		EditText cPhone = (EditText)findViewById(R.id.editText_phone);
		Log.i("buy","6");
		cPhone.setText(app.getAccountName());
		Log.i("buy","7");
		EditText cAddress = (EditText)findViewById(R.id.editText_name);
		Log.i("buy","8");
		cAddress.setText(app.getCAddress());
		
		ImageButton queren = (ImageButton)findViewById(R.id.queren);
		ImageView image = (ImageView)findViewById(R.id.userface);
		image.setImageBitmap(bitmap);
		queren.setOnClickListener(new android.view.View.OnClickListener()  { 
	        public void onClick(View v) { 
	        	// TODO Auto-generated method stub	        		        	 	        	
	        	EditText cAddress = (EditText)findViewById(R.id.editText_name);
    			EditText cPhone = (EditText)findViewById(R.id.editText_phone);
	        	int idint;
	        	Log.i("buy","5");
	    		if(cAddress.getText().toString()!= null && cPhone.getText().toString() != null && id != null)
	    			{
	    			idint = Integer.parseInt(id);
	    			
	    			Log.i("buy","6");
	    			try {
						addorder(idint,cPhone.getText().toString(),cAddress.getText().toString());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			Log.i("id",""+idint);
	    			Log.i("phone",cPhone.getText().toString());
	    			Log.i("address",cAddress.getText().toString());
	    			
	    			Toast.makeText(getApplicationContext(), "订餐成功！", Toast.LENGTH_LONG).show();
	    			}
	    		else
	    			Toast.makeText(getApplicationContext(), "您尚未登陆或菜品id有误，请返回", Toast.LENGTH_LONG).show();
	            
	        }});
		
		
		
	}
	
	
	
	public void addorder(final int id,final String phone,final String address) throws InterruptedException {  
		
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 

			 
				app = (MyApplication)getApplication();
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "addorder";  
       
        // EndPoint  

          
        
        // SOAP Action  

       String soapAction = "http://211.87.147.177/selget";  
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("DisId", id);  
        
        rpc.addProperty("Conname", phone);
        rpc.addProperty("address", address);  
       
  

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
        
        
        		
        try {
			detail = (Object) envelope.getResponse();
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ctx = getApplicationContext();
        Log.i("Yes",detail.toString());
        if(detail.toString().equals("YES"))
        {
        	int i = 0;
        	Log.i("进入","进入");
        	  
        	
        	
        	Intent intent = new Intent(BuyActivity.this,MainActivity.class);
        	intent.putExtra("toWhichTab", "2");
        	
        	startActivity(intent);
        	finish();
        	
        }
        
      // 获取返回的数据  

        
		
			
        	
        
  

        // 获取返回的结果  

        

        

			}
			};
		webserviceThread.start();
		webserviceThread.join();
		
		
	
	}
	
	

	
	
	public Handler getHandler(){

		return this.handler;

		}

	
}
