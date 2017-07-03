package com.example.FindFood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Tousu extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tousu);
		Button m_sendEmailBtn = (Button) findViewById(R.id.fasongtousu);  
		        m_sendEmailBtn.setOnClickListener(new OnClickListener(){  
		            @Override  
		            public void onClick(View v) {                 
		            	// TODO Auto-generated method stub
		            	String[] receiver = new String[] {"jgokfg1234@163.com"};  
		                String subject = "投诉建议";  
		                EditText et = (EditText)findViewById(R.id.tousuEdit);
		                
		                String content = et.getText().toString();
		                 
		                  
		                Intent email = new Intent(Intent.ACTION_SEND);  
		                email.setType("message/rfc822");  
		                // 设置邮件发收人  
		                email.putExtra(Intent.EXTRA_EMAIL, receiver);  
		                // 设置邮件标题  
		                email.putExtra(Intent.EXTRA_SUBJECT, subject);  
		                // 设置邮件内容  
		                email.putExtra(Intent.EXTRA_TEXT, content);               
		                  
		                // 调用系统的邮件系统  
		                startActivity(Intent.createChooser(email, "请选择邮件发送软件"));  
		           }



		        });  

		
		
		
		
		
	}
}

