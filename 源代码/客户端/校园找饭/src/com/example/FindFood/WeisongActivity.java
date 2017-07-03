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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WeisongActivity extends Activity{
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
	private Handler handler;
	private String [] sId = new String[50];
	private String [] id = new String[50];
	private int first = 1;
	private String UpdateId = new String();
	private ProgressDialog progDialog = null;
	private String updateStatus = new String();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (MyApplication)getApplication();
		Log.i("get","get");
		try {
			getAccounter(app.getSellerName(),app.getSellerPassword());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.i("sellernew","3");
		setContentView(R.layout.weisongitem);
		ImageButton shuaxin = (ImageButton) findViewById(R.id.shuaxin);
		shuaxin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					getAccounter(app.getSellerName(),app.getSellerPassword());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Log.i("shuaxin","shuaxin");
				list.clear();
				detail = new Object();
				Log.i("shuaxin","shuaxin");
				String []s = app.getSId();
				
				
				
				
				showProgressDialog();
				try {
					getSellerinfo(s);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		});
		Button allCheck = (Button)findViewById(R.id.checktoyisong);
		allCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					allUpdate(app.getSellerName());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		});
		Log.i("seller","4");
		String[] s = app.getSId();
		Log.i("seller","5");
		try {
			getSellerinfo(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listView = (ListView) this.findViewById(R.id.weisongitem);
		listView.setOnItemClickListener(new OnItemClickListener(){
			public void  onItemClick(AdapterView<?>listView,View view,int position,long id)
			{
				Map<String, String>  map = (Map<String, String>) listView.getItemAtPosition(position);
				UpdateId = map.get("id");
				updateStatus =  map.get("status");	
				String statusS = new String();
				int flag = 0;
				if(updateStatus.equals("未确认订单，请单击以确认。"))
				{
					flag = 0;
					updateStatus =  "0";
				}
				else if(updateStatus.equals("已确认订单，单击变更状态为已送"))
				{
					flag = 1;
					updateStatus =  "1";
				}
				if(flag == 0)statusS = "将本单的状态置为已确认。注意:此操作不可逆！请谨慎操作,虚假订单会给您的店铺带来损失!";
				else if(flag == 1)statusS = "将本单的状态置为已送出。注意:此操作不可逆！请谨慎操作,虚假订单会给您的店铺带来损失!";
				
				AlertDialog.Builder build = new AlertDialog.Builder(WeisongActivity.this); 
				//build.setCancelable(false);
	            build.setTitle("找饭提示")  
	                    .setMessage(statusS)  
	                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                        	app = (MyApplication)getApplication();
	                        	int status = Integer.parseInt(updateStatus);
	                        	checkTo2(UpdateId,status);
	                        	try {
									getSellerinfo(app.getSId());
								} catch (InterruptedException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
	                        	
	                        	
	                        	try {
	            					getAccounter(app.getSellerName(),app.getSellerPassword());
	            				} catch (InterruptedException e1) {
	            					// TODO Auto-generated catch block
	            					e1.printStackTrace();
	            				}
	            				Log.i("shuaxin","shuaxin");
	            				list.clear();
	            				detail = new Object();
	            				Log.i("shuaxin","shuaxin");
	            				String []s = app.getSId();
	            				
	            				
	            				
	            				showProgressDialog();
	            				try {
	            					getSellerinfo(s);
	            				} catch (InterruptedException e) {
	            					// TODO Auto-generated catch block
	            					e.printStackTrace();
	            				}
	                              
	                        }  
	                    })  
	                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            
	                        	
	                        	
	                        }  
	                    })  
	                    .show();  
				
				
				
				
			}

			
			
		});
		
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			if(msg.what == 0)
				Toast.makeText(WeisongActivity.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
			if(msg.what == 1)
				{
				Toast.makeText(WeisongActivity.this, "订单状态已更改！现在将刷新页面", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(WeisongActivity.this,WeisongActivity.class);
				startActivity(intent);
				finish();
				}
			  //根据message中的信息对主线程的UI进行改动

			  //……                                                      }

			}

			 };

		
	}
	
	
	public void getSellerinfo(final String[] info) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
				Log.i("seller","6");
				app = (MyApplication)getApplication();
				Log.i("run","run1");
				JSONObject jSId = new JSONObject();
				Log.i("run","run2");
				
				if(first == 0)
				{
					goodsName = new String[50];
					id= new String[50];
					price= new String[50];
					dingdanstatus= new String[50];
					//map.put("image", String.valueOf(image[x]));
					
					date = new String [50];
					
				}
				
				
				JSONArray ar = new JSONArray();
				int i = 0;
				for(i = 0; i < app.getSIdCount()  ; i++) {
					// 声明一个JSON对象
					
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
				
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "getfoodinfo2";  
       
        // EndPoint  

          
        
    
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
       rpc.addProperty("OrderId",jSId.toString());   
       
  

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

  
        
      // 获取返回的数据  

        
		
			try {
				detail = (Object) envelope.getResponse();
			} catch (SoapFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(WeisongActivity.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
				
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	Looper.prepare();
        	Toast.makeText(WeisongActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
		    Looper.loop(); 
		    			}
        else 
        {	
        	Log.i("detail",detail.toString());
        	if(first == 0)
        	{
        		
        		
        	}
        	JSONObject dataJson = null;
			try {
				dataJson = new JSONObject(detail.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Log.i("delete",""+app.getSIdCount());
			int i1 = 0;
        	try {
        		for(int a = 0;a < app.getSIdCount()   ;a++)
				{  	Log.i("delete1","delete1");
        			String status =""+dataJson.getJSONObject(""+a).getJSONObject(info[a]).get("status");
					if(status.equals("0")||status.equals("1"))
					{	Log.i("delete2","delete2");
						if(status.equals("0"))
						{
							dingdanstatus[i1] =  "未确认订单，请单击以确认。";
						
						}
						else
						{
							dingdanstatus[i1] =  "已确认订单，单击变更状态为已送";
							
						}
						String loop = ""+a;
						goodsName[i1] = (String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("name");
						price[i1] ="价格:"+ dataJson.getJSONObject(loop).getJSONObject(info[a]).get("price");
					    date[i1] ="下单时间:"+ (String) dataJson.getJSONObject(loop).getJSONObject(info[a]).get("date");
						id[i1] = info[a];
					    count++;
						i1++;
					}
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 i1 = 0;
        	 
        	 
        }
  

        // 获取返回的结果  
        	
        
       
        

			}
			};
		webserviceThread.start();
		webserviceThread.join();
		
		 try {
				
				for (int x = 0; x < count ; x++) {
					Map<String, String> map = new HashMap<String, String>();// 定义map集合
					map.put("imageItem",null);
					
					map.put("title",  goodsName[x]);
					map.put("id", id[x]);
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
		    listView = (ListView)this.findViewById(R.id.weisongitem);
		    
			
			this.simpleAdapter = new SimpleAdapter(this,
					list,
					R.layout.list_itemdingdan,
					new String[]{"imageItem","title","status","price","date"},
					new int[]{R.id.imageDingdan,R.id.titleDingdan,R.id.infoDingdan,R.id.priceDingdan,R.id.dingdandate});
			Log.i("add","add2");
			
			listView.post(new Runnable() {//另外一种更简洁的发送消息给ui线程的方法。
				           
				             @Override
				               public void run() {//run()方法会在ui线程执行
				            	 if(first == 0)
								 {
				            		 simpleAdapter.notifyDataSetChanged();//必须通知ui已改变
				            		 dissmissProgressDialog();
								 }
				            	 listView.setAdapter(simpleAdapter);
				            	 Log.i("add","add3");
				             
				              }
				           });
			
			first = 0;

		}


			
		
		
			
		//重新调取最新的商家订单数据
			public void getAccounter(final String sellerNumberS,final String sellerPasswordS) throws InterruptedException {  
				
				Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
				    
					
					public void run(){ 

					 Log.i("get","get");
						app = (MyApplication)getApplication();
				
		        // 命名空间  
						String nameSpace = app.getNameSpace(); 
						String endPoint = app.getEndPoint();  
				
		      // 调用的方法名称  

		        String methodName = "selget";  
		       
		        // EndPoint  

		          
		        
		        // SOAP Action  

		       String soapAction = "http://211.87.147.177/selget";  
		       
		  

		        // 指定WebService的命名空间和调用的方法名  

		       SoapObject rpc = new SoapObject(nameSpace, methodName);  
		     


		        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
		        rpc.addProperty("Selname", sellerNumberS);  
		        
		        rpc.addProperty("Selpassword", sellerPasswordS);  
		       
		  

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

		  
		        
		      // 获取返回的数据  

		        Log.i("Conname","2");
				
					try {
						detail = (Object) envelope.getResponse();
					} catch (SoapFault e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(WeisongActivity.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
						
					}
				
		        
		        if(detail.toString().equals("NO"))
				{
		        	Looper.prepare();
		        	Toast.makeText(WeisongActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
				    Looper.loop(); 
				    			}
		        else 
		        {	
		        	
		        	Log.i("Conname","3");
		        	
		        	JSONObject dataJson = null;
				    try {
						dataJson = new JSONObject(detail.toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    Log.i("Conname","4");
		            JSONArray data = null;
					try {
						data = dataJson.getJSONArray("oidObject");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		          
		        
		            try {
						for(int i = 0;i < Integer.parseInt(""+dataJson.get("count"));i++ )
						{
						JSONObject info=data.getJSONObject(i);
						String id=info.getString(""+i);
						
						
						sId[i] = id;
						}
						app.setSIdCount(Integer.parseInt(""+dataJson.get("count")));
						 Log.i("Conname","5");	
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	app.setSId(sId);
		        	
		            
		        	
		        	
		        }
		  

		        // 获取返回的结果  

		        

		        

					}
					};
				webserviceThread.join();
			
			}
			
	void allUpdate(final String sellerName) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 

			 
				app = (MyApplication)getApplication();
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "ship";  
       
        // EndPoint  

          
        
        
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("Seller", sellerName);  
        
        
       
  

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

  
        
      // 获取返回的数据  

        
		
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
				msg.what = 4;
				handler.sendMessage(msg);
			    			}
			}
		};
	webserviceThread.start();
	}
	
	
	void checkTo2(final String UpdateId,final int status)
	{
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 

			 
				app = (MyApplication)getApplication();
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "upStatus";  
       
        // EndPoint  
        if(status >= 3){
        	Log.i("严重错误","错误！！");
        	finish();
        }
          
        
        int orderId = Integer.parseInt(UpdateId);
        
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     
       Log.i("orderId",""+ orderId);
       Log.i("status",""+ status);
        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("orderId", orderId);  
        rpc.addProperty("status", status);
        
        
        
       
  

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

  
        
      // 获取返回的数据  

        
		
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
			
			
	
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在刷新.......");
		progDialog.show();
	}
	
	
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
	}
	

