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
				Toast.makeText(search.this, "�Բ���Ŷ���ˣ�û������������Ҫ�Ĳ�Ʒ�����ǻᾡ����¼~~", Toast.LENGTH_LONG).show();
			if(msg.what == 1)
				{
				Toast.makeText(search.this, "�����ɹ�������ת���б�ҳ����", Toast.LENGTH_LONG).show();
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
		//���ؼ�
		
		
		mCancel = (ImageButton)findViewById(R.id.back_search);
		mCancel.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		finish();
		}
		});		
	}
	
	void search(final String st)
	{
Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			
			 

		
        // �����ռ�  
		String nameSpace = app.getNameSpace(); 
		
      // ���õķ�������  

        String methodName = "search";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
       
       rpc.addProperty("disName", st);  
        
        
       
  

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
        

        // ��ȡ���صĽ��  

        

      

			}
			};
		webserviceThread.start();
		
	}
	

}
