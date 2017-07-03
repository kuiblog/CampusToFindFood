package com.example.FindFood;

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
import android.app.ActivityManager;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;


public class city extends Activity{
	private ImageButton mCancel =null;
	private MyApplication app;
	private Object detail;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private String[] address = new String [50];
	private String[] goodsName = new String [50];
	private String[] sellerTelphone = new String [50];
	private String[] distance = new String[50];
	private String[] sellerId = new String[50];
	private int[] image = new int[50];
	//private String [] groups1 = new String[]{"北京","上海","青岛","西安","武汉","哈尔滨"};
	private String[][] children= new String[][]{{"北京大学","清华大学","北京邮电大学","北京航空航天大学","北京科技大学","中国人民大学"},{"上海大学","上海交通大学","复旦大学",""},{"青岛大学","中国海洋大学","中国石油大学","青岛科技大学","青岛理工大学","青岛农业大学"},{"西安大学","西安电子科技大学","长安大学"},{"武汉大学","武汉科技大学","武汉理工大学","华中科技大学","中国地质大学","中南政法大学"},{"黑龙江大学","哈尔滨工业大学","哈尔滨商业大学","哈尔滨工程大学"}};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_city);
		Log.i("1","1");
		final ExpandableListView view =(ExpandableListView)findViewById(R.id.list);
		
			  
			               

		
		        //定义一个List，该List对象为一级条目提供数据
				List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
				Map<String, String> group1 = new HashMap<String, String>();
				group1.put("group", "北京");
				Map<String, String> group2 = new HashMap<String, String>();
				group2.put("group", "上海");
				Map<String, String> group3 = new HashMap<String, String>();
				group3.put("group", "青岛");
				Map<String, String> group4 = new HashMap<String, String>();
				group4.put("group", "西安");
				Map<String, String> group5 = new HashMap<String, String>();
				group5.put("group", "武汉");
				Map<String, String> group6 = new HashMap<String, String>();
				group6.put("group", "哈尔滨");
				groups.add(group1);
				groups.add(group2);
				groups.add(group3);
				groups.add(group4);
				groups.add(group5);
				groups.add(group6);
				
				//北京
				List<Map<String, String>> child1 = new ArrayList<Map<String, String>>();
				Map<String, String> child1Data1 = new HashMap<String, String>();
				child1Data1.put("child", "北京大学");
				child1.add(child1Data1);
				Map<String,String> child1Data2 = new HashMap<String,String>();
				child1Data2.put("child", "清华大学");
				child1.add(child1Data2);
				Map<String, String> child1Data3 = new HashMap<String, String>();
				child1Data3.put("child", "北京邮电大学");
				child1.add(child1Data3);
				Map<String, String> child1Data4 = new HashMap<String, String>();
				child1Data4.put("child", "北京航空航天大学");
				child1.add(child1Data4);
				Map<String, String> child1Data5 = new HashMap<String, String>();
				child1Data5.put("child", "北京科技大学");
				child1.add(child1Data5);
				Map<String, String> child1Data6 = new HashMap<String, String>();
				child1Data6.put("child", "中国人民大学");
				child1.add(child1Data6);
				
				
				//上海
				List<Map<String, String>> child2 = new ArrayList<Map<String, String>>();
				Map<String, String> child2Data1 = new HashMap<String, String>();
				child2Data1.put("child", "上海大学");
				child2.add(child2Data1);
				Map<String, String> child2Data2 = new HashMap<String, String>();
				child2Data2.put("child", "上海交通大学");
				child2.add(child2Data2);
				Map<String, String> child2Data3 = new HashMap<String, String>();
				child2Data3.put("child", "复旦大学");
				child2.add(child2Data3);
				
				//青岛
				List<Map<String, String>> child3 = new ArrayList<Map<String, String>>();
				Map<String, String> child3Data1 = new HashMap<String, String>();
				child3Data1.put("child", "青岛大学");
				child3.add(child3Data1);
				Map<String, String> child3Data2 = new HashMap<String,String>();
				child3Data2.put("child", "中国海洋大学");
				child3.add(child3Data2);
				Map<String, String> child3Data3 = new HashMap<String, String>();
				child3Data3.put("child", "中国石油大学");
				child3.add(child3Data3);
				Map<String, String> child3Data4 = new HashMap<String, String>();
				child3Data4.put("child", "青岛科技大学");
				child3.add(child3Data4);
				Map<String, String> child3Data5 = new HashMap<String, String>();
				child3Data5.put("child", "青岛理工大学");
				child3.add(child3Data5);
				Map<String, String> child3Data6 = new HashMap<String, String>();
				child3Data6.put("child", "青岛农业大学");
				child3.add(child3Data6);
				
				//西安
				List<Map<String, String>> child4 = new ArrayList<Map<String, String>>();
				Map<String, String> child4Data1 = new HashMap<String, String>();
				child4Data1.put("child", "西安交通大学");
				child4.add(child4Data1);
				Map<String, String> child4Data2 = new HashMap<String,String>();
				child4Data2.put("child", "西安电子科技大学");
				child4.add(child4Data2);
				Map<String, String> child4Data3 = new HashMap<String, String>();
				child4Data3.put("child", "长安大学");
				child4.add(child4Data3);
				
				//武汉
				List<Map<String, String>> child5 = new ArrayList<Map<String, String>>();
				Map<String, String> child5Data1 = new HashMap<String, String>();
				child5Data1.put("child", "武汉大学");
				child5.add(child5Data1);
				Map<String, String> child5Data2 = new HashMap<String,String>();
				child5Data2.put("child", "武汉科技大学");
				child5.add(child5Data2);
				Map<String, String> child5Data3 = new HashMap<String, String>();
				child5Data3.put("child", "武汉理工大学");
				child5.add(child5Data3);
				Map<String, String> child5Data4 = new HashMap<String, String>();
				child5Data4.put("child", "华中科技大学");
				child5.add(child5Data4);
				Map<String, String> child5Data5 = new HashMap<String, String>();
				child5Data5.put("child", "中国地质大学");
				child5.add(child5Data5);
				Map<String, String> child5Data6 = new HashMap<String, String>();
				child5Data6.put("child", "中南财经政法大学");
				child5.add(child5Data6);
				
				//哈尔滨
				List<Map<String, String>> child6 = new ArrayList<Map<String, String>>();
				Map<String, String> child6Data1 = new HashMap<String, String>();
				child6Data1.put("child", "黑龙江大学");
				child6.add(child6Data1);
				Map<String, String> child6Data2 = new HashMap<String,String>();
				child6Data2.put("child", "哈尔滨工业大学");
				child6.add(child6Data2);
				Map<String, String> child6Data3 = new HashMap<String, String>();
				child6Data3.put("child", "哈尔滨商业大学");
				child6.add(child6Data3);
				Map<String, String> child6Data4 = new HashMap<String, String>();
				child6Data4.put("child", "哈尔滨工程大学");
				child6.add(child6Data4);
								
				//定义一个List，该List对象用来存储所有的二级条目的数据
				List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
				childs.add(child1);
				childs.add(child2);
				childs.add(child3);
				childs.add(child4);
				childs.add(child5);
				childs.add(child6);
				

				//生成一个SimpleExpandableListAdapter对象
				//1.context
				//2.一级条目的数据
				//3.用来设置一级条目样式的布局文件
				//4.指定一级条目数据的key
				//5.指定一级条目数据显示控件的id
				//6.指定二级条目的数据
				//7.用来设置二级条目样式的布局文件
				//8.指定二级条目数据的key
				//9.指定二级条目数据显示控件的id
				view.setOnChildClickListener(new OnChildClickListener() {  
					 
		            @Override  
		            public boolean onChildClick(ExpandableListView parent, View v,  
		                    int groupPosition, int childPosition, long id) {
		            	Log.i("2","2");
		            	Object s = getChild(groupPosition,childPosition);
		            	String ss = (String) s;
		            	SharedPreferences  share = city.this.getSharedPreferences("perference", MODE_PRIVATE);  
		                Editor editor = share.edit();//取得编辑器  
		                editor.putString("selectedCity", (String) ss);//存储配置 参数1 是key 参数2 是值  
		                editor.commit();//提交刷新数据 
		            	Toast.makeText(getApplicationContext(), "您选择了"+s+",主页上将会显示该大学周边的信息，而附近选项卡仍然是您所处位置周边的商铺信息。", Toast.LENGTH_LONG).show();
		            	try {
							getInfo(s);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            	Intent intent = new Intent(city.this,MainActivity.class);
		            	intent.putExtra("toWhichTab", "0");
		            	app = (MyApplication) getApplication();
		            	app.setSelectCity((String)s);
		            	app.setFirstLoginFlag(0);
		            	app.setHomeMode(0);
		            	setResult(0x717);
		            	app.setAutoCity(0);
		            	startActivity(intent);
		            	finish();
		            	
		            	return true; 
		            	
		            }
				});
				SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
						this, groups, R.layout.group_city, new String[] { "group" },
						new int[] { R.id.groupTo }, childs, R.layout.child_school,
						new String[] { "child" }, new int[] { R.id.childTo });
				//将SimpleExpandableListAdapter对象设置给当前的ExpandableListActivity
				view.setAdapter(sela);
				
				//返回键
				mCancel = (ImageButton)findViewById(R.id.back_city);
				mCancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				finish();
				}
				});
	}
