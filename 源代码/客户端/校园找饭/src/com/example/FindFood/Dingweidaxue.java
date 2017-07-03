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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.wmap.util.AMapUtil;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Dingweidaxue extends Activity implements AMapLocationListener{
	
	private LocationManagerProxy mAMapLocManager = null;
	private MyApplication app;
	private ProgressDialog progDialog = null;
	private Object detail;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private String[] address = new String [50];
	private String[] goodsName = new String [50];
	private String[] sellerTelphone = new String [50];
	private String[] distance = new String[50];
	private String[] id = new String[50];
	private int[] image = new int[50];
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingweidaxue);
		
		mAMapLocManager = LocationManagerProxy.getInstance(this);
		showProgressDialog();
		Log.i("1","1");
	}

	public void enableMyLocation() {
		// Location API��λ����GPS�������϶�λ��ʽ��ʱ�������5000����
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2�汾��������������true��ʾ��϶�λ�а���gps��λ��false��ʾ�����綨λ��Ĭ����true
		 */
		mAMapLocManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	public void disableMyLocation() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		enableMyLocation();
	}

	@Override
	protected void onPause() {
		disableMyLocation();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destory();
		}
		mAMapLocManager = null;
		super.onDestroy();
	}

	/**
	 * �˷����Ѿ�����
	 */
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			String cityCode = "";
			String desc = "";
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				cityCode = locBundle.getString("citycode");
				desc = locBundle.getString("desc");
			}
			String str = ("��λ�ɹ�:(" + geoLng + "," + geoLat + ")"
					+ "\n��    ��    :" + location.getAccuracy() + "��"
					+ "\n��λ��ʽ:" + location.getProvider() + "\n��λʱ��:"
					+ AMapUtil.convertToTime(location.getTime()) + "\n���б���:"
					+ cityCode + "\nλ������:" + desc + "\nʡ:"
					+ location.getProvince() + "\n�У�" + location.getCity()
					+ "\n��(��)��" + location.getDistrict() + "\n���б��룺"
					+ location.getCityCode() + "\n������룺" + location.getAdCode());
			Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
			
			int i = 1;
						while(i == 1)
						{i = 0;
						if (desc.indexOf("������ѧ") > 0)
						{
							try {
								getInfo("������ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
						else if (desc.indexOf("�廪��ѧ") > 0)
						{
							try {
								getInfo("�廪��ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�����ʵ��ѧ") > 0)
						{
							try {
								getInfo("�����ʵ��ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�������պ����ѧ") > 0)
						{
							try {
								getInfo("�������պ����ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("���������ѧ") > 0)
						{
							try {
								getInfo("���������ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�����Ƽ���ѧ") > 0)
						{
							try {
								getInfo("�����Ƽ���ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�Ϻ���ѧ") > 0)
						{
							try {
								getInfo("�Ϻ���ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
					
						else if (desc.indexOf("�Ϻ���ͨ��ѧ") > 0)
						{
							try {
								getInfo("�Ϻ���ͨ��ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("������ѧ") > 0)
						{
							try {
								getInfo("������ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�ൺ��ѧ") > 0)
						{
							try {
								getInfo("�ൺ��ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�й������ѧ") > 0)
						{
							try {
								getInfo("�й������ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�й�ʯ�ʹ�ѧ") > 0)
						{
							try {
								getInfo("�й�ʯ�ʹ�ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�ൺ�Ƽ���ѧ")>0 ||desc.indexOf("������·") > 0)
						{
							try {
								getInfo("�ൺ�Ƽ���ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�ൺ����ѧ") > 0)
						{
							try {
								getInfo("�ൺ����ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�ൺũҵ��ѧ") > 0)
						{
							try {
								getInfo("�ൺũҵ��ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("������ѧ") > 0)
						{
							try {
								getInfo("������ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�������ӿƼ���ѧ") > 0)
						{
							try {
								getInfo("�������ӿƼ���ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�人��ѧ") > 0)
						{
							try {
								getInfo("�人��ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("������ѧ") > 0)
						{
							try {
								getInfo("������ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�人�Ƽ���ѧ") > 0)
						{
							try {
								getInfo("�人�Ƽ���ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�人����ѧ") > 0)
						{
							try {
								getInfo("�人����ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("���пƼ���ѧ") > 0)
						{
							try {
								getInfo("���пƼ���ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("�й����ʴ�ѧ") > 0)
						{
							try {
								getInfo("�й����ʴ�ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("����������ѧ") > 0)
						{
							try {
								getInfo("����������ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("��������ѧ") > 0)
						{
							try {
								getInfo("��������ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("��������ҵ��ѧ") > 0)
						{
							try {
								getInfo("��������ҵ��ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("��������ҵ��ѧ") > 0)
						{
							try {
								getInfo("��������ҵ��ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("����") > 0)
						{
							try {
								getInfo("�ൺ�Ƽ���ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("���������̴�ѧ") > 0)
						{
							try {
								getInfo("���������̴�ѧ");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else Toast.makeText(getApplicationContext(), "�����ܱ�δ����������ǩԼ�Ĵ�ѧ", Toast.LENGTH_LONG).show();
			
						}
			
			dissmissProgressDialog();
			double [] ll ={geoLat,geoLng};
			app = (MyApplication)getApplication();
			app.setSelectedCityLatlon(ll);
			app.setFirstLoginFlag(0);
			Intent intent = new Intent(Dingweidaxue.this,MainActivity.class);
			intent.putExtra("toWhichTab", "0");
        	setResult(0x717);
        	app.setHomeMode(1);
        	startActivity(intent);
        	finish();
		
		}
	}
	
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("���ڶ�λ��������");
		progDialog.show();
	}
	
	
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
	
	public void getInfo(final Object s) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
		    
			
			public void run(){ 
				
			   // Log.i("��������ҵ��ѧ",(String) s);
				Log.i("1","1");
				app = (MyApplication)getApplication();
        // �����ռ�  
				app.setSelectCity((String) s);
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // ���õķ�������  

        String methodName = "schoolinfo";  
       
        // EndPoint  

       
        
        // SOAP Action  

       String soapAction = "http://10.1.99.188:8080/schoolinfo";  
       
  

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
				id[i] = (String) dataJson.getJSONObject(""+i).get("ac");
				image[i] = R.drawable.qianyue;
			}
			
			for (int x = 0; x < Integer.parseInt(""+dataJson.get("count")) ; x++) {
				Map<String, String> map = new HashMap<String, String>();// ����map����
				//map.put("imageItem", String.valueOf(this.pic[x]));
				
				map.put("titleItem",  goodsName[x]);
				
				map.put("phone", sellerTelphone[x]);
				
				map.put("image", String.valueOf(image[x]));
				
				map.put("id", id[x]);

				
				
				map.put("address", address[x]);
				
				list.add(map);
				
				
			}
			
			app = (MyApplication)getApplication();
			app.setListFirst(list);
			app.setListFirstFlag(1);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    			
        
  

         

        

        Log.i("15","15");

			}
			};
		webserviceThread.start();               
		webserviceThread.join();
        
		
		
		
	}
	
	
	
	
}