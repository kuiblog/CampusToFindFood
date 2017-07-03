package com.example.FindFood;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetail extends Activity{
	private ImageButton buy;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyApplication app = (MyApplication)getApplication();
		setContentView(R.layout.itemdetail);
		buy = (ImageButton)findViewById(R.id.buy);
		buy.setOnClickListener(new buy());
		Log.i("11","11");
		Intent intent = getIntent(); 
		Bundle bundle = intent.getExtras();
		//Log.i("11","11");
		Map<String,String> map = (Map<String,String>) bundle.getSerializable("map");
		app.getImageFlag();
		String title = map.get("titleItem");
		String info = map.get("infoItem");
		String price = map.get("priceItem");
		String shouchu = map.get("shouchuItem");
		String image = map.get("imageItem");
		Log.i("22","22");
		int imageId = 0;
		Log.i("22",""+app.getImageFlag());
		
		if(app.getImageFlag() == 0){imageId = Integer.parseInt(image);}
		Log.i("33","33");
		TextView titleT = (TextView)findViewById(R.id.itemdetailtitle);
		titleT.setText(title);
		TextView infoT = (TextView)findViewById(R.id.itemdetailinfo);
		infoT.setText(info);
		TextView priceT = (TextView)findViewById(R.id.itemdetailprice);
		priceT.setText(price);
		ImageView imageI = (ImageView)findViewById(R.id.userface);
		if(app.getImageFlag() == 0){
		imageI.setImageResource(imageId);
		imageI.setAdjustViewBounds(true);
		}
		else if(!checkNetworkConnection(getApplicationContext())&&(app.getImageFlag() == 1)){
			imageI.setImageResource(R.drawable.blank);
		}
	}
	class buy implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(ItemDetail.this, BuyActivity.class);
			ItemDetail.this.startActivity(intent);
			finish();
		}
	}
	
	public static boolean checkNetworkConnection(Context context)   
	   {   
		       final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);   
		  Log.i("q1","q1");
		       final android.net.NetworkInfo wifi =connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);   
		       Log.i("q2","q2");
		       final android.net.NetworkInfo mobile =connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);   
		       Log.i("q3","q3");
		       if(wifi.isAvailable())   
		    	   {Log.i("q4","q4");
		    	   return true;  } 
		       else  
		           {  
		           Log.i("q5","q5");
		           return false; 
		           }
		           
		   }  

}