public Object getChild(int groupPosition,int childPosition)
	{
		return children[groupPosition][childPosition];
	}
	
	public void getInfo(final Object s) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
				app = (MyApplication)getApplication();
			 
				Log.i("1","1");
		
        // 命名空间  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "schoolinfo";  
       
        // EndPoint  

       
        
        
       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
        rpc.addProperty("Nearschool", s);  
        
        
       
  

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
			JSONObject  response=dataJson.getJSONObject("1");
			String s = (String) response.get("name");
			Log.i("name",""+dataJson.get("count"));
			JSONObject sa;
			
			for(int i = 0;i < Integer.parseInt(""+dataJson.get("count")) ;i++)
			{
				goodsName[i] = (String) dataJson.getJSONObject(""+i).get("name");
				Log.i("title",(String) dataJson.getJSONObject(""+i).get("name"));
				if(dataJson.getJSONObject(""+i).get("phone") != null)
					{sellerTelphone[i] = (String) dataJson.getJSONObject(""+i).get("phone");
				Log.i("phone",(String) dataJson.getJSONObject(""+i).get("phone"));}
				if(dataJson.getJSONObject(""+i).getString("address") != null)
					{address[i] = (String) dataJson.getJSONObject(""+i).get("address");
				Log.i("address",(String) dataJson.getJSONObject(""+i).get("address"));}
				image[i] = R.drawable.qianyue;
				sellerId[i] = (String) dataJson.getJSONObject(""+i).get("ac");
			}
			
			for (int x = 0; x < Integer.parseInt(""+dataJson.get("count")) ; x++) {
				Map<String, String> map = new HashMap<String, String>();// 定义map集合
				//map.put("imageItem", String.valueOf(this.pic[x]));
				
				map.put("titleItem",  goodsName[x]);
				
				map.put("phone", sellerTelphone[x]);
				
				map.put("image", String.valueOf(image[x]));

				map.put("id", sellerId[x]);
				
				map.put("address", address[x]);
				
				list.add(map);
				
				
			}
			
			app = (MyApplication)getApplication();
			app.setListFirst(list);
			app.setListFirstFlag(1);
			app.setHomeMode(0);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    			
        
  

         

        

        Log.i("15","15");

			}
			};
		webserviceThread.start();               
		
        
		
		
		
	}
	
	

}
