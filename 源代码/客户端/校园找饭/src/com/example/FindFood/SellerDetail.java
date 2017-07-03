package com.example.FindFood;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class SellerDetail extends Activity{
	private Object detail;
	String title = new String();
	private Handler handler;
	String address = new String();
	String idDis = new String();
	String phone = new String();
	private String[] info = new String [50];
	private String[] goodsName = new String [50];
	private String[] price = new String [50];
	private String[] ids = new String [50];
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private ListView listView;
	private SimpleAdapter simpleAdapter=null;  
	private MyApplication app;
	private Bitmap[] bitmap = new Bitmap[50];
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shanghudetail);
		//消息处理区
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			int message = msg.what;//Apogee不一定是String类，可以是别的类，看用户具体的应用
			if(message == 1)
		    {
				listView.setOnItemClickListener(new OnItemClickListener(){
					public void  onItemClick(AdapterView<?>listView,View view,int position,long id)
					{
						if(app.getAccountName() == null){
							AlertDialog.Builder build = new AlertDialog.Builder(SellerDetail.this); 
							build.setCancelable(false);
				            build.setTitle("找饭提示")  
				                    .setMessage("您还没有登录，登陆后可以点餐，更可定制每日定时自动点餐！")  
				                    .setPositiveButton("登陆", new DialogInterface.OnClickListener() {  
				                          
				                        @Override  
				                        public void onClick(DialogInterface dialog, int which) {  
				                            // TODO Auto-generated method stub  
				                            Intent intent = new Intent(SellerDetail.this,denglu_Activity.class);
				                            intent.putExtra("backflag", 1);
				                            startActivity(intent);
				                              
				                        }  
				                    }).setNegativeButton("注册", new DialogInterface.OnClickListener() {  
				                          
				                        @Override  
				                        public void onClick(DialogInterface dialog, int which) {  
				                            // TODO Auto-generated method stub  
				                            Intent intent = new Intent(SellerDetail.this,register.class);
				                            intent.putExtra("backflag", 1);
				                            startActivity(intent);
				                              
				                        }  
				                    })
				                    
				             .show();  
							
						}
						else
						{
						Map<String, Object>  map = (Map<String, Object>) listView.getItemAtPosition(position);
						String titleItem = (String) map.get("title");
						String price = (String) map.get("price");
						String info = (String) map.get("info");
						String idss = (String) map.get("id");
						Bitmap bitmap = (Bitmap) map.get("image"); 
						byte[] by = Bitmap2Bytes(bitmap);
			            String imagebase64 =Base64.encode(by);
						Intent intent = new Intent(SellerDetail.this,BuyActivity.class);
						Bundle bundle =  new Bundle();
						bundle.putString("titleItem", titleItem);
						bundle.putString("price", price);
						bundle.putString("info", info);
						bundle.putString("address", address);
						bundle.putString("title", title);
						bundle.putString("phone", phone);
						bundle.putString("id", idss);
						bundle.putString("base64", imagebase64);
						
						intent.putExtras(bundle);
						startActivity(intent);
						}
						
					}
					});
				
				
				
				
				
		    }

			  //根据message中的信息对主线程的UI进行改动

			  //……                                                      }

			}

			 };
		
		Log.i("sell","1");
		Intent intent = getIntent();
		
		Log.i("sell","2");
		Bundle bundle = intent.getExtras();
		if(bundle.getString("flag").equals("home"))
		{
		Log.i("sell","3");
		Map<String,String> map = (Map<String,String>) bundle.getSerializable("map");
		Log.i("sell","4");
		title = map.get("titleItem");
		Log.i("sell","5");
		address = map.get("address");
		
		phone = map.get("phone");
		Log.i("sell","7");
		
		
		Log.i("sell","8");
		
		//String image = map.get("imageItem");
		Log.i("sell","9");
		TextView shanghu = (TextView)findViewById(R.id.shanghuming);
		Log.i("sell","10");
		shanghu.setText(title);
		TextView dizhi =(TextView)findViewById(R.id.shanghu_dizhi);
		dizhi.setText(address);
		TextView dianhua = (TextView)findViewById(R.id.shanghu_dianhua);
		Log.i("sell","11");
		if(phone == null)dianhua.setText("暂无电话哦~");
		else dianhua.setText(phone);
		Log.i("sell","12");
		idDis = map.get("id");
		Log.i("id:",idDis);
		try {
			getInfo(idDis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{	
			title = (String) bundle.get("titleItem");
			Log.i("sell","5");
			address = (String) bundle.get("address");
			
			phone = (String) bundle.get("phone");
			TextView shanghu = (TextView)findViewById(R.id.shanghuming);
			Log.i("sell","10");
			shanghu.setText(title);
			TextView dizhi =(TextView)findViewById(R.id.shanghu_dizhi);
			dizhi.setText(address);
			TextView dianhua = (TextView)findViewById(R.id.shanghu_dianhua);
			Log.i("sell","11");
			if(phone == null)dianhua.setText("4008665252");
			else dianhua.setText(phone);
			Log.i("sell","12");
			idDis = (String) bundle.get("id");
			
			try {
				getInfo(idDis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		/*
		
		
		*/
		
		
	}
	
	public void getInfo(final Object s) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
				app = (MyApplication)getApplication();
			 
				Log.i("SELLER",(String) s);
				
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "resinfo";  
       
        // EndPoint  

        
        
        // SOAP Action  

       String soapAction = "http://10.1.99.188:8080/resinfo";  
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("Seller", s);  
        
        
       
  

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        
  

        envelope.bodyOut = rpc;  
        Log.i("9","9");
        // 设置是否调用的是dotNet开发的WebService  

        envelope.dotNet = true;  
        Log.i("10","10");
        // 等价于envelope.bodyOut = rpc;  

        envelope.setOutputSoapObject(rpc);  
        
        
        

        HttpTransportSE transport = new HttpTransportSE(endPoint);  
       


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
        
	
        try {
			JSONObject  dataJson=new JSONObject(detail.toString());
			
			bitmap = new Bitmap[50];
			
			for(int i = 0;i < Integer.parseInt(""+dataJson.get("count")) ;i++)
			{
				ids[i] = (String) dataJson.getJSONObject(""+i).get("id");
				goodsName[i] = (String) dataJson.getJSONObject(""+i).get("ac");
				Log.i("title",(String) dataJson.getJSONObject(""+i).get("ac"));
				if(dataJson.getJSONObject(""+i).get("price") != null)
					{price[i] = "价格"+(String) dataJson.getJSONObject(""+i).get("price");
				Log.i("price",(String) dataJson.getJSONObject(""+i).get("price"));}
				if(dataJson.getJSONObject(""+i).getString("disc") != null)
					{info[i] = (String) dataJson.getJSONObject(""+i).get("disc");
				Log.i("address",(String) dataJson.getJSONObject(""+i).get("disc"));}
				byte[] buffer = new BASE64Decoder(null).decodeReturnByte(( String) dataJson.getJSONObject(""+i).get("disphoto"));
		         //对android传过来的图片字符串进行解码   
		        bitmap[i]=BitmapFactory.decodeByteArray(buffer,0,buffer.length);
				//image[i] = R.drawable.qianyue;
			}
			
			for (int x = 0; x < Integer.parseInt(""+dataJson.get("count")) ; x++) {
				Map<String, Object> map = new HashMap<String,Object>();// 定义map集合
				//map.put("imageItem", String.valueOf(this.pic[x]));
				
				map.put("title",  goodsName[x]);
				
				map.put("price", price[x]);
				map.put("id", ids[x]);
				//map.put("image", String.valueOf(image[x]));

				map.put("info", info[x]);
				map.put("image",bitmap[x]);
				list.add(map);
				
				}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    			
        
  

         

        

        Log.i("15","15");

			}
			};
		webserviceThread.start();               
		webserviceThread.join();
		listView = (ListView)findViewById(R.id.shanghucailist);
		this.simpleAdapter = new SimpleAdapter(this,
				list,
				R.layout.list_item,
				new String[]{"image","title","info","price","distance"},
				new int[]{R.id.imageItem,R.id.titleItem,R.id.infoItem,R.id.priceItem,R.id.shouchuItem});
		this.simpleAdapter.setViewBinder(new ViewBinder() 
		{   public boolean setViewValue(View view, Object data,   String textRepresentation) 
		{   //判断是否为我们要处理的对象   
			if(view instanceof ImageView && data instanceof Bitmap)
			{   ImageView iv = (ImageView) view;   
			iv.setImageBitmap((Bitmap) data);   
			return true;   
			}
			else   
				return false;   
			}   
		});
		listView.post(new Runnable() {//另外一种更简洁的发送消息给ui线程的方法。
			           
			             @Override
			               public void run() {//run()方法会在ui线程执行
			            	 simpleAdapter.notifyDataSetChanged();   //必须通知ui已改变
			            	 listView.setAdapter(simpleAdapter);
			            	 
			              }
			           });
		
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
		
		
		
	}
	//bitmap-->byte[]
	public byte[] Bitmap2Bytes(Bitmap bm) {
		         ByteArrayOutputStream baos = new ByteArrayOutputStream();
		         bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		         return baos.toByteArray();
		     }
}
