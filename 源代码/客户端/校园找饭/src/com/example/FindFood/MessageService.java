package com.example.FindFood;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MessageService extends Service { 

	//��ȡ��Ϣ�߳� 
	private messagethread messagethread = null; 
	private messagethreadcon conmessagethread = null; 
	

	//����鿴 
	private Intent messageintent = null; 
	private PendingIntent messagependingintent = null; 
	private int currentCount;
	//֪ͨ����Ϣ 
	private int messagenotificationid = 1000; 
	private Notification messagenotification = new Notification(); 
	private NotificationManager messagenotificatiomanager = null; 
	private MyApplication app;
	private Object detail;
	private String [] sId = new String[50];
	private String [] infoUnDo = new String[50]; //δ��ɶ����ı��
	private String dishName = new String();
	private String rName = new String();
	private int unDoCount = 0; //����״̬0�����ĸ���
	private int do1Count = 0;//����״̬1�Ķ�������
	private Handler handler;
	private int unDoFlag = 1;//�Ƿ��һ��ִ���û�δ��ɶ������ӷ��� �� 1�����һ��
	private int newUnDoFlag = 0; //�Ƿ��ж���״̬�ı� ��1Ϊ�ı�
	private int unDoClassCount = 0;
	private int do1ClassCount = 0; //δ��ɶ���ʵ���ĸ���
	private infoUnOrder[] infounorder = new infoUnOrder[50]; //����״̬0�Ķ���
	private infoUnOrder[] info1order = new infoUnOrder[50];  //����״̬1�Ķ���
	private String[] cId = new String[50];
	
	class infoUnOrder{
		private String jsonId = new String();
		private String trueId = new String();
		private String status;
		public  String setJsonId(String jsonId) {
            return this.jsonId = jsonId;
    	}
        public String getJsonId() {
            return jsonId;
        }
        public  String setTrueId(String trueId) {
            return this.trueId = trueId;
    	}
        public String getTrueId() {
            return trueId;
        }
        public String getStatus() {
            return status;
    }
        public  String setStatus(String status) {
            return this.status = status;
    }
		
		
	}
	
	public IBinder onbind(Intent intent) { 
		Log.i("service","bind");
		return null; 
	} 
	
	
	public int onStartCommand(Intent intent, int flags, int startid) { 
	//��ʼ�� 
	app = (MyApplication)getApplication();
	
	handler=new Handler(){

		public void handleMessage(Message msg){

		     

		int message = msg.what;//Apogee��һ����String�࣬�����Ǳ���࣬���û������Ӧ��
		if(message == 1)
	    {
			MessageService.this.stopSelf();
			Log.i("ֹͣ����","ֹͣ����");
	    
	    }}
		};
	
	if(intent.getStringExtra("type").equals("seller"))
	{
		currentCount = app.getSIdCount();
		this.stopSelf();
		Log.i("service","2");
		Log.i("service","seller");
		messagenotification.icon = R.drawable.ic_launcher; 
		messagenotification.tickerText = "�µ�����"; 
		messagenotification.defaults = Notification.DEFAULT_SOUND; 
		messagenotificatiomanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE); 

		messageintent = new Intent(this, WeisongActivity.class); 
		messagependingintent = PendingIntent.getActivity(this,0,messageintent,0); 

	//�����߳� 
		messagethread = new messagethread(); 
		messagethread.isrunning = true; 
		messagethread.start(); 
	}
	else if(intent.getStringExtra("type").equals("con"))
	{
		Log.i("service","2");
		Log.i("service","con");
		messagenotification.icon = R.drawable.ic_launcher; 
		messagenotification.tickerText = "����Ԥ������ʳ״̬�Ѹı�"; 
		messagenotification.defaults = Notification.DEFAULT_SOUND; 
		messagenotificatiomanager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE); 

		messageintent = new Intent(this, MyItem.class); 
		
						//���붩���ı��class���ݡ�
		messagependingintent = PendingIntent.getActivity(this,0,messageintent,0); 

	//�����߳� 
		conmessagethread = new messagethreadcon(); 
		conmessagethread.isrunning = true; 
		conmessagethread.start(); 
		
	}
	

	return super.onStartCommand(intent, flags, startid); 
	} 

	/** 
	* seller�ӷ������˻�ȡ��Ϣ 
	* 
	*/ 
	class messagethread extends Thread{ 
	
	public boolean isrunning = true; 
	public void run() { 
	while(isrunning){ 
		if(app.getSellerService() == 0)  //�û�ע�����رշ���
		{
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
			isrunning = false;
			break;
		}
		try { 
	//��Ϣ10s 
	Thread.sleep(10000); 
	//��ȡ��������Ϣ 
	String servermessage = getServerMessage(); 
	if(servermessage!=null&&!"".equals(servermessage)){ 
	//����֪ͨ�� 
	messagenotification.setLatestEventInfo(MessageService.this,"���ĵ������µĶ���","�����鿴!",messagependingintent); 
	messagenotificatiomanager.notify(messagenotificationid, messagenotification); 
	//ÿ��֪ͨ�֪꣬ͨid����һ�£�������Ϣ���ǵ� 
	Log.i("service","3");
	messagenotificationid++; 
	} 
	} catch (InterruptedException e) { 
	e.printStackTrace(); 
	} 
	} 
	} 
	} 

	/** 
	* �����Դ˷���Ϊ������demo������ʾ�� 
	* @return ���ط�����Ҫ���͵���Ϣ���������Ϊ�յĻ��������� 
	*/ 
	public String getServerMessage()
	{ 
		
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 

			 
				app = (MyApplication)getApplication();
				Log.i("service","4");
        // �����ռ�  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // ���õķ�������  

        String methodName = "selget";  
       
        // EndPoint  

          
        
         
       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
        rpc.addProperty("Selname", app.getSellerName());  
        
        rpc.addProperty("Selpassword", app.getSellerPassword());  
       
  

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
       

        HttpTransportSE transport = new HttpTransportSE(endPoint);
        


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
				Toast.makeText(MessageService.this, "�������Ӵ���������������", Toast.LENGTH_SHORT).show();
				
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Looper.prepare();
        	Toast.makeText(MessageService.this, "�û������������", Toast.LENGTH_SHORT).show();
		    Looper.loop(); 
		    			}
        else 
        {	
        	
        	JSONObject dataJson = null;
		    try {
				dataJson = new JSONObject(detail.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            JSONArray data = null;
			try {
				data = dataJson.getJSONArray("oidObject");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          
          Log.i("login","1");
            try {
				for(int i = 0;i < Integer.parseInt(""+dataJson.get("count"));i++ )
				{
				JSONObject info=data.getJSONObject(i);
				String id=info.getString(""+i);
				Log.i("id,denglu",id);
				
				sId[i] = id;
				}
				app.setSIdCount(Integer.parseInt(""+dataJson.get("count")) - 1);
				Log.i("login","2");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	app.setSId(sId);
        	
        	
        	
        }
  

        // ��ȡ���صĽ��  

        

        

			}
			};
		webserviceThread.start();
		
		app = (MyApplication)getApplication();
		
		if(app.getSIdCount() != currentCount)
		{
			currentCount = app.getSIdCount();
			return "yes!"; 
			
		}
		
		Log.i("service","5");
		return null;
	}

	
	
	
	
	class messagethreadcon extends Thread{ 
		
		public boolean isrunning = true; 
		public void run() { 
		
		
		while(isrunning){ 
			if(app.getConService() == 0)  //�û�ע�����رշ���
			{
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				isrunning = false;
				break;
			}
		try { 
		//��Ϣ10���� 
		Thread.sleep(10000); 
		//��ȡ��������Ϣ 
		Log.i("service","conthread1");
		app = (MyApplication)getApplication();
		String servermessage = getServerMessagecon(); 
		if(servermessage.equals("ȷ��")){ 
		//����֪ͨ�� 
		messagenotification.setLatestEventInfo(MessageService.this,"���Ķ���״̬�иı�","��Ԥ������ʳ��"+dishName+"������["+rName+"]��ȷ�ϣ�����������ʳ�С���",messagependingintent); 
		messagenotificatiomanager.notify(messagenotificationid, messagenotification); 
		//ÿ��֪ͨ�֪꣬ͨid����һ�£�������Ϣ���ǵ� 
		Log.i("service","3");
		messagenotificationid++; 
		} 
		if(servermessage.equals("�ͳ�")){ 
			//����֪ͨ�� 
			messagenotification.setLatestEventInfo(MessageService.this,"���Ķ���״̬�иı�","��Ԥ������ʳ��"+dishName+"������["+rName+"]���ͳ�����ʳ����·�ϣ������ĵȴ�����",messagependingintent); 
			messagenotificatiomanager.notify(messagenotificationid, messagenotification); 
			//ÿ��֪ͨ�֪꣬ͨid����һ�£�������Ϣ���ǵ� 
			Log.i("service","3");
			messagenotificationid++; 
			} 
		} catch (InterruptedException e) { 
		e.printStackTrace(); 
		} 
		} 
		} 
		} 

		/** 
		* �����Դ˷���Ϊ������demo������ʾ�� 
		* @return ���ط�����Ҫ���͵���Ϣ���������Ϊ�յĻ��������� 
		 * @throws InterruptedException 
		*/ 
		public String getServerMessagecon() throws InterruptedException
		{ 	
			
			    getAccounter();
			    
				Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
				    
					
					public void run(){ 
						app = (MyApplication)getApplication();
						if(app.getConService() == 0)
						{
							
						}
						Log.i("run","run1");
						JSONObject jCId = new JSONObject();
						Log.i("run","run2");
						
						
						JSONArray ar = new JSONArray();
						int i = 0;
						for(i = 0; i < app.getCIdCount(); i++) {
							// ����һ��JSON����
							
							Log.i("run","run5");
							JSONObject temp = new JSONObject();
							
							try {
								temp.put(i+"",app.getCId()[i]);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ar.put(temp);
							
						}
						try {
							i--;
							jCId.put("Count",i+"");
						} catch (JSONException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							jCId.put("id", ar);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Log.i("jCId",jCId.toString());
						app = (MyApplication)getApplication();
				
		        // �����ռ�  
						String nameSpace = app.getNameSpace(); 
						String endPoint = app.getEndPoint();  
				
		      // ���õķ�������  

		        String methodName = "getfoodinfo1";  
		       
		        // EndPoint  

		        
		        
		        // SOAP Action  

		       String soapAction = "http://10.1.99.188/getfoodinfo1";  
		       
		  

		        // ָ��WebService�������ռ�͵��õķ�����  

		       SoapObject rpc = new SoapObject(nameSpace, methodName);  
		     


		        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
		        rpc.addProperty("OrderId",jCId.toString());  
		        
		         
		       
		  

		        // ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾  

		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
		        
		        

		        envelope.bodyOut = rpc;  
		        Log.i("9","9");
		        // �����Ƿ���õ���dotNet������WebService  

		        envelope.dotNet = true;  
		        Log.i("10","10");
		        // �ȼ���envelope.bodyOut = rpc;  

		        envelope.setOutputSoapObject(rpc);  
		        

		        HttpTransportSE transport = new HttpTransportSE(endPoint);
		        

		        Log.i("12","12");
		        try {  

		           // ����WebService  
		        	

		            transport.call(null, envelope);  
		            Log.i("13","13");

		        } catch (Exception e) {  

		            e.printStackTrace();  
		      }  

		  
		        
		      // ��ȡ���ص�����  

		        
				
					try {
						detail = (Object) envelope.getResponse();
						 Log.i("14","14");
					} catch (SoapFault e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
						
					}
				
		        Log.i("14",detail.toString());
		        if(detail.toString().equals("NO"))
				{
		        	
				    			}
		        else 
		        {	
		        	//Looper.prepare();
		        	//Toast.makeText(MyItem.this, "�ɹ���ȡ����", Toast.LENGTH_SHORT).show();
		        	//Looper.loop();
		        	
		        	
		        //��ȡ���صĽ��  
		        	JSONObject dataJson = null;
		        	
					try {
						dataJson = new JSONObject(detail.toString());
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int flag1 = 0;
					
					
		        	if(unDoFlag == 1)  //��ʼ��δ��ɶ�����
		        	{
		        		
					for(int a = 0;a < app.getCIdCount() ;a++)
					{   
					
					    String status = "test";
						try {
							status = ""+dataJson.getJSONObject(""+a).getJSONObject(app.getCId()[a]).get("status");
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//��ʼ��״̬0��
					    if(status.equals("0"))
					    {	
					    	infounorder[unDoCount] = new infoUnOrder();
					    	Log.i("loop","1");
					    	
					    	
					    	infounorder[unDoCount].setJsonId(""+a);
					    	
							infounorder[unDoCount].setTrueId(app.getCId()[a]);
							
							infounorder[unDoCount].setStatus("0"); 
							
							unDoCount++;
							
					    	
					    } 
					  //��ʼ��״̬1��
					    else if(status.equals("1"))
					    {	
					    	info1order[do1Count] = new infoUnOrder();
					    	Log.i("loop1","1");
					    	
					    	
					    	info1order[do1Count].setJsonId(""+a);
					    	
					    	info1order[do1Count].setTrueId(app.getCId()[a]);
							
							info1order[do1Count].setStatus("1"); 
							
							do1Count++;
							
					    	
					    } 
					}
						unDoFlag = 0;
						do1Count--; //���һ�γ�ʼ��ѭ�������һ��undo����
						unDoCount--; //���һ�γ�ʼ��ѭ�������һ��undo����
		        	}
					
					//**��̬ˢ��״̬Ϊ0�Ķ���
					try {
		        		for(int a = 0;a < app.getCIdCount() ;a++)
						{   
							flag1 = 0;
						    String status =""+dataJson.getJSONObject(""+a).getJSONObject(app.getCId()[a]).get("status");
						    //Log.i("cid",""+app.getCId()[a]);
						    
						    
						    if(status.equals("0"))
							{	
								for(int b = 0;b < unDoCount+1;b++)
								{	
									if(infounorder[b].getTrueId().equals(app.getCId()[a]))
									{	
										Log.i("test",""+b);
										flag1 = 1;
										break;
										
								    }
								}
								 if(flag1 == 0)
								{
									Log.i("add","add");
									unDoCount++;
									infounorder[unDoCount] = new infoUnOrder();
									Log.i("add","add");
									infounorder[unDoCount].setJsonId(""+a);
									Log.i("add","add");
									infounorder[unDoCount].setTrueId(app.getCId()[a]);
									Log.i("add","add");
									infounorder[unDoCount].setStatus("0"); 
									
									break;
									
									
									
								}
								
							}
							
							
							
							
							
						}
		        		
		        	for(int c = 0;c < unDoCount+1; c++)
		        	{	
		        		Log.i("start","start");
		        		if(infounorder[c].getJsonId().equals("done"))
		        		{
		        			Log.i("continue","continue");
		        			continue;
		        		}
		        		String status =""+dataJson.getJSONObject(infounorder[c].getJsonId()).getJSONObject(infounorder[c].getTrueId()).get("status");
		        		Log.i("1","1");
		        		Log.i("c:"+c,"status:"+status+"JsonId"+infounorder[c].getJsonId()+"TrueId:"+infounorder[c].getTrueId());
		        		Log.i("2","2");
		        		if(status.equals("1"))
		        		{	
		        			Log.i("3","3");
		        			infoUnDo[unDoClassCount] =  ""+unDoCount;
		        			Log.i("4","4");
		        			newUnDoFlag = 1;
		        			rName = ""+dataJson.getJSONObject(infounorder[c].getJsonId()).getJSONObject(infounorder[c].getTrueId()).get("Rsename");
	        				dishName = ""+dataJson.getJSONObject(infounorder[c].getJsonId()).getJSONObject(infounorder[c].getTrueId()).get("name");
	        				Log.i("5","5");
	        				infounorder[c].setJsonId("done");
	        				Log.i("6","6");
		        			Log.i("con","con");
		        			
		        		}
		        		
		    			
		        	}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	Log.i("fin","2");
		        //**��̬ˢ��״̬Ϊ1�Ķ���
					
					for(int a = 0;a < app.getCIdCount() ;a++)
					{   
						flag1 = 0;
					    String status = null;
						try {
							status = ""+dataJson.getJSONObject(""+a).getJSONObject(app.getCId()[a]).get("status");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    //Log.i("cid",""+app.getCId()[a]);
					    
					    
					    if(status.equals("1"))
						{	
					    	infoUnDo[do1ClassCount] =  ""+app.getCIdCount();
							for(int b = 0;b < do1Count+1;b++)
							{	
								if(info1order[b].getTrueId().equals(app.getCId()[a]))
								{	
									Log.i("test",""+b);
									flag1 = 1;
									break;
									
							    }
							}
							 if(flag1 == 0)
							{
								Log.i("add","add");
								do1Count++;
								info1order[do1Count] = new infoUnOrder();
								Log.i("add","add");
								info1order[do1Count].setJsonId(""+a);
								Log.i("add","add");
								info1order[do1Count].setTrueId(app.getCId()[a]);
								Log.i("add","add");
								info1order[do1Count].setStatus("1"); 
								
								break;
								
								
								
							}
							
						}
						
						
						
						
						
					}
	        		
	        	for(int c = 0;c < do1Count+1; c++)
	        	{	
	        		if(info1order[c].getJsonId().equals("done"))
	        		{
	        			continue;
	        		}
	        		String status = null;
					try {
						status = ""+dataJson.getJSONObject(info1order[c].getJsonId()).getJSONObject(info1order[c].getTrueId()).get("status");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		Log.i("c:"+c,"status:"+status+"JsonId"+info1order[c].getJsonId()+"TrueId:"+info1order[c].getTrueId());
	        		if(status.equals("2"))
	        		{	
	        			
	        			infoUnDo[do1ClassCount] =  ""+do1Count;
	        			try {
							messageintent.putExtra("name",""+dataJson.getJSONObject(info1order[c].getJsonId()).getJSONObject(info1order[c].getTrueId()).get("name"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			try {
							messageintent.putExtra("Rsename",""+dataJson.getJSONObject(info1order[c].getJsonId()).getJSONObject(info1order[c].getTrueId()).get("Rsename"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			newUnDoFlag = 2;
	        			
	        			try {
							rName = ""+dataJson.getJSONObject(info1order[c].getJsonId()).getJSONObject(info1order[c].getTrueId()).get("Rsename");
	        				dishName = ""+dataJson.getJSONObject(info1order[c].getJsonId()).getJSONObject(info1order[c].getTrueId()).get("name");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			info1order[c].setJsonId("done");
	        			Log.i("con","con");
	        		}
	        		
	    			
	        	}
				} 
	        	
	        	
	        	
	        	
	        	} 
		        	
		        	
		        	
		        
		        
		        	
		        	
		        
		        

		        

		        	
					};
			webserviceThread.start();
		    webserviceThread.join();
			
			
			if(newUnDoFlag == 1)
			{
				Log.i("enter","enter");
				newUnDoFlag = 0;
				
				
				return "ȷ��";
				
			}
			else if(newUnDoFlag == 2)
			{
				newUnDoFlag = 0;
				return "�ͳ�";
				
			}
			
			
			
			
			
			
			Log.i("service","5");
			return "null";
		}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	} 
	
public void getAccounter() throws InterruptedException {  
		
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
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
        rpc.addProperty("Conname", app.getAccountName());  
        
        rpc.addProperty("Conpassword", app.getPassword());  
       
  

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
        
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        


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
			
        	
		    
		    			}
        else 
        {	Message msg = new Message();
			msg.what = 2;
			
        	
        	app = (MyApplication)getApplication();
        	
        	
        	
        	
	       
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
		webserviceThread.join();
	
	}
	
	
	
} 

	
