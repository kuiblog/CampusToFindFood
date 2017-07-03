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
	//private String [] groups1 = new String[]{"����","�Ϻ�","�ൺ","����","�人","������"};
	private String[][] children= new String[][]{{"������ѧ","�廪��ѧ","�����ʵ��ѧ","�������պ����ѧ","�����Ƽ���ѧ","�й������ѧ"},{"�Ϻ���ѧ","�Ϻ���ͨ��ѧ","������ѧ",""},{"�ൺ��ѧ","�й������ѧ","�й�ʯ�ʹ�ѧ","�ൺ�Ƽ���ѧ","�ൺ����ѧ","�ൺũҵ��ѧ"},{"������ѧ","�������ӿƼ���ѧ","������ѧ"},{"�人��ѧ","�人�Ƽ���ѧ","�人����ѧ","���пƼ���ѧ","�й����ʴ�ѧ","����������ѧ"},{"��������ѧ","��������ҵ��ѧ","��������ҵ��ѧ","���������̴�ѧ"}};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_city);
		Log.i("1","1");
		final ExpandableListView view =(ExpandableListView)findViewById(R.id.list);
		
			  
			               

		
		        //����һ��List����List����Ϊһ����Ŀ�ṩ����
				List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
				Map<String, String> group1 = new HashMap<String, String>();
				group1.put("group", "����");
				Map<String, String> group2 = new HashMap<String, String>();
				group2.put("group", "�Ϻ�");
				Map<String, String> group3 = new HashMap<String, String>();
				group3.put("group", "�ൺ");
				Map<String, String> group4 = new HashMap<String, String>();
				group4.put("group", "����");
				Map<String, String> group5 = new HashMap<String, String>();
				group5.put("group", "�人");
				Map<String, String> group6 = new HashMap<String, String>();
				group6.put("group", "������");
				groups.add(group1);
				groups.add(group2);
				groups.add(group3);
				groups.add(group4);
				groups.add(group5);
				groups.add(group6);
				
				//����
				List<Map<String, String>> child1 = new ArrayList<Map<String, String>>();
				Map<String, String> child1Data1 = new HashMap<String, String>();
				child1Data1.put("child", "������ѧ");
				child1.add(child1Data1);
				Map<String,String> child1Data2 = new HashMap<String,String>();
				child1Data2.put("child", "�廪��ѧ");
				child1.add(child1Data2);
				Map<String, String> child1Data3 = new HashMap<String, String>();
				child1Data3.put("child", "�����ʵ��ѧ");
				child1.add(child1Data3);
				Map<String, String> child1Data4 = new HashMap<String, String>();
				child1Data4.put("child", "�������պ����ѧ");
				child1.add(child1Data4);
				Map<String, String> child1Data5 = new HashMap<String, String>();
				child1Data5.put("child", "�����Ƽ���ѧ");
				child1.add(child1Data5);
				Map<String, String> child1Data6 = new HashMap<String, String>();
				child1Data6.put("child", "�й������ѧ");
				child1.add(child1Data6);
				
				
				//�Ϻ�
				List<Map<String, String>> child2 = new ArrayList<Map<String, String>>();
				Map<String, String> child2Data1 = new HashMap<String, String>();
				child2Data1.put("child", "�Ϻ���ѧ");
				child2.add(child2Data1);
				Map<String, String> child2Data2 = new HashMap<String, String>();
				child2Data2.put("child", "�Ϻ���ͨ��ѧ");
				child2.add(child2Data2);
				Map<String, String> child2Data3 = new HashMap<String, String>();
				child2Data3.put("child", "������ѧ");
				child2.add(child2Data3);
				
				//�ൺ
				List<Map<String, String>> child3 = new ArrayList<Map<String, String>>();
				Map<String, String> child3Data1 = new HashMap<String, String>();
				child3Data1.put("child", "�ൺ��ѧ");
				child3.add(child3Data1);
				Map<String, String> child3Data2 = new HashMap<String,String>();
				child3Data2.put("child", "�й������ѧ");
				child3.add(child3Data2);
				Map<String, String> child3Data3 = new HashMap<String, String>();
				child3Data3.put("child", "�й�ʯ�ʹ�ѧ");
				child3.add(child3Data3);
				Map<String, String> child3Data4 = new HashMap<String, String>();
				child3Data4.put("child", "�ൺ�Ƽ���ѧ");
				child3.add(child3Data4);
				Map<String, String> child3Data5 = new HashMap<String, String>();
				child3Data5.put("child", "�ൺ����ѧ");
				child3.add(child3Data5);
				Map<String, String> child3Data6 = new HashMap<String, String>();
				child3Data6.put("child", "�ൺũҵ��ѧ");
				child3.add(child3Data6);
				
				//����
				List<Map<String, String>> child4 = new ArrayList<Map<String, String>>();
				Map<String, String> child4Data1 = new HashMap<String, String>();
				child4Data1.put("child", "������ͨ��ѧ");
				child4.add(child4Data1);
				Map<String, String> child4Data2 = new HashMap<String,String>();
				child4Data2.put("child", "�������ӿƼ���ѧ");
				child4.add(child4Data2);
				Map<String, String> child4Data3 = new HashMap<String, String>();
				child4Data3.put("child", "������ѧ");
				child4.add(child4Data3);
				
				//�人
				List<Map<String, String>> child5 = new ArrayList<Map<String, String>>();
				Map<String, String> child5Data1 = new HashMap<String, String>();
				child5Data1.put("child", "�人��ѧ");
				child5.add(child5Data1);
				Map<String, String> child5Data2 = new HashMap<String,String>();
				child5Data2.put("child", "�人�Ƽ���ѧ");
				child5.add(child5Data2);
				Map<String, String> child5Data3 = new HashMap<String, String>();
				child5Data3.put("child", "�人����ѧ");
				child5.add(child5Data3);
				Map<String, String> child5Data4 = new HashMap<String, String>();
				child5Data4.put("child", "���пƼ���ѧ");
				child5.add(child5Data4);
				Map<String, String> child5Data5 = new HashMap<String, String>();
				child5Data5.put("child", "�й����ʴ�ѧ");
				child5.add(child5Data5);
				Map<String, String> child5Data6 = new HashMap<String, String>();
				child5Data6.put("child", "���ϲƾ�������ѧ");
				child5.add(child5Data6);
				
				//������
				List<Map<String, String>> child6 = new ArrayList<Map<String, String>>();
				Map<String, String> child6Data1 = new HashMap<String, String>();
				child6Data1.put("child", "��������ѧ");
				child6.add(child6Data1);
				Map<String, String> child6Data2 = new HashMap<String,String>();
				child6Data2.put("child", "��������ҵ��ѧ");
				child6.add(child6Data2);
				Map<String, String> child6Data3 = new HashMap<String, String>();
				child6Data3.put("child", "��������ҵ��ѧ");
				child6.add(child6Data3);
				Map<String, String> child6Data4 = new HashMap<String, String>();
				child6Data4.put("child", "���������̴�ѧ");
				child6.add(child6Data4);
								
				//����һ��List����List���������洢���еĶ�����Ŀ������
				List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
				childs.add(child1);
				childs.add(child2);
				childs.add(child3);
				childs.add(child4);
				childs.add(child5);
				childs.add(child6);
				

				//����һ��SimpleExpandableListAdapter����
				//1.context
				//2.һ����Ŀ������
				//3.��������һ����Ŀ��ʽ�Ĳ����ļ�
				//4.ָ��һ����Ŀ���ݵ�key
				//5.ָ��һ����Ŀ������ʾ�ؼ���id
				//6.ָ��������Ŀ������
				//7.�������ö�����Ŀ��ʽ�Ĳ����ļ�
				//8.ָ��������Ŀ���ݵ�key
				//9.ָ��������Ŀ������ʾ�ؼ���id
				view.setOnChildClickListener(new OnChildClickListener() {  
					 
		            @Override  
		            public boolean onChildClick(ExpandableListView parent, View v,  
		                    int groupPosition, int childPosition, long id) {
		            	Log.i("2","2");
		            	Object s = getChild(groupPosition,childPosition);
		            	String ss = (String) s;
		            	SharedPreferences  share = city.this.getSharedPreferences("perference", MODE_PRIVATE);  
		                Editor editor = share.edit();//ȡ�ñ༭��  
		                editor.putString("selectedCity", (String) ss);//�洢���� ����1 ��key ����2 ��ֵ  
		                editor.commit();//�ύˢ������ 
		            	Toast.makeText(getApplicationContext(), "��ѡ����"+s+",��ҳ�Ͻ�����ʾ�ô�ѧ�ܱߵ���Ϣ��������ѡ���Ȼ��������λ���ܱߵ�������Ϣ��", Toast.LENGTH_LONG).show();
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
				//��SimpleExpandableListAdapter�������ø���ǰ��ExpandableListActivity
				view.setAdapter(sela);
				
				//���ؼ�
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
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
				app = (MyApplication)getApplication();
			 
				Log.i("1","1");
		
        // �����ռ�  
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // ���õķ�������  

        String methodName = "schoolinfo";  
       
        // EndPoint  

       
        
        
       
  

        // ָ��WebService�������ռ�͵��õķ�����  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // ���������WebService�ӿ���Ҫ�������������mobileCode��userId  
        rpc.addProperty("Nearschool", s);  
        
        
       
  

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
				Map<String, String> map = new HashMap<String, String>();// ����map����
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
