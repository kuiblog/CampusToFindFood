package com.example.FindFood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MoreActivity extends Activity{
	private MyApplication app;
	private int whichImage;
	private boolean[] checkedItems = new boolean [3];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		SharedPreferences  share = MoreActivity.this.getSharedPreferences("perference", MODE_PRIVATE);  
       
        String autoFlag = share.getString("autologin","0");//存储配置 参数1 是key 参数2 是值  
        if(autoFlag.equals("1"))checkedItems[0] = true;
        else checkedItems[0] = false;
        String jizhuFlag = share.getString("accountName", "");
        if(jizhuFlag.equals(""))
        {
        	checkedItems[1] = false;
        }
        else checkedItems[1] = true;
        
        RelativeLayout banbengenxin = (RelativeLayout)findViewById(R.id.banbengenxin);
        banbengenxin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				AlertDialog.Builder build= new AlertDialog.Builder(MoreActivity.this); 
				//build.setCancelable(false);
	            build.setTitle("找饭提示")  
	                    .setMessage("已经是最新版本！")  
	                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                                          //完善
	                              
	                        }  
	                    })  
	                      
	                    .show();

				
			}

		});
        
        
        RelativeLayout denglushezhi = (RelativeLayout)findViewById(R.id.denglushezhi);
		denglushezhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				new AlertDialog.Builder(MoreActivity.this) .
					setTitle("登陆及历史设置").
					setMultiChoiceItems(new String[] {"打开自动登录","记住用户名密码","清除选择的城市"},checkedItems, new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							// TODO Auto-generated method stub
							checkedItems[which] = isChecked;
							
						}
						
						
					}). 
					
					setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							
								if(checkedItems[0]){
									SharedPreferences  share = MoreActivity.this.getSharedPreferences("perference", MODE_PRIVATE);  
					                 Editor editor = share.edit();//取得编辑器  
					                 editor.putString("autologin","1"); 
					                 Log.i("atuologin","autologin");
					                 editor.commit();		//提交刷新数据 
									
								}
								if(!checkedItems[1])
								{
									SharedPreferences  share = MoreActivity.this.getSharedPreferences("perference", MODE_PRIVATE);  
					                 Editor editor = share.edit();//取得编辑器  
					                 editor.putString("accountName", "");//存储配置 参数1 是key 参数2 是值  
					                 editor.putString("accountPassword", "");  
					                 editor.commit();//提交刷新数据 
									
								}
								if(checkedItems[2])
								{
									SharedPreferences  share = MoreActivity.this.getSharedPreferences("perference", MODE_PRIVATE);  
					                 Editor editor = share.edit();//取得编辑器  
					                 editor.putString("selectedCity","null");//存储配置 参数1 是key 参数2 是值  
					                 
					                 editor.commit();//提交刷新数据 
									
								}
								
								
							}
							
						
					}).                
					setNegativeButton("取消", null). 
					show(); 

				
			}

		});
		
		
		RelativeLayout shanghu = (RelativeLayout)findViewById(R.id.shangjiarukou);
		shanghu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MoreActivity.this,SellerLogin.class);
				startActivity(intent);
				
			}
			

		});
		RelativeLayout xiaoxi = (RelativeLayout)findViewById(R.id.xiaoxiguanbi);
		xiaoxi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
					    
						android.app.AlertDialog.Builder builder=new AlertDialog.Builder(MoreActivity.this);
					    builder.setTitle("只在wifi环境下接收图片？");
					    builder.setIcon(android.R.drawable.ic_dialog_info);
					    builder.setSingleChoiceItems(new String[] { "不是", "是的" }, 0,new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								whichImage = which;
								Log.i("ImageFlag",""+which);
							}
						});
					    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								app = (MyApplication) getApplication();
								app.setImageFlag(whichImage);
							}
						});
					    builder.setNegativeButton("取消", null);
					    
					    AlertDialog dialog=builder.create();
					    dialog.show();
					  
					
			
				
			}

		});
		RelativeLayout message = (RelativeLayout)findViewById(R.id.messagekaiguan);
		
		message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
					    
						android.app.AlertDialog.Builder builder=new AlertDialog.Builder(MoreActivity.this);
					    builder.setTitle("要关闭您的订单状态改变提醒信息的推送吗？");
					    builder.setIcon(android.R.drawable.ic_dialog_info);
					    builder.setSingleChoiceItems(new String[] { "不是", "是的" }, 0,new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
					    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
						{

							
							public void onClick(DialogInterface dialog,int which) {
								// TODO Auto-generated method stub
								if(which == 1){
									app = (MyApplication) getApplication();
									app.setMessageFlag(which);
									Log.i("MessageFlag",""+which);
								}
										
									
								}});
					    builder.setNegativeButton("取消", null);
					    
					    AlertDialog dialog=builder.create();
					    dialog.show();
					  
					
			
				
			}

		});
		RelativeLayout soungkaiguan = (RelativeLayout)findViewById(R.id.soungkaiguan);
		soungkaiguan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
					    
						android.app.AlertDialog.Builder builder=new AlertDialog.Builder(MoreActivity.this);
					    builder.setTitle("要关闭校园找饭的声音提示吗？");
					    builder.setIcon(android.R.drawable.ic_dialog_info);
					    builder.setSingleChoiceItems(new String[] { "不是", "是的" }, 0,new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								app = (MyApplication) getApplication();
								
								
							}
						});
					    builder.setPositiveButton("确定",new DialogInterface.OnClickListener()
						{

							
							public void onClick(DialogInterface dialog,int which) {
								// TODO Auto-generated method stub
								if(which == 0){
								Log.i("sound","sound");
								//AudioManager mAudioManager = (AudioManager)getSystemService(getApplicationContext().AUDIO_SERVICE); 
								//mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
								}
										
									
								}});
					    builder.setNegativeButton("取消", null);
					    
					    AlertDialog dialog=builder.create();
					    
					    dialog.show();
					    
					  
					
			
				
			}

		});
		RelativeLayout guanyu = (RelativeLayout)findViewById(R.id.guanyu);
		guanyu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MoreActivity.this,Info.class);
				startActivity(intent);
				
				
				
				
			}

		});
		RelativeLayout jianyi = (RelativeLayout)findViewById(R.id.jianyi1);
		jianyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MoreActivity.this,Tousu.class);
				startActivity(intent);
				
				
				
				
			}

		});
		
		
	}
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        // TODO Auto-generated method stub  
        switch (keyCode) {  
        case KeyEvent.KEYCODE_BACK:  
            AlertDialog.Builder build=new AlertDialog.Builder(this);  
            build.setTitle("注意")  
                    .setMessage("确定要退出校园找饭吗？")  
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
                          
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
                            finish();  
                              
                        }  
                    })  
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
                          
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
}
		