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
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
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
	 * 此方法已经废弃
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
			String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
					+ "\n精    度    :" + location.getAccuracy() + "米"
					+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
					+ AMapUtil.convertToTime(location.getTime()) + "\n城市编码:"
					+ cityCode + "\n位置描述:" + desc + "\n省:"
					+ location.getProvince() + "\n市：" + location.getCity()
					+ "\n区(县)：" + location.getDistrict() + "\n城市编码："
					+ location.getCityCode() + "\n区域编码：" + location.getAdCode());
			Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
			
			int i = 1;
						while(i == 1)
						{i = 0;
						if (desc.indexOf("北京大学") > 0)
						{
							try {
								getInfo("北京大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
						else if (desc.indexOf("清华大学") > 0)
						{
							try {
								getInfo("清华大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("北京邮电大学") > 0)
						{
							try {
								getInfo("北京邮电大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("北京航空航天大学") > 0)
						{
							try {
								getInfo("北京航空航天大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("北京人民大学") > 0)
						{
							try {
								getInfo("北京人民大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("北京科技大学") > 0)
						{
							try {
								getInfo("北京科技大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("上海大学") > 0)
						{
							try {
								getInfo("上海大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
					
						else if (desc.indexOf("上海交通大学") > 0)
						{
							try {
								getInfo("上海交通大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("复旦大学") > 0)
						{
							try {
								getInfo("复旦大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("青岛大学") > 0)
						{
							try {
								getInfo("青岛大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("中国海洋大学") > 0)
						{
							try {
								getInfo("中国海洋大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("中国石油大学") > 0)
						{
							try {
								getInfo("中国石油大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("青岛科技大学")>0 ||desc.indexOf("辽阳东路") > 0)
						{
							try {
								getInfo("青岛科技大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("青岛理工大学") > 0)
						{
							try {
								getInfo("青岛理工大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("青岛农业大学") > 0)
						{
							try {
								getInfo("青岛农业大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("西安大学") > 0)
						{
							try {
								getInfo("西安大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("西安电子科技大学") > 0)
						{
							try {
								getInfo("西安电子科技大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("武汉大学") > 0)
						{
							try {
								getInfo("武汉大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("长安大学") > 0)
						{
							try {
								getInfo("长安大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("武汉科技大学") > 0)
						{
							try {
								getInfo("武汉科技大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("武汉理工大学") > 0)
						{
							try {
								getInfo("武汉理工大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("华中科技大学") > 0)
						{
							try {
								getInfo("华中科技大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("中国地质大学") > 0)
						{
							try {
								getInfo("中国地质大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("中南政法大学") > 0)
						{
							try {
								getInfo("中南政法大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("黑龙江大学") > 0)
						{
							try {
								getInfo("黑龙江大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("哈尔滨工业大学") > 0)
						{
							try {
								getInfo("哈尔滨工业大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("哈尔滨商业大学") > 0)
						{
							try {
								getInfo("哈尔滨商业大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("贵阳") > 0)
						{
							try {
								getInfo("青岛科技大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else if (desc.indexOf("哈尔滨工程大学") > 0)
						{
							try {
								getInfo("哈尔滨工程大学");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}break;
						}
						else Toast.makeText(getApplicationContext(), "在您周边未发现与我们签约的大学", Toast.LENGTH_LONG).show();
			
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
		progDialog.setMessage("正在定位。。。。");
		progDialog.show();
	}
	
	
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
	
	public void getInfo(final Object s) throws InterruptedException
	{
		Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
				
			   // Log.i("哈尔滨商业大学",(String) s);
				Log.i("1","1");
				app = (MyApplication)getApplication();
        // 命名空间  
				app.setSelectCity((String) s);
				String nameSpace = app.getNameSpace(); 
				String endPoint = app.getEndPoint();  
		
      // 调用的方法名称  

        String methodName = "schoolinfo";  
       
        // EndPoint  

       
        
        // SOAP Action  

       String soapAction = "http://10.1.99.188:8080/schoolinfo";  
       
  

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
				id[i] = (String) dataJson.getJSONObject(""+i).get("ac");
				image[i] = R.drawable.qianyue;
			}
			
			for (int x = 0; x < Integer.parseInt(""+dataJson.get("count")) ; x++) {
				Map<String, String> map = new HashMap<String, String>();// 定义map集合
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