package com.example.FindFood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SellerHome extends Activity{
	private Button bt_seller_exit;
	MyApplication app;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sellerhome);
		bt_seller_exit = (Button)findViewById(R.id.bt_seller_exit);
		bt_seller_exit.setOnClickListener(new bt_seller_exit());
		
		app = (MyApplication)getApplication();
		
		RelativeLayout dishinfoxiugai = (RelativeLayout)findViewById(R.id.dishinfoxiugai);
		dishinfoxiugai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SellerHome.this,Yifabucaipingxiugai.class);
				Log.i("seller111",app.getSId()[0]);
				startActivity(intent);
				Log.i("seller","2");
				
			}

		});
		RelativeLayout weisong = (RelativeLayout)findViewById(R.id.weisongdingdan);
		weisong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SellerHome.this,WeisongActivity.class);
				
				startActivity(intent);
				Log.i("seller","2");
				
			}

		});
		RelativeLayout yisong = (RelativeLayout)findViewById(R.id.yisongdingdan);
		yisong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SellerHome.this,YisongActivity.class);
				
				startActivity(intent);
				
				
			}

		});
		
		RelativeLayout dishinfoshezhi = (RelativeLayout)findViewById(R.id.dishinfoshezhi);
		dishinfoshezhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
				
				Intent intent = new Intent(SellerHome.this,NewDishAddActivity.class);
				startActivity(intent);
				
				
			}

		});
		
		
		RelativeLayout shouyitongji = (RelativeLayout)findViewById(R.id.shouyitongjihome);
		shouyitongji.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SellerHome.this,ShouyitongjiActivity.class);
				startActivity(intent);
				
				
			}

		});
		TextView shanghuhao = (TextView)findViewById(R.id.sellerName);
		if(app.getSellerName()!=null)
		shanghuhao.setText(app.getSellerName());
		else {Toast.makeText(getApplicationContext(),"你尚未登陆，非法请求", Toast.LENGTH_LONG).show();
			finish();
		}
		

}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
        // TODO Auto-generated method stub  
        switch (keyCode) {  
        case KeyEvent.KEYCODE_BACK:  
            AlertDialog.Builder build=new AlertDialog.Builder(this);  
            build.setTitle("找饭提示")  
                    .setMessage("为了您的数据安全，请点击注销登录安全退出。")  
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                           
                              
                        }  
                    }).show();  
            break;  
  
        default:  
            break;  
        }  
        return false;
}
	
	
	
	class bt_seller_exit implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(SellerHome.this, SellerLogin.class);
			app.setSellerName(null);
			app.setSId(null);
			app.setSellerService(0);
			SellerHome.this.startActivity(intent);
			finish();
		}
		
	}
}

