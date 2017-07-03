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
       
        String autoFlag = share.getString("autologin","0");//�洢���� ����1 ��key ����2 ��ֵ  
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
	            build.setTitle("�ҷ���ʾ")  
	                    .setMessage("�Ѿ������°汾��")  
	                    .setPositiveButton("�õ�", new DialogInterface.OnClickListener() {  
	                          
	                        @Override  
	                        public void onClick(DialogInterface dialog, int which) {  
	                            // TODO Auto-generated method stub  
	                                          //����
	                              
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
					setTitle("��½����ʷ����").
					setMultiChoiceItems(new String[] {"���Զ���¼","��ס�û�������","���ѡ��ĳ���"},checkedItems, new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							// TODO Auto-generated method stub
							checkedItems[which] = isChecked;
							
						}
						
						
					}). 
					
					setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							
								if(checkedItems[0]){
									SharedPreferences  share = MoreActivity.this.getSharedPreferences("perference", MODE_PRIVATE);  
					                 Editor editor = share.edit();//ȡ�ñ༭��  
					                 editor.putString("autologin","1"); 
					                 Log.i("atuologin","autologin");
					                 editor.commit();		//�ύˢ������ 
									
								}
								if(!checkedItems[1])
								{
									SharedPreferences  share = MoreActivity.this.getSharedPreferences("perference", MODE_PRIVATE);  
					                 Editor editor = share.edit();//ȡ�ñ༭��  
					                 editor.putString("accountName", "");//�洢���� ����1 ��key ����2 ��ֵ  
					                 editor.putString("accountPassword", "");  
					                 editor.commit();//�ύˢ������ 
									
								}
								if(checkedItems[2])
								{
									SharedPreferences  share = MoreActivity.this.getSharedPreferences("perference", MODE_PRIVATE);  
					                 Editor editor = share.edit();//ȡ�ñ༭��  
					                 editor.putString("selectedCity","null");//�洢���� ����1 ��key ����2 ��ֵ  
					                 
					                 editor.commit();//�ύˢ������ 
									
								}
								
								
							}
							
						
					}).                
					setNegativeButton("ȡ��", null). 
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
					    builder.setTitle("ֻ��wifi�����½���ͼƬ��");
					    builder.setIcon(android.R.drawable.ic_dialog_info);
					    builder.setSingleChoiceItems(new String[] { "����", "�ǵ�" }, 0,new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								whichImage = which;
								Log.i("ImageFlag",""+which);
							}
						});
					    builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								app = (MyApplication) getApplication();
								app.setImageFlag(whichImage);
							}
						});
					    builder.setNegativeButton("ȡ��", null);
					    
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
					    builder.setTitle("Ҫ�ر����Ķ���״̬�ı�������Ϣ��������");
					    builder.setIcon(android.R.drawable.ic_dialog_info);
					    builder.setSingleChoiceItems(new String[] { "����", "�ǵ�" }, 0,new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
					    builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
						{

							
							public void onClick(DialogInterface dialog,int which) {
								// TODO Auto-generated method stub
								if(which == 1){
									app = (MyApplication) getApplication();
									app.setMessageFlag(which);
									Log.i("MessageFlag",""+which);
								}
										
									
								}});
					    builder.setNegativeButton("ȡ��", null);
					    
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
					    builder.setTitle("Ҫ�ر�У԰�ҷ���������ʾ��");
					    builder.setIcon(android.R.drawable.ic_dialog_info);
					    builder.setSingleChoiceItems(new String[] { "����", "�ǵ�" }, 0,new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								app = (MyApplication) getApplication();
								
								
							}
						});
					    builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener()
						{

							
							public void onClick(DialogInterface dialog,int which) {
								// TODO Auto-generated method stub
								if(which == 0){
								Log.i("sound","sound");
								//AudioManager mAudioManager = (AudioManager)getSystemService(getApplicationContext().AUDIO_SERVICE); 
								//mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
								}
										
									
								}});
					    builder.setNegativeButton("ȡ��", null);
					    
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
            build.setTitle("ע��")  
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
}
		