package com.example.FindFood;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
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
import com.amap.api.maps.MapView;
import com.amap.api.search.core.AMapException;
import com.amap.api.search.core.LatLonPoint;
import com.amap.api.search.poisearch.PoiItem;
import com.amap.api.search.poisearch.PoiPagedResult;
import com.amap.api.search.poisearch.PoiSearch;
import com.amap.api.search.poisearch.PoiTypeDef;
import com.amap.wmap.util.AMapUtil;
import com.amap.wmap.util.Constants;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class HomeActivity extends Activity  {
	
	private String[] address = new String [50];
	private String[] goodsName = new String [50];
	private String[] sellerTelphone = new String [50];
	private String[] distance = new String[50];
	private int [] imageFlag = new int[50];
	private ProgressDialog progDialog = null;
	private PoiPagedResult result;
	private LatLonPoint llp = new LatLonPoint(39.90923,116.397428);
	private Object detail;
	
	private String[] address1 = new String [50];
	private String[] goodsName1 = new String [50];
	private String[] sellerTelphone1 = new String [50];
	
	private String[] sellerId1 = new String[50];
	private int[] image1 = new int[50];
	
	private final int CODE = 0x717;
	private ImageButton searchButton = null;
	private int listCount = 0;
	private ImageButton cityButton = null;
	private ListView listView;
	private SimpleAdapter simpleAdapter=null;  
	private ListView dataList;
	private List<Map<String, Object>> listItems;
	
	private int[] blank = { R.drawable.blank, R.drawable.blank,
			R.drawable.blank,R.drawable.blank };
	private String []cId = new String [50];
	
	
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // �������е�List����
	private List<Map<String, String>> list1 = new ArrayList<Map<String, String>>();
    private int loginFlag = 0;
    
    private ViewPager viewPager; // android-support-v4�еĻ������  
    private List<ImageView> imageViews; // ������ͼƬ����  
    private MyApplication app;
      
    private String[] titles; // ͼƬ����  
    private int[] imageResId; // ͼƬID 
    private Bitmap[] image;
    private List<View> dots; // ͼƬ�������ĵ���Щ��  
      
    private TextView tv_title;  
    private int currentItem = 0; // ��ǰͼƬ��������  
    private Bitmap[] bitmap = new Bitmap[5];
    private String[] base64 = new String[5]; //�ݴ�base64����ͼƬ����
      
        // An ExecutorService that can schedule commands to run after a given delay,  
       // or to execute periodically.  
    private ScheduledExecutorService scheduledExecutorService; 
    private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(currentItem);
			
			if (msg.what == Constants.REOCODER_RESULT) {
				progDialog.dismiss();
				Toast.makeText(getApplicationContext(), app.getSelectCity(),
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constants.ERROR) {
				progDialog.dismiss();
				Toast.makeText(getApplicationContext(), "�������������Ƿ���ȷ?",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
    

  
    	    private class MyPageChangeListener implements OnPageChangeListener {
    			private int oldPosition = 0;

    			/**
    			 * This method will be invoked when a new page becomes selected.
    			 * position: Position index of the new selected page.
    			 */
    			public void onPageSelected(int position) {
    				
    				currentItem = position;
    				tv_title.setText(titles[position]);
    				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
    				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
    				oldPosition = position;
    			}

    			public void onPageScrollStateChanged(int arg0) {

    			}

    			public void onPageScrolled(int arg0, float arg1, int arg2) {

    			}
    		}

    		/**
    		 * ���ViewPagerҳ���������
    		 * 
    		 * @author Administrator
    		 * 
    		 */
    		private class MyAdapter extends PagerAdapter {

    			@Override
    			public int getCount() {
    				
    				return imageResId.length;
    			}

    			@Override
    			public Object instantiateItem(View arg0, int arg1) {
    				((ViewPager) arg0).addView(imageViews.get(arg1));
    				
    				return imageViews.get(arg1);
    			}

    			@Override
    			public void destroyItem(View arg0, int arg1, Object arg2) {
    				((ViewPager) arg0).removeView((View) arg2);
    			}

    			@Override
    			public boolean isViewFromObject(View arg0, Object arg1) {
    				return arg0 == arg1;
    			}

    			@Override
    			public void restoreState(Parcelable arg0, ClassLoader arg1) {

    			}

    			@Override
    			public Parcelable saveState() {
    				return null;
    			}

    			@Override
    			public void startUpdate(View arg0) {

    			}

    			@Override
    			public void finishUpdate(View arg0) {

    			}
    		}
    	    
    	    
    	    
    	    /**
    		 * �����л�����
    		 * 
    		 * @author Administrator
    		 * 
    		 */
    private class ScrollTask implements Runnable {

    		public void run() {
    			synchronized (viewPager) {
    					System.out.println("currentItem: " + currentItem);
    					currentItem = (currentItem + 1) % imageViews.size();
    					handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
    					
    				}
    			}

    		}
	    
    
    	    
    @Override
    
    protected void onStop() {
    			// ��Activity���ɼ���ʱ��ֹͣ�л�
    			scheduledExecutorService.shutdown();
    			super.onStop();
    		}	 
    @Override
    protected void onStart() {
    		Log.i("onstart","onstart");
    		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    			// ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
    		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
    			super.onStart();
    			app = (MyApplication) getApplication();
    			//if(app.)
    			//����Ϊimageview����ʵ��
    			//����û�����WiFi���������˽���wifi����ʾͼƬ���򻬶�image����
    			//����Ϊ�û�ˢ�½���ʱ�����ж��Ƿ���ʾͼƬ
    		/*	if(app.getSelectCity() != null)
    			{
    				doSearchQuery();
    				TextView title = (TextView)findViewById(R.id.homeTitle);
    				title.setText(app.getSelectCity()+"�ĸ���");
    				Log.i("rukou","rukou");
    				
    			}*/
    		/*	int  flag = app.getImageFlag();
    			
    			if(flag == 0){}//imageResId = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };} 
    			else if (!checkNetworkConnection(getApplicationContext())&&(app.getImageFlag() == 1)){imageResId = new int[] { R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank };
    					Toast.makeText(getApplicationContext(), "�������˽���WiFi����ʾͼƬ����ǰΪ��ͼģʽ������ǿ�ҽ�������ʾͼƬ��������ʳ��������", Toast.LENGTH_LONG);}
    					Log.i("2","2");
    					titles = new String[5]; 
    					Log.i("3","3");
    			        titles[0] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
    			        titles[1] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
    			        titles[2] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
    			        titles[3] = "�������������2Сʱ";  
    			        titles[4] = "�����ؼ�";  
    			        
    			        imageViews = new ArrayList<ImageView>();  
    			  
    			        // ��ʼ��ͼƬ��Դ  
    			        for (int i = 0; i < imageResId.length; i++) {
    			        	Log.i("4","4");
    			            ImageView imageView = new ImageView(this);  
    			            imageView.setImageResource(imageResId[i]);  
    			            imageView.setScaleType(ScaleType.CENTER_CROP);  
    			            imageViews.add(imageView);  
    			        }  
    			  
    			        Log.i("5","5");
    			       dots = new ArrayList<View>();  
    			        dots.add(findViewById(R.id.v_dot0));  
    			        dots.add(findViewById(R.id.v_dot1));  
    			        dots.add(findViewById(R.id.v_dot2));  
    			        dots.add(findViewById(R.id.v_dot3));  
    			        dots.add(findViewById(R.id.v_dot4));
    			        
    			  
    			        tv_title = (TextView) findViewById(R.id.tv_title);  
    			        tv_title.setText(titles[0]);//  
    			  
    			        viewPager = (ViewPager) findViewById(R.id.vp);  
    			        viewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������  
    			        // ����һ������������ViewPager�е�ҳ��ı�ʱ����  
    			        viewPager.setOnPageChangeListener(new MyPageChangeListener()); 
    		
    			        Log.i("6","6");*/
    
    }
   
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Log.i("ppp","ppp");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		app = (MyApplication) getApplication();
		
	
			
		if(app.getImageFlag() == 0)
					{
						try {
						get("1");
						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
		else if (!checkNetworkConnection(getApplicationContext())&&(app.getImageFlag() == 1)){imageResId = new int[] { R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank };}
				
			
		
		handler=new Handler(){

			public void handleMessage(Message msg){

			     

			
			
			if(msg.what == 3)
				{

    			
    			
    			
    					
    					Log.i("imageadd","imageadd777");
    					titles = new String[5]; 
    					Log.i("imageadd","imageadd888");
    			        titles[0] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
    			        titles[1] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
    			        titles[2] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
    			        titles[3] = "�������������2Сʱ";  
    			        titles[4] = "�����ؼ�";  
    			        
    			        imageViews = new ArrayList<ImageView>();  
    			  
    			        // ��ʼ��ͼƬ��Դ  
    			        for (int i = 0; i < 5; i++) {
    			        	Log.i("4","4");
    			            ImageView imageView = new ImageView(getApplicationContext());  
    			            //imageView.setImageResource(imageResId[i]);
    			            imageView.setImageBitmap(bitmap[i]);
    			            imageView.setScaleType(ScaleType.CENTER_CROP);  
    			            imageViews.add(imageView);  
    			        }  
    			  
    			        Log.i("5","5");
    			        dots = new ArrayList<View>();  
    			        dots.add(findViewById(R.id.v_dot0));  
    			        dots.add(findViewById(R.id.v_dot1));  
    			        dots.add(findViewById(R.id.v_dot2));  
    			        dots.add(findViewById(R.id.v_dot3));  
    			        dots.add(findViewById(R.id.v_dot4));
    			        
    			  
    			        tv_title = (TextView) findViewById(R.id.tv_title);  
    			        tv_title.setText(titles[0]);//  
    			  
    			        viewPager = (ViewPager) findViewById(R.id.vp);  
    			        viewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������  
    			        // ����һ������������ViewPager�е�ҳ��ı�ʱ����  
    			        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    			        
				}
			
			                                                     

			}

			 };
		
		
		
		
		SharedPreferences  share = HomeActivity.this.getSharedPreferences("perference", MODE_PRIVATE);  
		String selectedCity = share.getString("selectedCity", "null");
		if(selectedCity.equals("null")){
			Log.i("null","null");
			app.setAutoCity(1);
			app.setFirstLoginFlag(1);
		}
		else{app.setFirstLoginFlag(0);
		app.setSelectCity(selectedCity);
		}
		//******************************����
		
		
		
		
		if(app.getFirstLoginFlag() == 1)
		{
			AlertDialog.Builder build=new AlertDialog.Builder(this); 
			//build.setCancelable(false);
            build.setTitle("�ҷ���ʾ")  
                    .setMessage("��ѡ�������ڴ�ѧ")  
                    .setPositiveButton("����������", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                            Intent intent = new Intent(HomeActivity.this,Dingweidaxue.class);
                            startActivity(intent);
                            finish();                   //����
                              
                        }  
                    })  
                    .setNegativeButton("ѡ��", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                        	Intent intent = new Intent(HomeActivity.this,city.class);
                        	startActivityForResult(intent,CODE);
                        	
                        }  
                    })  
                    .show();  
			
		}	
		else{
		if(app.getAutoCity() == 0)
			try {
				app.setAutoCity(1);  
				app.setHomeMode(0);
				app.setSelectCity(selectedCity);
				getInfo(selectedCity);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			doSearchQuery();
			TextView title = (TextView)findViewById(R.id.homeTitle);
			if(app.getSelectCity() != null)
			title.setText(app.getSelectCity()+"�ĸ���");
			Log.i("rukou","rukou");
			
		}
		
		
	
		
		Log.i("11","11");
		
	//	   if(!isNetworkConnected())
		//{
		//	AlertDialog.Builder build=new AlertDialog.Builder(this);  
         //   build.setTitle("ϵͳ��ʾ")  
          //          .setMessage("���������ӿ��ã�");
         //}
		//����Ϊimageview����ʵ��
		//����û�����WiFi���������˽���wifi����ʾͼƬ���򻬶�image����
		
		int  flag = app.getImageFlag();
		
		if(flag == 0){imageResId = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };} 
		else if (!checkNetworkConnection(getApplicationContext())&&(app.getImageFlag() == 1)){imageResId = new int[] { R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank, R.drawable.blank };
				Toast.makeText(getApplicationContext(), "�������˽���WiFi����ʾͼƬ����ǰΪ��ͼģʽ������ǿ�ҽ�������ʾͼƬ��������ʳ��������", Toast.LENGTH_LONG);}
				Log.i("f2","2");
				titles = new String[5]; 
				Log.i("f3","3");
		        titles[0] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
		        titles[1] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
		        titles[2] = "���ֲ�Ʒ���۴����У�ץ�������ɣ�";  
		        titles[3] = "�������������2Сʱ";  
		        titles[4] = "�����ؼ�";  
		        
		        imageViews = new ArrayList<ImageView>();  
		  
		        // ��ʼ��ͼƬ��Դ  
	/*	        for (int i = 0; i < 5; i++) {
		        	Log.i("f4","3");
		            ImageView imageView = new ImageView(this);  
		            
		            imageView.setImageBitmap(bitmap) ; 
		            imageView.setScaleType(ScaleType.CENTER_CROP);  
		            imageViews.add(imageView);  
		        }  
		  
		          
		        dots = new ArrayList<View>();  
		        dots.add(findViewById(R.id.v_dot0));  
		        dots.add(findViewById(R.id.v_dot1));  
		        dots.add(findViewById(R.id.v_dot2));  
		        dots.add(findViewById(R.id.v_dot3));  
		        dots.add(findViewById(R.id.v_dot4));
		        
		  
		        tv_title = (TextView) findViewById(R.id.tv_title);  
		        tv_title.setText(titles[0]);//  
		  
		        viewPager = (ViewPager) findViewById(R.id.vp);  
		        viewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������  
		        // ����һ������������ViewPager�е�ҳ��ı�ʱ����  
		       viewPager.setOnPageChangeListener(new MyPageChangeListener());  */
	  
		        Log.i("f5","f5");
		listView = (ListView) this.findViewById(R.id.datalist);
		listView.setOnItemClickListener(new OnItemClickListener(){
			public void  onItemClick(AdapterView<?>listView,View view,int position,long id)
			{
				Map<String, String>  map = (Map<String, String>) listView.getItemAtPosition(position);
				Intent intent = new Intent(HomeActivity.this,SellerDetail.class);
	            Bundle bundle = new Bundle();
	            bundle.putSerializable("map", (Serializable) map);
	            bundle.putString("flag", "home");
	            intent.putExtras(bundle);
				Log.i("12","11");
				//Bundle bundle = new Bundle();
				Log.i("13","11");
				//bundle.putInt("loginFlag", loginFlag);
				Log.i("14","11");
				//intent.putExtras(bundle);
				Log.i("15","11");
				
				startActivity(intent);
				
				
				
			}

			
			
		});
		
		
		
		
		
		searchButton = (ImageButton) findViewById(R.id.search_home);
		cityButton = (ImageButton) findViewById(R.id.city_home);
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HomeActivity.this, search.class);
				startActivity(intent);
			}

		});

		cityButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(HomeActivity.this, city.class);
				startActivityForResult(intent,CODE);
			}

		});
		
		
			}

		
		
	

	private List<Map<String, Object>> getListItems() {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < goodsName.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("image", imageIds);

		}
		// TODO Auto-generated method stub
		return null;
	}
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        // TODO Auto-generated method stub  
        switch (keyCode) {  
        case KeyEvent.KEYCODE_BACK:  
            AlertDialog.Builder build=new AlertDialog.Builder(this);  
            build.setTitle("ϵͳ��ʾ")  
                    .setMessage("ȷ��Ҫ�˳�У԰�ҷ���")  
                    .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                            finish();  
                              
                        }  
                    })  
                    .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                              
                        }  
                    })  
                    .show();  
            break;  
  
        default:  
            break;  
        }  
        return false;
}
	
	public static boolean checkNetworkConnection(Context context)   
   {   
	       final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);   
	       Log.i("q1","q1");
	       final android.net.NetworkInfo wifi =connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);   
	       
	       final android.net.NetworkInfo mobile =connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);   
	      
	       if(wifi.isAvailable())   
	    	   {Log.i("q4","q4");
	    	   return true;  } 
	       else  
	           {  
	           Log.i("q5","q5");
	           return false; 
	           }
	           
	   }  
	protected void doSearchQuery() {
		//showProgressDialog(0);// ��ʾ���ȿ�
		new Thread(new Runnable() {
			public void run() {
				try {
					
					

					PoiSearch poiSearch = new PoiSearch(
							HomeActivity.this, new PoiSearch.Query(
									"�͹�", PoiTypeDef.All));// ���������ַ�����poi�������ͣ�poi�������򣨿��ַ�������ȫ����
					   
					app = (MyApplication)getApplication();
					
					Log.i("Th","Th1");
					if(app.getHomeMode() == 1)
					{
					
					
					Geocoder coder = new Geocoder(getApplicationContext(),Locale.getDefault());
					List<Address> address1;
					try {
						Log.i("Th","Th2");
						if(app.getSelectCity() != null)
						{
						address1 = coder.getFromLocationName(app.getSelectCity(),1);
						Log.i("Th","Th3");
						if (address1 != null && address1.size() > 0) {
							Address addres =address1.get(0);
							Log.i("Th","Th4");
							double lat = addres.getLatitude();
							double lon = addres.getLongitude();
							Log.i("lat",""+lat);
							Log.i("lon",""+lon);
							double []latlon ={lat,lon};
							app =(MyApplication)getApplication();
							app.setSelectedCityLatlon(latlon);
						}
						
						
						else if (app.getSelectCity() == "������ѧ")
						{
							double [] ll = {39.987755,116.305611};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�廪��ѧ")
						{
							double [] ll = {40.001244,116.3284839};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�����ʵ��ѧ")
						{
							double [] ll = {40.001244,116.3284839};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�������պ����ѧ")
						{
							double [] ll = {39.9809553,116.3472564};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "���������ѧ")
						{
							double [] ll = {39.9700009,116.3185455};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�����Ƽ���ѧ")
						{
							double [] ll = {39.990249,116.357096};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�������պ����ѧ")
						{
							double [] ll = {39.9809553,116.3472564};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�Ϻ���ѧ")
						{
							double [] ll = {31.274201,121.456572};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�Ϻ���ͨ��ѧ")
						{
							double [] ll = {31.147635,121.35509};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "������ѧ")
						{
							double [] ll = {31.2974197,121.5036178};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�ൺ��ѧ")
						{
							double [] ll = {36.1161823,120.4813058};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�й������ѧ")
						{
							double [] ll = {36.061242,120.335285};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�й�ʯ�ʹ�ѧ")
						{
							double [] ll = {40.219466,116.247116};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�ൺ�Ƽ���ѧ")
						{
							double [] ll = {36.158801,120.420564};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�ൺ����ѧ")
						{
							double [] ll = {35.97576,120.204975};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�ൺũҵ��ѧ")
						{
							double [] ll = {36.3149529,120.3964663};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�������պ����ѧ")
						{
							double [] ll = {39.9809553,116.3472564};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "������ѧ")
						{
							double [] ll = {34.1456588,108.9386179};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�������ӿƼ���ѧ")
						{
							double [] ll = {34.235138,108.9185219};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�人��ѧ")
						{
							double [] ll = {30.5403638,114.3617683};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "������ѧ")
						{
							double [] ll = {34.2244534,108.9469697};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�人�Ƽ���ѧ")
						{
							double [] ll = {30.5403638,114.3617683};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�人����ѧ")
						{
							double [] ll = {30.602247,114.358781};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "���пƼ���ѧ")
						{
							double [] ll = {30.507905,114.413666};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "�й����ʴ�ѧ")
						{
							double [] ll = {39.9900267,116.3493884};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "����������ѧ")
						{
							double [] ll = {30.475036,114.3941861};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "��������ѧ")
						{
							double [] ll = {45.707197,126.623957};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "��������ҵ��ѧ")
						{
							double [] ll = {45.747047,126.633425};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "��������ҵ��ѧ")
						{
							double [] ll = {45.818093,126.56437};
							app.setSelectedCityLatlon(ll);
						}
						else if (app.getSelectCity() == "���������̴�ѧ")
						{
							double [] ll = {45.7754388,126.6778707};
							app.setSelectedCityLatlon(ll);
						}
						
						
						
						
						
						}
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						Log.i("listllp"+llp.getLatitude(),""+llp.getLongitude());
					
						
						
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					
				    llp.setLatitude(app.getSelectedCityLatlon()[0]);
					llp.setLongitude(app.getSelectedCityLatlon()[1]);
				    Log.i("listllp"+llp.getLatitude(),""+llp.getLongitude());
					
					PoiSearch.SearchBound sb = new PoiSearch.SearchBound(llp,10000);
					
					 
					poiSearch.setBound(sb);
					
					poiSearch.setPageSize(20);// ��������ÿ����෵�ؽ����
					
					result = poiSearch.searchPOI();
					List<PoiItem> poiItems = result.getPage(1);
					for(int i = 0;i < 20; i ++)
					{
						
						
					}
					
					for (int i = 0; i < poiItems.size(); i++){
						
						goodsName[i] =stringFilter(poiItems.get(i).toString());
						
					
						sellerTelphone[i]=poiItems.get(i).getTel();
						
						
				     	distance[i]=poiItems.get(i).getDistance()+"m";
						address[i] = stringFilter(poiItems.get(i).getSnippet());
						
						
						listCount++;
					}
					addList();
					
					
					if (result != null) {
						
						//Log.i(""+sellerTelphone[2],""+sellerTelphone[2]);
					}
					handler.sendMessage(Message.obtain(handler,
							Constants.POISEARCH));
				} }catch (AMapException e) {
					handler.sendMessage(Message
							.obtain(handler, Constants.ERROR));
					e.printStackTrace();
				}
			}
		}).start();
		

	}
	private void showProgressDialog(int n) {
		
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		if(n == 0)
		{
			progDialog.setMessage("��������"+app.getSelectCity()+"�ĸ���\n");
		}
		else if(n == 1)
		{
			progDialog.setMessage("���ڼ�����Դ������");
			
		}
		progDialog.show();
	}
	
	
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
	
	private void addList()
	{
	Log.i("ok","ok");
	listView = (ListView) this.findViewById(R.id.datalist);
	listItems = getListItems();
	this.listView = (ListView) super.findViewById(R.id.datalist);
	
	
	list.clear();
	/*for (int x = 0; x < 20; x++) {
		Map<String, String> map = new HashMap<String, String>();// ����map����
		//map.put("imageItem", String.valueOf(this.pic[x]));
		
		map.put("titleItem",  goodsName[x]);
		
		map.put("phone", sellerTelphone[x]);
		
		//map.put("priceItem", this.data[x][1]);
		map.put("distance", distance[x]);
		
		map.put("address", address[x]);
		map.put("image", String.valueOf(this.imageFlag[x]));
		this.list.add(map);
		
		
	}
	*/
	if(app.getListFirstFlag() == 1)
	{	
		Map<String, String> map1 = app.getListFirst().get(1);
		
		Log.i("address",map1.get("address"));
		this.simpleAdapter = new SimpleAdapter(this,
			app.getListFirst(),
			R.layout.list_item,
			new String[]{"image","titleItem","address","phone","distance"},
			new int[]{R.id.imageItem,R.id.titleItem,R.id.infoItem,R.id.priceItem,R.id.shouchuItem});
	
	listView.post(new Runnable() {//����һ�ָ����ķ�����Ϣ��ui�̵߳ķ�����
		           
		             @Override
		               public void run() {//run()��������ui�߳�ִ��
		            	 simpleAdapter.notifyDataSetChanged();   //����֪ͨui�Ѹı�
		            	 listView.setAdapter(simpleAdapter);
		            	 
		              }
		           });
	}
	else
	{
		for (int x = 0; x < 20; x++) {
			Map<String, String> map = new HashMap<String, String>();// ����map����
			//map.put("imageItem", String.valueOf(this.pic[x]));
			
			map.put("titleItem",  goodsName[x]);
			
			map.put("phone", sellerTelphone[x]);
			
			//map.put("priceItem", this.data[x][1]);
			map.put("distance", distance[x]);
			
			map.put("address", address[x]);
			map.put("image", String.valueOf(this.imageFlag[x]));
			this.list.add(map);
		}
			
			this.simpleAdapter = new SimpleAdapter(this,
					app.getListFirst(),
					R.layout.list_item,
					new String[]{"image","titleItem","address","phone","distance"},
					new int[]{R.id.imageItem,R.id.titleItem,R.id.infoItem,R.id.priceItem,R.id.shouchuItem});
			
			listView.post(new Runnable() {//����һ�ָ����ķ�����Ϣ��ui�̵߳ķ�����
				           
				             @Override
				               public void run() {//run()��������ui�߳�ִ��
				            	 simpleAdapter.notifyDataSetChanged();   //����֪ͨui�Ѹı�
				            	 listView.setAdapter(simpleAdapter);
				            	 
				              }
				           });
		
	}
	//���������󶨵�listView��
	Log.i("finish","finish");
	
	
	}
	

		//����Ű�����
	 public static String stringFilter(String str) {  
		         str = str.replaceAll("��", "[").replaceAll("��", "]")  
		                .replaceAll("��", "!").replaceAll("��", ":");// �滻���ı��  
		         String regEx = "[����]"; // ����������ַ�  
		         Pattern p = Pattern.compile(regEx);  
		         Matcher m = p.matcher(str);  
		         return m.replaceAll("").trim();  
		     }  
	 
	 private boolean isNetworkConnected() {  
		         ConnectivityManager cm =   
		                 (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);  
		         NetworkInfo network = cm.getActiveNetworkInfo();  
		         WifiManager wifiManager = (WifiManager) getApplicationContext()  
		        		                 .getSystemService(Context.WIFI_SERVICE);  

		         if (network != null||wifiManager != null) {  
		             return network.isAvailable();  
		         }  
		         return false;  
		     }  
	 
	 protected void onActivityResult(int requestCode,int resultCode,Intent data)
	 {
		 if(requestCode == CODE&&resultCode == CODE)
		 {
			 finish();
			 
		 }
		 
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
					goodsName1[i] = (String) dataJson.getJSONObject(""+i).get("name");
					Log.i("title",(String) dataJson.getJSONObject(""+i).get("name"));
					if(dataJson.getJSONObject(""+i).get("phone") != null)
						{sellerTelphone1[i] = (String) dataJson.getJSONObject(""+i).get("phone");
					Log.i("phone",(String) dataJson.getJSONObject(""+i).get("phone"));}
					if(dataJson.getJSONObject(""+i).getString("address") != null)
						{address1[i] = (String) dataJson.getJSONObject(""+i).get("address");
					Log.i("address",(String) dataJson.getJSONObject(""+i).get("address"));}
					image1[i] = R.drawable.qianyue;
					sellerId1[i] = (String) dataJson.getJSONObject(""+i).get("ac");
				}
				
				for (int x = 0; x < Integer.parseInt(""+dataJson.get("count")) ; x++) {
					Map<String, String> map = new HashMap<String, String>();// ����map����
					//map.put("imageItem", String.valueOf(this.pic[x]));
					
					map.put("titleItem",  goodsName1[x]);
					
					map.put("phone", sellerTelphone1[x]);
					
					map.put("image", String.valueOf(image1[x]));

					map.put("id", sellerId1[x]);
					
					map.put("address", address1[x]);
					
					list1.add(map);
					
					
				}
				
				app = (MyApplication)getApplication();
				app.setListFirst(list1);
				app.setListFirstFlag(1);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    			
	        
	        
	        
	         

	        

	        Log.i("15","15");

				}
				};
			webserviceThread.start();  
			dissmissProgressDialog();
			dissmissProgressDialog();
			listView = (ListView) this.findViewById(R.id.datalist);
			this.simpleAdapter = new SimpleAdapter(this,
					list1,
					R.layout.list_item,
					new String[]{"image","titleItem","address","phone","distance"},
					new int[]{R.id.imageItem,R.id.titleItem,R.id.infoItem,R.id.priceItem,R.id.shouchuItem});
			listView.post(new Runnable() {//����һ�ָ����ķ�����Ϣ��ui�̵߳ķ�����
		           
	             @Override
	               public void run() {//run()��������ui�߳�ִ��
	            	 simpleAdapter.notifyDataSetChanged();   //����֪ͨui�Ѹı�
	            	 listView.setAdapter(simpleAdapter);
	            	 
	              }
	           });
			
			
			
		}
	 
			
	 public void get(String n) throws InterruptedException {
			String detail;
			//FileOutputStream fos = null;
Thread webserviceThread = new Thread() {  //�½�һ���߳�Ϊ�������ݴ��䣬�����������
			    
				
				public void run(){ 
					String detail;
					try {
				System.out.println(1);
				app = (MyApplication)getApplication();
				app.getNameSpace();
				Log.i("imageadd","imageadd111");
				SoapObject rpc = new SoapObject(app.getNameSpace(),"get_image_home");
	    		System.out.println(2);
				
				Log.i("imageadd","imageadd2");
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				System.out.println(5);
				envelope.bodyOut = rpc;
				envelope.dotNet = true;
				envelope.setOutputSoapObject(rpc);
				HttpTransportSE ht = new HttpTransportSE(app.getEndPoint());
				System.out.println(6);
				ht.debug = true;
				ht.call(null, envelope);
				System.out.println(3);
				detail = (String) envelope.getResponse().toString();
				System.out.println(detail);
				if(detail!=null)
				{
					Log.i("imageadd","imageadd3");
				JSONObject  dataJson=new JSONObject(detail.toString());
				for(int i = 0;i < 5 ;i++)
				{
					base64[i] = (String) dataJson.getJSONObject(""+(i+1)).get("base64");
					byte[] buffer = new BASE64Decoder(null).decodeReturnByte(base64[i]);
					bitmap [i]=BitmapFactory.decodeByteArray(buffer,0,buffer.length);
				}
				
		       
		        Message msg = new Message();
		 		msg.what = 3;
		 		handler.sendMessage(msg);
		 		Log.i("imageadd","imageadd4");
				}}catch (Exception e){
					//announce_flag=0;
		    		e.printStackTrace();
		    	}
				}
				
				};
				webserviceThread.start();  
		         //saveFile(bitmap,n);
				
			
			//return null;
			
		}

	
	
		}



