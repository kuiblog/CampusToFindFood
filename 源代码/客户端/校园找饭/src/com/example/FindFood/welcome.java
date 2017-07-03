package com.example.FindFood;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;


public class welcome extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//动画程序--由浅入深
		this.setContentView(R.layout.welcome);
		ImageView iv=(ImageView)this.findViewById(R.id.welcome1);
		iv.setAdjustViewBounds(true);
		AlphaAnimation aa = new AlphaAnimation(0.1f,1.0f);
		aa.setDuration(3000);
		iv.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener()
		{

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				SharedPreferences  share = getSharedPreferences("perference", MODE_PRIVATE);
                String firstflag= share.getString("firstflag", "2");
               if(firstflag.equals("5"))
                {
				Intent it=new Intent(welcome.this,MainActivity.class);
				it.putExtra("toWhichTab", "0");
				
				startActivity(it);
				finish();
                }
               else
                {
                	Intent it=new Intent(welcome.this,GuideViewDemoActivity.class);
                	Editor editor = share.edit();//取得编辑器  
                    editor.putString("firstflag","5");//存储配置 参数1 是key 参数2 是值  
                    editor.commit();//提交刷新数据 
                    startActivity(it);
                    finish();
                }
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}

}
