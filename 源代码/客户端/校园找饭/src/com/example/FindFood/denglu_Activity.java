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
		String oldaccountname = share.getString("accountName", "");//����keyѰ��ֵ ����1 key ����2 ���û��value��ʾ������  
		String oldpassword  = share.getString("accountPassword", "");  
		EditText phonenumber = (EditText)findViewById(R.id.shoujihao);	        	
    	EditText password = (EditText)findViewById(R.id.mima1);	        	
    	phonenumber.setText(oldaccountname) ;	        	
    	password.setText(oldpassword);
    	
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(denglu_Activity.this, "�������Ӵ���������������", Toast.LENGTH_LONG).show();
			   
			else if(msg.what == 1)
				{
				Toast.makeText(denglu_Activity.this, "�û������������������ٴ����롣", Toast.LENGTH_LONG).show();
				
				}
			else if(msg.what == 2)
			{
			Toast.makeText(denglu_Activity.this, "�û�["+phonenumberS+"]���ѵ�½�ɹ�", Toast.LENGTH_LONG).show();
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
	                 Editor editor = share.edit();//ȡ�ñ༭��  
	                 editor.putString("autologin","1");//�洢���� ����1 ��key ����2 ��ֵ  
	                 Log.i("atuologin","autologin");
	                 editor.commit();//�ύˢ������ 
					
				}
				CheckBox jizhumima = (CheckBox)findViewById(R.id.jizhumima);
				if(jizhumima.isChecked())
				{
					Log.i("jizhu","jizhu"); 
					SharedPreferences  share = denglu_Activity.this.getSharedPreferences("perference", MODE_PRIVATE);  
					                 Editor editor = share.edit();//ȡ�ñ༭��  
					                 editor.putString("accountName", app.getAccountName());//�洢���� ����1 ��key ����2 ��ֵ  
					                 editor.putString("accountPassword", app.getPassword());  
					                 editor.commit();//�ύˢ������ 
					                 Log.i("jizhu","jizhu2");                 		
					
				}
				else
				{
					SharedPreferences  share = denglu_Activity.this.getSharedPreferences("perference", MODE_PRIVATE);  
	                 Editor editor = share.edit();//ȡ�ñ༭��  
	                 editor.putString("accountName","");//�洢���� ����1 ��key ����2 ��ֵ  
	                 editor.putString("accountPassword","");  
	                 editor.commit();//�ύˢ������ 
	                 Log.i("jizhu","jizhu2");          
					
				}
			}
			  //����message�е���Ϣ�����̵߳�UI���иĶ�

			  //����                                                      }

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
		
		webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			Log.i("endpoint",app.getEndPoint());
			Log.i("namespace",app.getNameSpace());
			 

		
        // �����ռ�  
		String nameSpace = app.getNameSpace(); 
		
      // ���õķ�������  

        String methodName = "get";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
        rpc.addProperty("Conname", phonenumberS);  
        
        rpc.addProperty("Conpassword", passwordS);  
       
  

        // ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾  

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        
  

        envelope.bodyOut = rpc;  
        Log.i("9","9");
        // �����Ƿ���õ���dotNet������WebService  

        envelope.dotNet = true;  
        Log.i("10","10");
        // �ȼ���envelope.bodyOut = rpc;  

        envelope.setOutputSoapObject(rpc);  
        Log.i("11","11");
        Log.i("Conname","1");
        Log.i("Conpassword",passwordS);
        System.out.println(passwordS);

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
        

        // ��ȡ���صĽ��  

        

        Log.i("15","15");

			}
			};
		webserviceThread.start();
		//webserviceThread.join();
	
	}
	

	
	
	
}
