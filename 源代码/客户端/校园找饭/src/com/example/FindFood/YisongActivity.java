package com.example.FindFood;

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

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class YisongActivity extends Activity{
	private ListView listView;
	private SimpleAdapter simpleAdapter=null;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private MyApplication app;
	private Object detail;
	private List<Map<String, Object>> listItems;
	private String[] date = new String [50];
	private String[] goodsName = new String [50];
	private String[] price = new String [50];
	private String[] sellerName = new String[50];
	private int[] status = new int[50];
	private String []dingdanstatus = new String[50];
	private int count = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (MyApplication)getApplication();
		Log.i("seller","3");
		setContentView(R.layout.yisongitem);
		Log.i("seller","4");
		String[] s = app.getSId();
		Log.i("seller","5");
		try {
			getSellerinfo(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void getSellerinfo(final String[] info) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
				Log.i("seller","6");
				app = (MyApplication)getApplication();
				Log.i("run","run1");
				JSONObject jSId = new JSONObject();
				Log.i("run","run2");
				
				
				JSONArray ar = new JSONArray();
				int i = 0;
				for(i = 0; i < app.getSIdCount() ; i++) {
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
					
					jSId.put("Count",i+"");
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					jSId.put("id", ar);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Log.i("jSId",jSId.toString());
				
		
        // �����ռ�  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // ���õķ�������  

        String methodName = "getfoodinfo2";  
       
        // EndPoint  

          
        
        // SOAP Action  

       String soapAction = "http://211.87.147.177/selget";  
       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
       rpc.addProperty("OrderId",jSId.toString());   
       
  

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
				Toast.makeText(YisongActivity.this, "�������Ӵ���������������", Toast.LENGTH_SHORT).show();
				
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Looper.prepare();
        	Toast.makeText(YisongActivity.this, "�û������������", Toast.LENGTH_SHORT).show();
		    Looper.loop(); 
		    			}
        else 
        {	
        	Log.i("detail",detail.toString());
        	JSONObject dataJson = null;
			try {
				dataJson = new JSONObject(detail.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int i1 = 0;
        	try {
        		for(int a = 0;a < app.getSIdCount()  ;a++)
				{  	
        			String status =""+dataJson.getJSONObject(""+a).getJSONObject(info[a]).get("status");
					if(status.equals("3")||status.equals("2"))
					{	
						Log.i("enter","enter");
						if(status.equals("3"))
						{
						
							dingdanstatus[i1] =  "����ɵĶ���";
						}
						else if(status.equals("2"))
						{
							
							dingdanstatus[i1] =  "���ͳ��Ķ���";
						}
						String loop = ""+a;
						goodsName[i1] = (String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("name");
						price[i1] ="�۸�:"+ dataJson.getJSONObject(loop).getJSONObject(info[a]).get("price");
					    date[i1] ="�µ�ʱ��:"+ (String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("date");
						count++;
						i1++;
					}
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
  

        // ��ȡ���صĽ��  
        	
        

        

			}
			};
		webserviceThread.start();
		webserviceThread.join();
		
		 try {
				
				for (int x = 0; x < count ; x++) {
					Map<String, String> map = new HashMap<String, String>();// ����map����
					map.put("imageItem",null);
					
					map.put("title",  goodsName[x]);
					
					map.put("price", price[x]);
					map.put("status", dingdanstatus[x]);
					//map.put("image", String.valueOf(image[x]));

					map.put("date", date[x]);
					
					this.list.add(map);
					
					}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    
		    Log.i("add","add1");
		    listView = (ListView)this.findViewById(R.id.yisongitem);
		    
			
			this.simpleAdapter = new SimpleAdapter(this,
					list,
					R.layout.list_itemdingdan,
					new String[]{"imageItem","title","status","price","date"},
					new int[]{R.id.imageDingdan,R.id.titleDingdan,R.id.infoDingdan,R.id.priceDingdan,R.id.dingdandate});
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


			
		
		
	}
