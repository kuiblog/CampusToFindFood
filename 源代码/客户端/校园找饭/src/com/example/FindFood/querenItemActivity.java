package com.example.FindFood;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class querenItemActivity extends Activity{
	private ListView listView;
	private SimpleAdapter simpleAdapter=null;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private int num = 0;
	private Object detail;
	private String infoR ="test";
	private TextView tx;
	private MyApplication app;
	private List<Map<String, Object>> listItems;
	private String[] date = new String [50];
	private String[] goodsName = new String [50];
	private String[] price = new String [50];
	private String[] sellerName = new String[50];
	private String[] cId = new String[50];
	private int[] status = new int[50];
	private int[] image = new int[50];
	private String []dingdanstatus = new String[50];
	private int trueCount = 0;
	private String ids = new String();
	private Handler handler;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.querensongda);
		Log.i("myitem1","af1");
		app = (MyApplication)getApplication();
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(querenItemActivity.this, "�������Ӵ���������������", Toast.LENGTH_SHORT).show();
			if(msg.what == 1)
				{
				Toast.makeText(querenItemActivity.this, "����״̬�Ѹ��ģ����ڽ�ˢ��ҳ��", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(querenItemActivity.this,querenItemActivity.class);
				startActivity(intent);
				finish();
				}
			  //����message�е���Ϣ�����̵߳�UI���иĶ�

			  //����                                                      }

			}

			 };
		
	
				
	        	// TODO Auto-generated method stub
		
		listView = (ListView) this.findViewById(R.id.songdaitem);
		listView.setOnItemClickListener(new OnItemClickListener(){
			public void  onItemClick(AdapterView<?>listView,View view,int position,long id)
			{
				Map<String, String>  map = (Map<String, String>) listView.getItemAtPosition(position);
				ids = map.get("id");
				AlertDialog.Builder build = new AlertDialog.Builder(querenItemActivity.this); 
				build.setCancelable(false);
	            build.setTitle("�ҷ���ʾ")  
	                    .setMessage("�����յ�����ʳ��")  
	                    .setPositiveButton("�ǵ�", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                        	//
	                        	checkTo2(ids,2);
	                        	Toast.makeText(querenItemActivity.this, "��ȷ�ϣ���л���ĺ�����", Toast.LENGTH_LONG).show();
	                              
	                        }  
	                    }).setNegativeButton("��û��", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                           
	                              
	                        }  
	                    })
	                    
	             .show();  
				
				
				
			}

			
			
		});
		
				
		
	        	Log.i("run","pre1");
	        	app = (MyApplication)getApplication();
	        	Log.i("run","pre2");
	        	String[] s = app.getCId();
	        	Log.i("run","pre3");
	        	try {
					getInfo(s);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	
	        	//15265618739
	        	
	             
	            
	       
	
	}
	
	
@SuppressLint("NewApi")
public void getInfo(final String[] info) throws InterruptedException {  
		
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 

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
						temp.put(i+"",info[i]);
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
				Looper.prepare();
				Toast.makeText(querenItemActivity.this, "�������Ӵ���������������", Toast.LENGTH_SHORT).show();
				Looper.loop(); 
				
			}
		
        Log.i("test",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Looper.prepare();
        	Toast.makeText(querenItemActivity.this, "��û�ж����� ", Toast.LENGTH_SHORT).show();
		    Looper.loop(); 
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
			int s = 0;
        	try {
        		for(int a = 0;a < app.getCIdCount() ;a++)
				{   String loop = ""+a;
					String status =""+dataJson.getJSONObject(""+a).getJSONObject(info[a]).get("status");
					if(status.equals("2")||status.equals("3"))
					{
					sellerName[trueCount] =( String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("Rsename");
					goodsName[trueCount] = (String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("name");
					price[trueCount] ="�۸�:"+ dataJson.getJSONObject(loop).getJSONObject(info[a]).get("price");
				    date[trueCount] ="�µ�ʱ��:"+ (String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("date");
				    if(status.equals("2"))
				    {
				    	image[trueCount] = R.drawable.querensongdao;
				    }
				    cId[trueCount] = info[a];
				    trueCount++;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        
        

        Log.i("15",""+s);

        }	}
			};
	webserviceThread.start();
    webserviceThread.join();
    try {
		
		for (int x = 0; x < trueCount ; x++) {
			Map<String, String> map = new HashMap<String, String>();// ����map����
			map.put("imageItem",null);
			
			map.put("title",  goodsName[x]);
			map.put("id", cId[x]);
			map.put("price", price[x]);
			map.put("status", dingdanstatus[x]);
			if(image[x] > 0)
			{
				Log.i("image","image1");
				map.put("image", String.valueOf(image[x]));
			}

			map.put("date", date[x]);
			
			this.list.add(map);
			
			}
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
    
    Log.i("add","add1");
    listView = (ListView)this.findViewById(R.id.songdaitem);
    
	
	this.simpleAdapter = new SimpleAdapter(this,
			list,
			R.layout.list_itemdingdan,
			new String[]{"image","title","status","price","date"},
			new int[]{R.id.querenDingdan,R.id.titleDingdan,R.id.infoDingdan,R.id.priceDingdan,R.id.dingdandate});
	Log.i("add","add2");
	
	//listView.post(new Runnable() {//����һ�ָ����ķ�����Ϣ��ui�̵߳ķ�����
		           
		             //@Override
		               //public void run() {//run()��������ui�߳�ִ��
		            	 //simpleAdapter.notifyDataSetChanged();   //����֪ͨui�Ѹı�
		            	 listView.setAdapter(simpleAdapter);
		            	 Log.i("add","add3");
		            	 
		              //}
		           //});

}

//��״̬Ϊ2�Ķ�����Ϊ3����ɡ�
void checkTo2(final String UpdateId,final int status)
{
	Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
	    
		
		public void run(){ 

		 
			app = (MyApplication)getApplication();
	
    // �����ռ�  
			String nameSpace = app.getNameSpace(); 
			String endPoint = app.getEndPoint();  
	
  // ���õķ�������  

    String methodName = "upStatus";  
   
    // EndPoint  
    if(status >= 3){
    	Log.i("���ش���","���󣡣�");
    	finish();
    }
      
    
    int orderId = Integer.parseInt(UpdateId);
    
   


    // ָ��WebService�������ռ�͵��õķ�����  

   SoapObject rpc = new SoapObject(nameSpace, methodName);  
 


    // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
    rpc.addProperty("orderId", orderId);  
    rpc.addProperty("status", status);
    
    
    
   


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
			
			
		}
		if(detail.toString().equals("YES"))
		{
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		    			}
		}
	};
webserviceThread.start();
	
}



    
}
