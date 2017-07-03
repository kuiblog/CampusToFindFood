package com.example.FindFood;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;




@SuppressLint("NewApi")
public  class MainActivity extends TabActivity{
    public TabHost mth;
    public static final String TAB_HOME="TabHome";
    public static final String TAB_MY="TabMy";
    public static final String TAB_NEAR="TabNear";
    public static final String TAB_MORE="TabMore";
    public RadioGroup mainbtGroup;
    private RadioButton Radio1=null,Radio2=null,Radio3=null;
    private  String str=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);		
	    this.setContentView(R.layout.maintabs);
	    mth=this.getTabHost();
	    Intent intent = getIntent();
	    str= intent.getStringExtra("toWhichTab");
	    Log.i("str",str);
	    //创建4个子页
	    TabSpec ts1= mth.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);	 
	    ts1.setContent(new Intent(MainActivity.this,HomeActivity.class));
	    mth.addTab(ts1);
	    TabSpec ts2= mth.newTabSpec(TAB_NEAR).setIndicator(TAB_NEAR);
	    ts2.setContent(new Intent(MainActivity.this,NearActivity.class));
	    mth.addTab(ts2);
	    TabSpec ts3= mth.newTabSpec(TAB_MY).setIndicator(TAB_MY);	 
	    ts3.setContent(new Intent(MainActivity.this,MyActivity.class));
	    mth.addTab(ts3);
	    TabSpec ts4= mth.newTabSpec(TAB_MORE).setIndicator(TAB_MORE);
	    ts4.setContent(new Intent(MainActivity.this,MoreActivity.class));
	    mth.addTab(ts4);
	    //得到RadioGroup对象
	    this.mainbtGroup=(RadioGroup)this.findViewById(R.id.main_radio);
	    Radio1=(RadioButton)findViewById(R.id.radio_button0);   //首页
	  //  Radio1.setChecked(true);
	    Radio2=(RadioButton)findViewById(R.id.radio_button1);   //附近
	    Radio3=(RadioButton)findViewById(R.id.radio_button2);	//我的饭桶
	    Log.i("1111",str);
	    //MainActivity进行判断
	    if(str.equals("0"))
	    {
	    	Radio1.setChecked(true);
	    	mth.setCurrentTabByTag(TAB_HOME);
	    }
	    else if(str.equals("1"))
	    {
	    	Radio2.setChecked(true);
	    	mth.setCurrentTabByTag(TAB_NEAR);
	    }
	    else if(str.equals("2"))
	    {
	    	Log.i("2222",str);
	    	Radio3.setChecked(true);
	    	mth.setCurrentTabByTag(TAB_MY);
	    	
	    }
	    
	    mainbtGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
	    {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				Log.d("select ID", "---------------"+checkedId);
				switch(checkedId)
				{
				case R.id.radio_button0://首页
					 mth.setCurrentTabByTag(TAB_HOME);
					 break;
				case R.id.radio_button1://附近
					 mth.setCurrentTabByTag(TAB_NEAR);
					 break;
				case R.id.radio_button2://我的饭桶
					 mth.setCurrentTabByTag(TAB_MY);
					 break;
				case R.id.radio_button3://更多
					 mth.setCurrentTabByTag(TAB_MORE);
					 break;
				
				}
			}
	    	
	    }
	    );
	    Log.d("maintab", "maintab_MainActivity------onCreate");
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}



