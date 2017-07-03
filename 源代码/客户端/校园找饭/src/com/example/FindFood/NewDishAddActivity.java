package com.example.FindFood;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.amap.api.search.core.l;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewDishAddActivity extends Activity{
	private MyApplication app;
	private Object detail;
	private Handler handler;
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 2;
	private ImageView add;
	private Bitmap bm;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newdishadd);
		app = (MyApplication)getApplication();
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			int message = msg.what;
			if(message == 1)
			{
				
				AlertDialog.Builder build=new AlertDialog.Builder(NewDishAddActivity.this); 
				//build.setCancelable(false);
	            build.setTitle("饭小二提示")  
	                    .setMessage("菜品添加成功！")  
	                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                    finish();
	                              
	                        }  
	                    }).setPositiveButton("继续添加菜品", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                        	EditText dishName = (EditText)findViewById(R.id.dishNameEdit);
	            				dishName .setText(""); 
	            				
	            				EditText dishPrice = (EditText)findViewById(R.id.priceEdit);
	            				dishPrice .setText(""); 
	            					
	            				EditText dishInfo = (EditText)findViewById(R.id.infoEdit);
	            				dishInfo .setText(""); 
	                              
	                        }  
	                    }).show();  
				
			}
			}
		};
		
		add = (ImageView)findViewById(R.id.imageadd);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
				getAlbum.setType(IMAGE_TYPE);
				startActivityForResult(getAlbum, IMAGE_CODE);

				
			}

		});
		TextView shanghuhao = (TextView)findViewById(R.id.shanghuhaoadd);
		shanghuhao.setText(app.getSellerName());
		Button submit = (Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText dishName = (EditText)findViewById(R.id.dishNameEdit);
				String dishNameS = dishName.getText().toString();
				if(dishNameS == null)
				{
					dishName .setError("请正确填写菜品名！");  
					dishName .requestFocus();  
					dishName .setText(""); 
					return;
				}
				EditText dishPrice = (EditText)findViewById(R.id.priceEdit);
				int dishPriceS = Integer.parseInt(dishPrice.getText().toString());
				if(dishPriceS < 1)
					{
						dishName .setError("价格须设置为一元以上，且为整数。");  
						dishName .requestFocus();  
						dishName .setText(""); 
						return;
					}
				EditText dishInfo = (EditText)findViewById(R.id.infoEdit);
				String dishInfoS = dishInfo.getText().toString();
				addDish(dishNameS,dishPriceS,dishInfoS);
				
			}

		});
		
		
		
	}
	
	void addDish(final String dishNameS,final int dishPriceS,final String dishInfoS)
	{
Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 

				app = (MyApplication)getApplication();

		
        // 命名空间  
		
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "addDish";  
       
        // EndPoint  

        
        
        // SOAP Action  

       
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("Disname", dishNameS);  
        
        rpc.addProperty("Disprice", dishPriceS);  
        
        rpc.addProperty("Disdesc",dishInfoS);
       
        rpc.addProperty("Disseller",app.getSellerName());
        

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
        	
		    			}
        else if(detail.toString().substring(0, 3).equals("YES"));
        {	
        	
        	Message msg = new Message();
    		msg.what = 1;
    		handler.sendMessage(msg);
            
        	Log.i("denglu",""+app.getLoginFlag());
        	
        }
  

         

        

        Log.i("15","15");

			}
			};
		webserviceThread.start();               
		
	
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){

	    if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量

	        Log.e("TAG","ActivityResult resultCode error");

	        return;

	    }

	 

	    Bitmap bm = null;

	 

	    //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

	    ContentResolver resolver = getContentResolver();

	 

	    //此处的用于判断接收的Activity是不是你想要的那个

	    if (requestCode == IMAGE_CODE) {

	        

	            Uri originalUri = data.getData();        //获得图片的uri 

	            Log.i("add1","add1");
	            
	            try {
					bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}        //显得到bitmap图片
	            Log.i("add2","add2");
	            add.setImageBitmap(bm);
	 

	

	        

	    }

	}

	
	
		
	}
	
	


