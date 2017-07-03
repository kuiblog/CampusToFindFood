package com.example.FindFood;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.UiSettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.VisibleRegion;
import com.amap.api.search.core.AMapException;
import com.amap.api.search.core.LatLonPoint;
import com.amap.api.search.poisearch.PoiItem;
import com.amap.api.search.poisearch.PoiPagedResult;
import com.amap.api.search.poisearch.PoiSearch;
import com.amap.api.search.poisearch.PoiTypeDef;
import com.amap.api.search.poisearch.PoiSearch.SearchBound;
import com.amap.wmap.util.AMapUtil;
import com.amap.wmap.util.Constants;
import com.amap.wmap.util.ToastUtil;











public class NearActivity extends FragmentActivity implements
LocationSource, AMapLocationListener,OnMarkerClickListener, InfoWindowAdapter{
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private String query = null;
	private PoiPagedResult result;
	private ProgressDialog progDialog = null;
	private Button btn;
	private int curpage = 1;
	private int cnt = 0;
	private int listCount = 0;
	private LatLonPoint llp = new LatLonPoint(100,100);
	private double latitude;
	private double longitude;
	private boolean displayFlag = false;
	private ListView listView;
	private SimpleAdapter simpleAdapter=null;
	private ListView dataList;
	private List<Map<String, Object>> listItems;
	private int[] pic = { R.drawable.paigumifan, R.drawable.gongbaojiding,
			R.drawable.yuxiangrousi,R.drawable.shiguobanfan };
	private String[] address = new String [50];
	private String[] goodsName = new String [50];
	private String[] sellerTelphone = new String [50];
	private String[] distance = new String[50];
	private MyApplication app;
	
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private Object detail;
	private String phone = new String();
	private String ids = new String();
	
	private String address1 = new String();
	private int flag = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_near);
		if(!isNetworkGpsWifiConnected())
		{
			Toast.makeText(getApplicationContext(), "网络或GPS不可用！无法定位！", Toast.LENGTH_LONG).show();
		}
		showProgressDialog();
		init();
		
	
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}

	}

	private void setUpMap() {
		// 自定义系统定位小蓝点、
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));
		myLocationStyle.strokeColor(Color.BLACK);
		myLocationStyle.strokeWidth(5);
		aMap.setMyLocationStyle(myLocationStyle);
		mAMapLocationManager = LocationManagerProxy
				.getInstance(NearActivity.this);
		aMap.setLocationSource(this);
		aMap.setMyLocationEnabled(true);
		aMap.setOnMarkerClickListener(this);
		aMap.setInfoWindowAdapter(this);
		
		
		
		
		
		
		
	
			

		// 设置为true表示系统定位按钮显示并响应点击，false表示隐藏，默认是false
		
	}
	
	
	

	@Override
	protected void onPause() {
		super.onPause();
		deactivate();
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
		if(!displayFlag)doSearchQuery("");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null) {
			mListener.onLocationChanged(aLocation);
			double latitude = aLocation.getLatitude();
			double longitude = aLocation.getLongitude();
			llp.setLatitude(latitude);
			llp.setLongitude(longitude);
			//Log.i("ok","ok");
			if(!displayFlag)doSearchQuery("");
			
			
		

		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
		}
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
		 */
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
		
		

	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}
	protected void onNewIntent(final Intent newIntent) {
		super.onNewIntent(newIntent);
		String ac = newIntent.getAction();
		if (Intent.ACTION_SEARCH.equals(ac)) {
			doSearchQuery("");
		}
	}

	/**
	 * 显示进度框
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在搜索您的附近\n");
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	
	
	protected void doSearchQuery(String st) {
		query = st;
		curpage = 1;
		cnt = 0;
		// 显示进度框
		new Thread(new Runnable() {
			public void run() {
				try {
					PoiSearch poiSearch = new PoiSearch(
							NearActivity.this, new PoiSearch.Query(
									"餐馆", PoiTypeDef.All));// 设置搜索字符串，poi搜索类型，poi搜索区域（空字符串代表全国）
					
					//Log.i(""+llp.getLatitude(),""+llp.getLongitude());
					//Log.i("2","2");
					PoiSearch.SearchBound sb = new PoiSearch.SearchBound(llp,5000);
					//Log.i("3","3");
					//Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
					poiSearch.setBound(sb);
					//Log.i("4","4");
					poiSearch.setPageSize(10);// 设置搜索每次最多返回结果数
					//Log.i("5","5");
					result = poiSearch.searchPOI();
					if (result != null) {
						cnt = result.getPageCount();
						//Log.i(""+sellerTelphone[2],""+sellerTelphone[2]);
					}
					handler.sendMessage(Message.obtain(handler,
							Constants.POISEARCH));
				} catch (AMapException e) {
					handler.sendMessage(Message
							.obtain(handler, Constants.ERROR));
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		marker.showInfoWindow();
		Log.i("spinner",marker.getSnippet());
		Log.i("title",marker.getTitle());
		Log.i("id",marker.getId()+"");
		Map<String, String> map = new HashMap<String, String>();// 定义map集合
		
		
		map.put("titleItem", marker.getTitle());
		
		map.put("address", marker.getSnippet());
		Intent intent = new Intent(NearActivity.this,SellerDetail.class);
		Bundle bundle = new Bundle();
        bundle.putSerializable("map", (Serializable) map);
        intent.putExtras(bundle);
        startActivity(intent);
        
		return true;
	}

	@Override
	public View getInfoContents(Marker arg0) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return null;
	}

	/**
	 * 一次性打印多个Marker出来
	 */
	private void addMarkers(List<PoiItem> poiItems) {
		for (int i = 0; i < poiItems.size(); i++) {
			aMap.addMarker(new MarkerOptions()
					.position(
							new LatLng(
									poiItems.get(i).getPoint().getLatitude(),
									poiItems.get(i).getPoint().getLongitude()))
					.title(poiItems.get(i).getTitle())
					.snippet(poiItems.get(i).getSnippet())
					.icon(BitmapDescriptorFactory.defaultMarker()));
			//以下为为list添加数据
			
			}

	}

	private LatLngBounds getLatLngBounds(List<PoiItem> poiItems) {
		LatLngBounds.Builder b = LatLngBounds.builder();
		for (int i = 0; i < poiItems.size(); i++) {
			b.include(new LatLng(poiItems.get(i).getPoint().getLatitude(),
					poiItems.get(i).getPoint().getLongitude()));
		}
		return b.build();
	}

	private void showPoiItem(List<PoiItem> poiItems) {
		if (poiItems != null && poiItems.size() > 0) {
			if (aMap == null)
				return;
			aMap.clear();
			LatLngBounds bounds = getLatLngBounds(poiItems);
			aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
			addMarkers(poiItems);
			displayFlag = true;
			//为list捕获数据
			for (int i = 0; i < poiItems.size(); i++){
				//Log.i("error","error");
				goodsName[i] = poiItems.get(i).toString() ;
				//Log.i("1"+i,"1"+i);
				sellerTelphone[i]=poiItems.get(i).getTel();
				//Log.i("2"+i,"2"+i);
				distance[i]="距离我有："+poiItems.get(i).getDistance()+"m";
				address[i] = poiItems.get(i).getSnippet();
				
				Log.i("diatance","3"+distance[i]);
				listCount++;
			}
			addList();
		} else {
			ToastUtil.show(getApplicationContext(), "没有搜索到数据！");
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Constants.POISEARCH) {
				dissmissProgressDialog();// 隐藏对话框

				if (result != null) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								final List<PoiItem> poiItems = result
										.getPage(1);
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										showPoiItem(poiItems);// 每页显示10个poiitem
										
									}
								});

							} catch (AMapException e) {
								e.printStackTrace();

							}
						}
					}).start();
				}

			} else if (msg.what == Constants.ERROR) {
				dissmissProgressDialog();// 隐藏对话框
				ToastUtil.show(getApplicationContext(), "搜索失败,请检查网络连接！");
			} else if (msg.what == Constants.POISEARCH_NEXT) {
				curpage++;
				new Thread(new Runnable() {

					@Override
					public void run() {
						final List<PoiItem> poiItems;
						try {
							poiItems = result.getPage(curpage);
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									showPoiItem(poiItems);// 每页显示10个poiitem
									
								}
							});
						} catch (AMapException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	};
	




	private List<Map<String, Object>> getListItems() {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < goodsName.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("image", imageIds);

		}
		// TODO Auto-generated method stub
		return null;
	}
	private void addList()
	{
	Log.i("ok","ok");
	listView = (ListView) this.findViewById(R.id.neardatalist);
	listItems = getListItems();
	this.listView = (ListView) super.findViewById(R.id.neardatalist);
	listView.setOnItemClickListener(new OnItemClickListener(){
		public void  onItemClick(AdapterView<?>listView,View view,int position,long id)
		{
			
			
			
			
			
			Map<String, String>  map = (Map<String, String>) listView.getItemAtPosition(position);
			String titleItem = map.get("titleItem");
			Log.i("titleItem",titleItem);
			phone = map.get("phone");
			if(phone.length() < 1)
			{
				phone = "13241821402";
			}
			
			address1 = map.get("address");
			int flag = judgeSeller(titleItem);
			if(flag == 0)
			{
				AlertDialog.Builder build=new AlertDialog.Builder(NearActivity.this); 
				build.setCancelable(false);
	            build.setTitle("饭小二提示")  
	                    .setMessage("非常抱歉，该商家还未与我们签约，您只能以电话方式订餐，我们将记录您的动作，尽快与该商家签约，为您提供更为优质的服务！")  
	                    .setPositiveButton("电话订餐", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                        	Intent i=new Intent("android.intent.action.CALL",Uri.parse("tel:"+phone));
	                            startActivity(i);
	                 
	                              
	                        }  
	                    })  
	                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                        	
	                        	
	                        }  
	                    })  
	                    .show();  
				}
			else
			{
				Intent intent = new Intent(NearActivity.this,SellerDetail.class);
				Bundle bundle =  new Bundle();
				bundle.putString("titleItem", titleItem);
				bundle.putString("phone", phone);
				bundle.putString("flag", "near");
				bundle.putString("address", address1);
				bundle.putString("id", ids);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
			
			
		}
		});
	
	Log.i("listdiatance",""+distance[3]);
	for (int x = 0, i = 0; x < listCount; x++) {
		Map<String, String> map = new HashMap<String, String>();// 定义map集合
		//map.put("imageItem", String.valueOf(this.pic[x]));
		map.put("titleItem",  goodsName[x]);
		map.put("phone", sellerTelphone[x]);
		//map.put("priceItem", this.data[x][1]);
		map.put("distance", distance[x]);
		map.put("address", address[x]);
		this.list.add(map);
		
	}
	this.simpleAdapter = new SimpleAdapter(this,
			this.list,
			R.layout.list_itemnear,
			new String[]{"titleItem","phone","address","distance"},
			new int[]{R.id.titleItemNear,R.id.infoItemNear,R.id.addressItemNear,R.id.priceItemNear});
	//将适配器绑定到listView上
	listView.setAdapter(simpleAdapter);
	}
	
	private boolean isNetworkGpsWifiConnected() {  
        ConnectivityManager cm =   
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo network = cm.getActiveNetworkInfo();  
        WifiManager wifiManager = (WifiManager) getApplicationContext()  
       		                 .getSystemService(Context.WIFI_SERVICE);  
        LocationManager locationManager =   
        		                ((LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE));  

        if ((network != null||wifiManager != null)&&(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {  
            return true;  
        }  
        return false;  
    }  
	
	int judgeSeller(final String titleItem)
	{
Thread webserviceThread = new Thread() {  //新建一个线程为网络数据传输，以免网络堵塞
		    
			
			public void run(){ 
			app = (MyApplication)getApplication();
			
			
			 

		
        // 命名空间  
		String nameSpace = app.getNameSpace(); 
		
      // 调用的方法名称  

        String methodName = "resinfo1";  
       
        // EndPoint  

        String endPoint = app.getEndPoint();  
        


       
  

        // 指定WebService的命名空间和调用的方法名  

       SoapObject rpc = new SoapObject(nameSpace, methodName);  
     


        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId  
       
       rpc.addProperty("disName", titleItem);  
        
        
       
  

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        
  

        envelope.bodyOut = rpc;  
        Log.i("9","9");
        // 设置是否调用的是dotNet开发的WebService  

        envelope.dotNet = true;  
        Log.i("10","10");
        // 等价于envelope.bodyOut = rpc;  

        envelope.setOutputSoapObject(rpc);  
       

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
				msg.what = 1;
				handler.sendMessage(msg);
				
			}
		
        Log.i("14",detail.toString());
        if(detail.toString().equals("NO"))
		{
        	
        	
        	 flag = 0;
        	
		    
		    			}
        else 
        {	
        	try {
				JSONObject  dataJson=new JSONObject(detail.toString());
				address1 = (String) dataJson.getJSONObject("0").get("location");
				phone = (String) dataJson.getJSONObject("0").get("selphone");
				ids =  (String) dataJson.getJSONObject("0").get("seller");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        	flag = 1;
        }
        

        // 获取返回的结果  

        

      

			}
			};
		webserviceThread.start();
		
		
		
		if(flag == 1)
		{
		
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
}

	
    
    		








